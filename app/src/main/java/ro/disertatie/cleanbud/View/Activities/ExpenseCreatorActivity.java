package ro.disertatie.cleanbud.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.disertatie.cleanbud.BuildConfig;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.API.APIClient;
import ro.disertatie.cleanbud.View.API.APIService;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.ExpenseCategoryDialog;
import ro.disertatie.cleanbud.View.Models.ExpenseCategory;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.View.ProgressDialogClass;
import ro.disertatie.cleanbud.View.ViewModel.ExpenseCreatorViewModel;
import ro.disertatie.cleanbud.databinding.ActivityBudgetCreatorBinding;
import ro.disertatie.cleanbud.databinding.ActivityExpenseCreatorBinding;

import static ro.disertatie.cleanbud.View.Activities.TestOcrActivity.rotate;
import static ro.disertatie.cleanbud.View.Utils.Constants.PICK_PHOTO_FOR_OCR;

public class ExpenseCreatorActivity extends AppCompatActivity implements ExpenseCategoryDialog.OnCompleteListenerColor {
    private static final String LOG_TAG = "Text API";
    private static final int PHOTO_REQUEST = 10;
    private Uri imageUri;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";
    private APIService apiService;
    private ExpenseCreatorViewModel expenseCreatorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExpenseCreatorBinding activityExpenseCreatorBinding = DataBindingUtil.setContentView(this,R.layout.activity_expense_creator);
         expenseCreatorViewModel = new ExpenseCreatorViewModel(this,activityExpenseCreatorBinding);
        ActivityCompat.requestPermissions(ExpenseCreatorActivity.this, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        expenseCreatorViewModel.addDateExpenseClick();
        expenseCreatorViewModel.addExpenseClick();
        expenseCreatorViewModel.addExpenseCategory();
        expenseCreatorViewModel.setBudgetId(getIntent().getIntExtra(Constants.BUDGET_ID_KEY,-1));
        expenseCreatorViewModel.fillComponentsForEdit(getIntent());
    }

    private boolean isScan(){
        return getIntent().getBooleanExtra(Constants.IS_SCAN_KEY,false);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(isScan()){
                        takePicture();
                    }else{

                    }

                } else {
                    Toast.makeText(ExpenseCreatorActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = decodeBitmapUri(this, imageUri);
                Bitmap bitmap2 = createContrast(bitmap,25);
                expenseCreatorViewModel.setImgUri(imageUri);

                apiService = APIClient.getRetrofit2().create(APIService.class);


                File f = new File(getApplicationContext().getCacheDir(), String.valueOf(Calendar.getInstance().getTimeInMillis()+".jpeg"));
                f.createNewFile();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                Bitmap bitmap3 = rotate(bitmap2,90);
                expenseCreatorViewModel.setReceiptImage(SetBrightness(bitmap3,25));
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);

                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveImageToExternal(f.getName(),bitmap3);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
                MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", f.getName(), reqFile);
                Call<ResponseBody> call = apiService.uploadAttachment(body);

                ProgressDialogClass progressDialogClass = new ProgressDialogClass(this);
                progressDialogClass.showDialog("Processing Text","Please Wait");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            assert response.body() != null;
                            expenseCreatorViewModel.parseDataFromScanner(response.body().string());
                            progressDialogClass.dismissDialog();
                        } catch (IOException e) {
                            e.printStackTrace();
                            progressDialogClass.dismissDialog();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });


            } catch (Exception e) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                        .show();
                Log.e(LOG_TAG, e.toString());
            }
        }
        else if (requestCode == PICK_PHOTO_FOR_OCR && resultCode == Activity.RESULT_OK){
            try {
                imageUri = data.getData();
                expenseCreatorViewModel.setImgUri(imageUri);
                Bitmap bitmap = decodeBitmapUri(this, imageUri);

                apiService = APIClient.getRetrofit2().create(APIService.class);


                File f = new File(getApplicationContext().getCacheDir(), String.valueOf(Calendar.getInstance().getTimeInMillis()+".jpeg"));
                f.createNewFile();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                expenseCreatorViewModel.setReceiptImage(bitmap);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);

                byte[] bitmapdata = bos.toByteArray();

                //write the bytes in file
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveImageToExternal(f.getName(),bitmap);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
                MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", f.getName(), reqFile);
                Call<ResponseBody> call = apiService.uploadAttachment(body);

                ProgressDialogClass progressDialogClass = new ProgressDialogClass(this);
                progressDialogClass.showDialog("Processing Text","Please Wait");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            assert response.body() != null;
                            expenseCreatorViewModel.parseDataFromScanner(response.body().string());
                            progressDialogClass.dismissDialog();
                        } catch (IOException e) {
                            e.printStackTrace();
                            progressDialogClass.dismissDialog();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });


            } catch (Exception e) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                        .show();
                Log.e(LOG_TAG, e.toString());
            }
        }
    }


    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        imageUri = FileProvider.getUriForFile(ExpenseCreatorActivity.this,
                BuildConfig.APPLICATION_ID + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PHOTO_REQUEST);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
        }
        super.onSaveInstanceState(outState);
    }
    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void saveImageToExternal(String imgName, Bitmap bm) throws IOException {
        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+"/cleanbud"); //Creates app specific folder
        path.mkdirs();
        File imageFile = new File(path, imgName+".jpeg"); // Imagename.png
        FileOutputStream out = new FileOutputStream(imageFile);
        try{
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out); // Compress Image
            out.flush();
            out.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(getApplicationContext(),new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        } catch(Exception e) {
            Log.i("ExternalStorage", "not working");
            throw new IOException();
        }
    }
    public Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }


    public static Bitmap createContrast(Bitmap src, double value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    public Bitmap SetBrightness(Bitmap src, int value) {
        // original image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    @Override
    public void onClickCategoryExpense(Integer position) {
        expenseCreatorViewModel.updateCategory(ExpenseCategory.populateExpenseTypes()[position].getTitleExpCategory());
        expenseCreatorViewModel.categoryId(position);
    }
}

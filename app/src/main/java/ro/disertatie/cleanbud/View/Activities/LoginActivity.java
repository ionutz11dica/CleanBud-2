package ro.disertatie.cleanbud.View.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;



import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.UserDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.UserMethods;
import ro.disertatie.cleanbud.View.Models.User;
import ro.disertatie.cleanbud.View.Utils.CheckForNetwork;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.Utils.StaticVar;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{


    @BindView(R.id.editTextEmailLogin)
    EditText etEmail;
    @BindView(R.id.editTextPasswordLogin)
    EditText etPassword;
    @BindView(R.id.cirLoginButton)
    Button btnLogin;
    @BindView(R.id.imv_google_signin)
    ImageView googleLogin;
    GoogleSignInOptions gso;

    GoogleSignInClient mGoogleSignInClient;
    UserDAO userDao;
    UserMethods userMethods;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        openDb();
        initGoogleSignIn();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifySignin()) {
                    verifyLoginFromDb(v);

                }else{
                    Snackbar.make(v, "Invalid inputs", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });


    }

    void initGoogleSignIn(){
      gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleLogin.setOnClickListener(this);
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }


    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }

    private void updateUI(GoogleSignInAccount account){
        if(account!=null){
            // createUserGoogle(account);
            verifyLoginFromDbGoogleAccount(account);
        } else {

        }
    }



    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .build();
        return GoogleSignIn.getClient(getApplicationContext(), signInOptions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_google_signin:
                if(CheckForNetwork.isConnectedToNetwork(getApplicationContext())){

                    signIn();
                    break;
                }else{
                    Snackbar.make(view, "Please check your network", Snackbar.LENGTH_LONG)
                            .show();
                    break;
                }

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("Fail" ,"signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    void verifyLoginFromDb(View v){
        final User userr = new User();
        userr.setEmail(etEmail.getText().toString());
        userr.setPassword(etPassword.getText().toString());

        final Intent intent = new Intent(getApplicationContext(),StartActivity.class);

        User user = new User();
        user.setEmail(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());
        intent.putExtra(Constants.USER_KEY,user);

        Single<User> userDb  = userMethods.verifyAvailableAccount(userr.getEmail(),userr.getPassword());
        userDb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public synchronized void onSuccess(User user) {
                        StaticVar.USER_ID = user.getUserId();
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(v, "Invalid inputs", Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
    }



    @SuppressLint("StaticFieldLeak")
    void verifyLoginFromDbGoogleAccount(final GoogleSignInAccount account) {
        final User user = new User();
        user.setEmail(account.getEmail());

        final Intent intent = new Intent(getApplicationContext(),StartActivity.class);


        Single<User> userDb  = userMethods.verifyExistenceGoogleAccount(user.getEmail());
        userDb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public synchronized void onSuccess(User user) {
                        StaticVar.USER_ID = user.getUserId();
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        userMethods.insertUser(user);
                        startActivity(intent);
                    }
                });
    }

    boolean verifySignin(){
        if( etEmail.getText() == null || etEmail.getText().toString().trim().isEmpty()){
            return false;
        }else if(etPassword.getText() == null || etPassword.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    void openDb(){
        userDao = AppRoomDatabase.getInstance(getApplicationContext()).getUserDao();
        userMethods = UserMethods.getInstance(userDao);
    }

}

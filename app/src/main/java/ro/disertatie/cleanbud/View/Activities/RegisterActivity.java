package ro.disertatie.cleanbud.View.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.reactivex.Single;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.UserDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.UserMethods;
import ro.disertatie.cleanbud.View.Fragments.HomeFragment;
import ro.disertatie.cleanbud.View.Models.User;


public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.editTextName)
    EditText etName;
    @BindView(R.id.editTextEmail)
    EditText etEmail;
    @BindView(R.id.editTextMobile)
    EditText etMobile;
    @BindView(R.id.editTextPassword)
    EditText etPassword;
    @BindView(R.id.cirRegisterButton)
    Button btnRegister;

    UserDAO userDao;
    UserMethods userMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        changeStatusBarColor();
        openDb();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verifySignup()){
                    User user = new User();
                    user.setName(etName.getText().toString());
                    user.setEmail(etEmail.getText().toString());
                    user.setMobilePhone(etMobile.getText().toString());
                    user.setPassword(etPassword.getText().toString());

                    userMethods.insertUser(user);
                    startActivity(new Intent(getApplicationContext(), StartActivity.class));
                }else{
                    Snackbar.make(view, "Please check your inputs", Snackbar.LENGTH_LONG)
                            .show();
                }


            }
        });

    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }


    void openDb(){
        userDao = AppRoomDatabase.getInstance(getApplicationContext()).getUserDao();
        userMethods = UserMethods.getInstance(userDao);
    }

    boolean verifySignup(){
        if(etEmail.getText() == null || etEmail.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            return false;
        } else if( etName.getText() == null || etName.getText().toString().trim().isEmpty() || etName.getText().toString().length() < 3){
            return false;
        }else if(etPassword.getText() == null || etPassword.getText().toString().trim().isEmpty()) {
            return false;
        }else if(etMobile.getText() == null || etMobile.getText().toString().trim().isEmpty() || etMobile.getText().toString().length() != 10) {
            return false;
        }
        return true;
    }

}

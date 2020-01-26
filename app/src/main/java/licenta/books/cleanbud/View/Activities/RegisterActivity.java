package licenta.books.cleanbud.View.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.Database.ObjectBox;
import licenta.books.cleanbud.View.Models.User;


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

    Box<User> userBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        changeStatusBarColor();

        userBox = ObjectBox.get().boxFor(User.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setName(etName.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setMobilePhone(etMobile.getText().toString());
                user.setPassword(etPassword.getText().toString());

                userBox.put(user);
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



}

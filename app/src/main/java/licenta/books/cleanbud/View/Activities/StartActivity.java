package licenta.books.cleanbud.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.Fragments.HomeFragment;

public class StartActivity extends AppCompatActivity implements HomeFragment.OnHomeFragmentInteractionListener {

    Fragment homeFragment;

    FragmentManager fm ;
    Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        homeFragment = new HomeFragment();
        fm = getSupportFragmentManager();

        active = homeFragment;

        fm.beginTransaction().add(R.id.fragment_container,homeFragment,"homeF").commit();
    }

    @Override
    public void onHomeButtonsPressed(int idBtn) {
        Toast.makeText(getApplicationContext(),"=====" + String.valueOf(idBtn),Toast.LENGTH_LONG).show();
    }
}

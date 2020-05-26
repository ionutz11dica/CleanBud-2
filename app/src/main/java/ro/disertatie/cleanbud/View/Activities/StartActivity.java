package ro.disertatie.cleanbud.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import java.util.Objects;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Fragments.BudgetFragments;
import ro.disertatie.cleanbud.View.Fragments.CurrencyFragment;
import ro.disertatie.cleanbud.View.Fragments.HomeFragment;
import ro.disertatie.cleanbud.View.Fragments.ReportsFragment;

public class StartActivity extends AppCompatActivity implements HomeFragment.OnHomeFragmentInteractionListener, CurrencyFragment.CurrencyFragmentInteractionListener, BudgetFragments.BudgetInteractionListener, ReportsFragment.ReportInteractionListener {

    Fragment homeFragment;
    Fragment currencyFragment;
    Fragment budgetsFragment;
    Fragment reportsFragment;

    FragmentManager fm ;
    Fragment active;
    private static final int REQUEST_WRITE_PERMISSION = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActivityCompat.requestPermissions(StartActivity.this, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);


        homeFragment = new HomeFragment();
        currencyFragment = new CurrencyFragment();
        budgetsFragment = new BudgetFragments();
        reportsFragment = new ReportsFragment();

        fm = getSupportFragmentManager();

        active = homeFragment;

        fm.beginTransaction().add(R.id.fragment_container,homeFragment,"homeF").commit();
        fm.beginTransaction().add(R.id.fragment_container,budgetsFragment,"budgetF").hide(budgetsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,currencyFragment,"currencyF").hide(currencyFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,reportsFragment,"reportsF").hide(reportsFragment).commit();
    }

    @Override
    public void onHomeButtonsPressed(int idBtn) {
        switch (idBtn){
            case 1 :{
//                fm.beginTransaction().hide(active).show(reportsFragment).commit();
//                active = reportsFragment;
                startActivity(new Intent(getApplicationContext(),TestOcrActivity.class));
                break;
            }
            case 2 : {
                fm.beginTransaction().hide(active).show(budgetsFragment).commit();
                active = budgetsFragment;
                break;

            }
            case 3 : {
                fm.beginTransaction().hide(active).show(currencyFragment).commit();
                active = currencyFragment;
                break;
            }

        }
    }



    @Override
    public void onBackButtonPressed(String  fragment) {
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(fragment))).commit();
        active = fm.findFragmentByTag(fragment);
    }

    @Override
    public void onBudgetListener() {

    }

    @Override
    public void onBackButtonPressedBudget(String fragment) {
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(fragment))).commit();
        active = fm.findFragmentByTag(fragment);
    }
}

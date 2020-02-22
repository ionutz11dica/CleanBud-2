package licenta.books.cleanbud.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Objects;

import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.Fragments.BudgetFragments;
import licenta.books.cleanbud.View.Fragments.CurrencyFragment;
import licenta.books.cleanbud.View.Fragments.HomeFragment;

public class StartActivity extends AppCompatActivity implements HomeFragment.OnHomeFragmentInteractionListener, CurrencyFragment.CurrencyFragmentInteractionListener, BudgetFragments.BudgetInteractionListener {

    Fragment homeFragment;
    Fragment currencyFragment;
    Fragment budgetsFragment;

    FragmentManager fm ;
    Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        homeFragment = new HomeFragment();
        currencyFragment = new CurrencyFragment();
        budgetsFragment = new BudgetFragments();

        fm = getSupportFragmentManager();

        active = homeFragment;

        fm.beginTransaction().add(R.id.fragment_container,homeFragment,"homeF").commit();
        fm.beginTransaction().add(R.id.fragment_container,budgetsFragment,"budgetF").hide(budgetsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,currencyFragment,"currencyF").hide(currencyFragment).commit();

    }

    @Override
    public void onHomeButtonsPressed(int idBtn) {
        switch (idBtn){
            case 1 :{

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
        active = fm.findFragmentByTag(fragment);;
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

package ro.disertatie.cleanbud.View.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.EconomyBudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAO.UserDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.EconomyBudgetMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.UserMethods;
import ro.disertatie.cleanbud.View.Fragments.AnalyticsFragment;
import ro.disertatie.cleanbud.View.Fragments.BudgetFragments;
import ro.disertatie.cleanbud.View.Fragments.CurrencyFragment;
import ro.disertatie.cleanbud.View.Fragments.FavoriteHotelsFragment;
import ro.disertatie.cleanbud.View.Fragments.HomeFragment;
import ro.disertatie.cleanbud.View.Fragments.HotelDetailsFragment;
import ro.disertatie.cleanbud.View.Fragments.HotelsFragment;
import ro.disertatie.cleanbud.View.Fragments.ReportsFragment;
import ro.disertatie.cleanbud.View.Fragments.TripFilterFragment;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.View.Models.Trip;
import ro.disertatie.cleanbud.View.Models.User;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.Utils.StaticVar;
import ro.disertatie.cleanbud.View.View.ProgressDialogClass;

public class StartActivity extends AppCompatActivity implements HomeFragment.OnHomeFragmentInteractionListener, CurrencyFragment.CurrencyFragmentInteractionListener, BudgetFragments.BudgetInteractionListener,
        ReportsFragment.ReportInteractionListener, TripFilterFragment.TripFilterListener, HotelsFragment.HotelsListener, HotelDetailsFragment.HotelsDetailsListener, FavoriteHotelsFragment.FavoriteHotelsListener {

    Fragment homeFragment;
    Fragment currencyFragment;
    Fragment budgetsFragment;
    Fragment reportsFragment;
    Fragment tripsFilterFragment;
    Fragment hotelsFragment;
    Fragment hotelsDetailsFragment;
    Fragment favoriteFragment;
    Fragment analyticsFragment;

    FragmentManager fm ;
    Fragment active;
    private static final int REQUEST_WRITE_PERMISSION = 20;


    UserMethods userMethods;
    UserDAO userDAO;

    private EconomyBudgetMethods economyBudgetMethods;
    private EconomyBudgetDAO economyBudgetDAO;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActivityCompat.requestPermissions(StartActivity.this, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);

        openDb();

         if(getIntent().hasExtra(Constants.USER_KEY)){
            User user = getIntent().getParcelableExtra(Constants.USER_KEY);
            ProgressDialogClass progressDialogClass = new ProgressDialogClass(this);
            progressDialogClass.showDialog("Fetching data..", "Please wait");
            if(user!=null){
                Single<User> userDb  = userMethods.verifyAvailableAccount(user.getEmail(),user.getPassword());
                userDb.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<User>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public synchronized void onSuccess(User user) {
                                StaticVar.USER_ID = user.getUserId();
                                progressDialogClass.dismissDialog();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        }

        homeFragment = new HomeFragment();
        currencyFragment = new CurrencyFragment();
        budgetsFragment = new BudgetFragments();
        reportsFragment = new ReportsFragment();
        tripsFilterFragment = new TripFilterFragment();
        hotelsFragment = new HotelsFragment();
        hotelsDetailsFragment = new HotelDetailsFragment();
        favoriteFragment = new FavoriteHotelsFragment();
        analyticsFragment = new AnalyticsFragment();

        fm = getSupportFragmentManager();

        active = homeFragment;

        fm.beginTransaction().add(R.id.fragment_container,homeFragment,"homeF").commit();
        fm.beginTransaction().add(R.id.fragment_container,budgetsFragment,"budgetF").hide(budgetsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,currencyFragment,"currencyF").hide(currencyFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,reportsFragment,"reportsF").hide(reportsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,tripsFilterFragment,"tripFilterF").hide(tripsFilterFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,hotelsFragment,"hotelsF").hide(hotelsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,hotelsDetailsFragment,"hotelsDetailsF").hide(hotelsDetailsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,favoriteFragment,"favoriteF").hide(favoriteFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,analyticsFragment,"analyticsF").hide(analyticsFragment).commit();


    }

    @Override
    public void onHomeButtonsPressed(int idBtn) {
        switch (idBtn){
            case 1 :{
                fm.beginTransaction().hide(active).show(tripsFilterFragment).commit();
                active = tripsFilterFragment;
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
            case 4 : {
                fm.beginTransaction().hide(active).show(analyticsFragment).commit();
                active = analyticsFragment;
                break;
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
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

    void openDb(){
        userDAO = AppRoomDatabase.getInstance(getApplicationContext()).getUserDao();
        userMethods = UserMethods.getInstance(userDAO);
        economyBudgetDAO = AppRoomDatabase.getInstance(getApplicationContext()).economyBudgetDAO();
        economyBudgetMethods = EconomyBudgetMethods.getInstance(economyBudgetDAO);
    }

    @Override
    public void onBackButtonPressedTripFilter(String string) {
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(string))).commit();
        active = fm.findFragmentByTag(string);
    }

    @Override
    public void passDataToHotels(ArrayList<ResultObjectHotel> list,String fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.HOTELS_LIST_KEY,list);
        fm.findFragmentByTag(fragment).setArguments(bundle);
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(fragment))).commit();

        active = fm.findFragmentByTag(fragment);
    }

    @Override
    public void onBackButtonPressedHotelsListener(String string) {
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(string))).commit();
        active = fm.findFragmentByTag(string);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void passHotelDetailsToFragment(ResultObjectHotel id,String fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.HOTELS_KEY,id);
        fm.findFragmentByTag(fragment).setArguments(bundle);
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(fragment))).commit();
        active = fm.findFragmentByTag(fragment);
    }

    @Override
    public void onBackButtonPressedHotelsDetailsListener(String string) {
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(string))).commit();
        active = fm.findFragmentByTag(string);
    }

    @Override
    public void onBackButtonPressedFavorite(String string) {
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(string))).commit();
        active = fm.findFragmentByTag(string);
    }

    @Override
    public void passDataToHotelDetailsFav(Trip trip, String string) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.HOTELS_KEY,trip);
        fm.findFragmentByTag(string).setArguments(bundle);
        fm.beginTransaction().hide(active).show(Objects.requireNonNull(fm.findFragmentByTag(string))).commit();
        active = fm.findFragmentByTag(string);
    }
}

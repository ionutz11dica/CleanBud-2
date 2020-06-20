package ro.disertatie.cleanbud.View.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.EconomyBudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.EconomyBudgetMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.UserMethods;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.CreateEconomyBudgetDialog;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.Utils.StaticVar;

public class HomeFragment extends Fragment {

    //Delegate -> Listener
    private OnHomeFragmentInteractionListener listener;

    //Attributes


    @BindView(R.id.cv_myBudget)
    CardView cvBudgets;

    @BindView(R.id.cv_currency)
    CardView cvCurrency;

    @BindView(R.id.cv_myTrips)
    CardView cv_myTrips;

    @BindView(R.id.cv_analytics)
    CardView cvAnalytics;

    @BindView(R.id.cv_mySettings)
    CardView cv_mySettings;

    @BindView(R.id.home_toolbar)
    Toolbar toolbar;



    @BindView(R.id.tv_home_user)
    TextView textView;

    private EconomyBudgetMethods economyBudgetMethods;
    private EconomyBudgetDAO economyBudgetDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        ButterKnife.bind(this,view);
        toolbar.setTitle("Home");
        textView.setText(StaticVar.NAME_USER+"'s budget");
        toolbar.setTitleTextAppearance(getContext(),R.style.Widget_AppCompat_ActionBar_Solid);
        openDb();
        currencyClick();
        budgetsClick();
        tripsClick();
        analyticsClick();
        settingsClick();
        return view;
    }

    private void currencyClick(){
        cvCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHomeButtonsPressed(3);
            }
        });
    }



    private void budgetsClick(){
        cvBudgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHomeButtonsPressed(2);
            }
        });
    }

    private void analyticsClick(){
        cvAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHomeButtonsPressed(4);
            }
        });
    }

    private void settingsClick(){
        cvAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHomeButtonsPressed(5);
            }
        });
    }

    private void tripsClick(){
        cv_myTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single<Integer> single = economyBudgetMethods.checkIfEconomyBudgetExists(StaticVar.USER_ID);
                single.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(Integer integer) {
                                if(integer == 0){
                                    Toast.makeText(getContext(),"Nu exista in bd",Toast.LENGTH_LONG).show();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                            builder1.setTitle("Budget Savings");
                                            builder1.setMessage("Hei!\nWe saw you don't have a budget savings, do you want to create one right now?");
                                            builder1.setCancelable(false);
                                            builder1.setPositiveButton(
                                                    "Yes",
                                                    (dialog, id) -> {
                                                        CreateEconomyBudgetDialog createEconomyBudgetDialog = new CreateEconomyBudgetDialog();
                                                        createEconomyBudgetDialog.setTargetFragment(HomeFragment.this,Constants.REQUEST_CREATE_SAVING_BUDGET);
                                                        createEconomyBudgetDialog.show(getActivity().getSupportFragmentManager(),"budgSavings");

                                                    });

                                            builder1.setNegativeButton(
                                                    "No",
                                                    (dialog, id) -> dialog.cancel());

                                            AlertDialog alert11 = builder1.create();
                                            alert11.show();
                                        }
                                    });

                                }else{
                                    listener.onHomeButtonsPressed(1);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        });
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            listener = (OnHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data != null){
            if (requestCode == Constants.REQUEST_CREATE_SAVING_BUDGET){
                Toast.makeText(getContext(),"S-a creat",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnHomeFragmentInteractionListener{
        void onHomeButtonsPressed(int idBtn);
    }

    void openDb(){
        economyBudgetDAO = AppRoomDatabase.getInstance(getContext()).economyBudgetDAO();
        economyBudgetMethods = EconomyBudgetMethods.getInstance(economyBudgetDAO);
    }
}

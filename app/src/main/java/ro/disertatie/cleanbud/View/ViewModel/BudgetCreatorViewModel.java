package ro.disertatie.cleanbud.View.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Activities.BudgetCreatorActivity;
import ro.disertatie.cleanbud.View.Adapter.BudgetTypeRecyclerViewAdapter;
import ro.disertatie.cleanbud.View.Adapter.SpinnerAdapter;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAO.CurrencyDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.BudgetMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.CurrencyMethods;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.BudgetType;
import ro.disertatie.cleanbud.View.Models.Currency;
import ro.disertatie.cleanbud.View.Models.User;
import ro.disertatie.cleanbud.View.Uitility.Utility;
import ro.disertatie.cleanbud.View.Utils.SpinnerClass;
import ro.disertatie.cleanbud.View.Utils.StaticVar;
import ro.disertatie.cleanbud.databinding.ActivityBudgetCreatorBinding;

import static ro.disertatie.cleanbud.View.Utils.Constants.ADD_BUDGET_KEY;

public class BudgetCreatorViewModel {
    private BudgetCreatorActivity budgetCreatorActivity;
    private ActivityBudgetCreatorBinding activityBudgetCreatorBinding;
    private BudgetTypeRecyclerViewAdapter adapterBud;
    private BudgetMethods budgetMethods;
    private CurrencyMethods currencyMethods;

    private List<BudgetType> budgetTypes = new ArrayList<>();

    public BudgetCreatorViewModel(BudgetCreatorActivity budgetCreatorActivity, ActivityBudgetCreatorBinding activityBudgetCreatorBinding) {
        this.budgetCreatorActivity = budgetCreatorActivity;
        this.activityBudgetCreatorBinding = activityBudgetCreatorBinding;
        RxJavaPlugins.setErrorHandler(throwable -> {});
        openDB();

    }

    public void setUpToolbar() {
//        activityBudgetCreatorBinding.budgetCreatorToolbar.inflateMenu(R.menu.menu_budget_creator);

        activityBudgetCreatorBinding.budgetCreatorToolbar.setTitleTextAppearance(budgetCreatorActivity.getApplicationContext(),R.style.Widget_AppCompat_ActionBar_Solid);
        activityBudgetCreatorBinding.budgetCreatorToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
    }


    public void onActionChangeEt(){
        activityBudgetCreatorBinding.etBudgetDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do something, e.g. set your TextView here via .setText()
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }


    public void setAdapterSpinner(){
        Single<List<Currency>> single = currencyMethods.getCurrencies();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Currency>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Currency> currencies) {
                        activityBudgetCreatorBinding.currencySpn.setAdapter(new SpinnerAdapter(budgetCreatorActivity,R.layout.row_spinner,currencies));

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }




    public void setDescriptionBudget(){
        String month = getMonthForInt(Calendar.getInstance().get(Calendar.MONTH));

        activityBudgetCreatorBinding.edBudgetTitle.setText(month.substring(0,1).toUpperCase() + month.substring(1) + " " + Calendar.getInstance().get(Calendar.YEAR));
    }

    public void setBudgetTypeAdapter(){

        budgetTypes.add(new BudgetType(1,"Long Term Budget",R.drawable.ic_long_term));
        budgetTypes.add(new BudgetType(2,"Short Term Budget",R.drawable.ic_short));
        budgetTypes.add(new BudgetType(3,"Flexible  Budget",R.drawable.ic_flexible));
        budgetTypes.add(new BudgetType(4,"Financial Budget",R.drawable.ic_help));
        budgetTypes.add(new BudgetType(5,"Expense Budget",R.drawable.ic_budget_expense));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#e3b04b"));
        colors.add(Color.parseColor("#1089ff"));
        colors.add(Color.parseColor("#53d397"));

        colors.add(Color.parseColor("#f73859"));
        colors.add(Color.parseColor("#ef6c35"));


        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(budgetCreatorActivity, LinearLayoutManager.HORIZONTAL, false);
        activityBudgetCreatorBinding.budgetTypesRecycler.setLayoutManager(horizontalLayoutManagaer);
        adapterBud = new BudgetTypeRecyclerViewAdapter(budgetCreatorActivity,colors,budgetTypes);
        adapterBud.setClickListener(budgetCreatorActivity);
        activityBudgetCreatorBinding.budgetTypesRecycler.setAdapter(adapterBud);
    }


    public void addNewBudgetOnClick(Intent intent){
        activityBudgetCreatorBinding.cirAddBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(v)){
                    Budget budget = new Budget();
                    budget.setTitle(activityBudgetCreatorBinding.edBudgetTitle.getText().toString());
                    budget.setInitialAmount(Float.parseFloat(activityBudgetCreatorBinding.etBudgetSum.getText().toString()));
                    budget.setCurrentAmount(Float.parseFloat(activityBudgetCreatorBinding.etBudgetSum.getText().toString()));
                    budget.setBudgetDescription(activityBudgetCreatorBinding.etBudgetDescription.getText().toString());
                    budget.setBudgetTypeId(budgetTypes.get(budgetCreatorActivity.selectedBudgetType).getBudgetTypeId());
                    budget.setDate(Calendar.getInstance().getTime());
                    budget.setUserId(StaticVar.USER_ID);
                    budget.setCurrencyId(activityBudgetCreatorBinding.currencySpn.getSelectedItemPosition());

                    budgetMethods.insertBudget(budget);
                    intent.putExtra(ADD_BUDGET_KEY,budget);
                    budgetCreatorActivity.setResult(Activity.RESULT_OK,intent);
                    budgetCreatorActivity.finish();
                }
            }
        });
    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    private boolean validate(View view){
        boolean isValid = true;
        if (Utility.isEmptyOrNull(activityBudgetCreatorBinding.edBudgetTitle.getText().toString())){
            isValid = false;
            activityBudgetCreatorBinding.edBudgetTitle.setError("Verify inputs");
        }else if (Utility.isEmptyOrNull(activityBudgetCreatorBinding.etBudgetSum.getText().toString())){
            isValid = false;
            activityBudgetCreatorBinding.etBudgetSum.setError("Verify inputs");
        }else if (budgetCreatorActivity.selectedBudgetType == -1){
            isValid = false;
            Snackbar.make(view, "Please check your inputs", Snackbar.LENGTH_LONG)
                    .show();
        }

        return isValid;
    }







    private void openDB(){
        BudgetDAO budgetDAO = AppRoomDatabase.getInstance(budgetCreatorActivity.getApplicationContext()).getBudgetDao();
        budgetMethods = BudgetMethods.getInstance(budgetDAO);
        CurrencyDAO currencyDAO = AppRoomDatabase.getInstance(budgetCreatorActivity.getApplicationContext()).currencyDAO();
        currencyMethods = CurrencyMethods.getInstance(currencyDAO);
    }
}

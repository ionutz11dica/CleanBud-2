package ro.disertatie.cleanbud.View.ViewModel;

import android.content.Intent;
import android.os.Build;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Activities.BudgetCreatorActivity;
import ro.disertatie.cleanbud.View.Adapter.BudgetTypeRecyclerViewAdapter;
import ro.disertatie.cleanbud.View.Adapter.RecordBudgetAdapter;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.BudgetMethods;
import ro.disertatie.cleanbud.View.Fragments.BudgetFragments;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.BudgetType;
import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.Income;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.Utils.RecordProtocol;
import ro.disertatie.cleanbud.View.View.ProgressDialogClass;
import ro.disertatie.cleanbud.databinding.ActivityBudgetCreatorBinding;
import ro.disertatie.cleanbud.databinding.BudgetComplexDetailsBinding;
import ro.disertatie.cleanbud.databinding.BudgetFragmentBinding;

public class BudgetFragmentViewModel {
    private BudgetFragments budgetFragments;
    private BudgetFragmentBinding budgetFragmentBinding;
    private BudgetTypeRecyclerViewAdapter adapterBud;
    private BudgetMethods budgetMethods;
    private List<Budget> budgetsList;
    private int cursorBudgets = 0;
    private ProgressDialogClass progressDialogClass;
    private BudgetFragments.BudgetInteractionListener listener;
    private BudgetComplexDetailsBinding complexDetailsBinding;


    public BudgetFragmentViewModel(BudgetFragments budgetFragments, BudgetFragmentBinding budgetFragmentBinding) {
        this.budgetFragments = budgetFragments;
        this.budgetFragmentBinding = budgetFragmentBinding;
        this.budgetsList = new ArrayList<>();
        this.progressDialogClass = new ProgressDialogClass(budgetFragments.getActivity());
        openDB();
//        RxJavaPlugins.setErrorHandler(throwable -> {});


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews(List<Budget> budgets){
        if (budgets.isEmpty()){
            initToolbar();


        }else{
            getBudgets();
            initBudgetsView();
            changeNextBudget();
        }
    }



    private void initBudgetsView(){
        budgetFragmentBinding.llPlaceholder.setVisibility(View.GONE);
        budgetFragmentBinding.fabAddBudget.setVisibility(View.GONE);
        budgetFragmentBinding.layoutBudgetList.getRoot().setVisibility(View.VISIBLE);

        ArrayList<Expense> expenses = new ArrayList<>();
        Expense expense = new Expense();

        expense.setExpenseDate(TimestampConverter.fromTimestamp("2020-05-20 15:53:34"));
        expense.setTitleExpense("Titlu mistoc");
        expense.setAmountExpense(50);
        expenses.add(expense);
        ArrayList<Income> incomes = new ArrayList<>();
        Income income = new Income();

        income.setDateIncome(TimestampConverter.fromTimestamp("2020-05-20 15:53:34"));
        income.setTitleIncome("Salariu");
        income.setAmountIncome(2020);
        incomes.add(income);
        RecordBudgetAdapter recordBudgetAdapter = new RecordBudgetAdapter(budgetFragments.getActivity(),expenses,incomes);
        budgetFragmentBinding.layoutBudgetList.recordLv.setAdapter(recordBudgetAdapter);


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        budgetFragmentBinding.budgetToolbar.setTitleTextAppearance(budgetFragments.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        budgetFragmentBinding.budgetToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        budgetFragmentBinding.llPlaceholder.setVisibility(View.VISIBLE);
        complexDetailsBinding.getRoot().setVisibility(View.GONE);
        budgetFragmentBinding.fabAddBudget.setVisibility(View.VISIBLE);
        backListener();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        budgetFragmentBinding.budgetToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressedBudget("homeF");
            }
        });
    }


    public void fabAddClick(){
        budgetFragmentBinding.fabAddBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(budgetFragments.getContext(), BudgetCreatorActivity.class);
                Objects.requireNonNull(budgetFragments.getActivity()).startActivityForResult(intent, Constants.REQUEST_BUDGET_CREATOR);
            }
        });
    }

    public void leftArrowClick(){
        if(budgetsList.size() != 1 && cursorBudgets != 0){
            cursorBudgets -= 1;
            changeNextBudget();
        }
    }

    public void rightArrowClick(){
        if(budgetsList.size() != 1 && cursorBudgets != budgetsList.size() - 1){
            cursorBudgets += 1;
            changeNextBudget();
        }
    }

    private void changeNextBudget(){
        if (!budgetsList.isEmpty())
            complexDetailsBinding.budgetToolbar.setTitle(budgetsList.get(cursorBudgets).getTitle());
            complexDetailsBinding.budgetSumTv.setText(String.valueOf(budgetsList.get(cursorBudgets).getAmount()));

//        budgetFragmentBinding.layoutBudgetList.budgetCurrencyTv.setText(String.valueOf(budgetsList.get(cursorBudgets).getAmount()));
    }


    public void addNewBudget(Budget budget){
        if(budget!= null){
            budgetsList.add(budget);
            if(budgetsList.size() == 1){
                initBudgetsView();
                changeNextBudget();
            }else{

                changeNextBudget();
            }
        }

    }

    private void getBudgets(){
        progressDialogClass.showDialog("Please wait..","Fetching data from database..");

        Single<List<Budget>> single = budgetMethods.getAllBudgets(1);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Budget>>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   Log.d("Budget", "nu");
                               }

                               @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                               @Override
                               public void onSuccess(List<Budget> budgets) {
                                   Log.d("Budget", String.valueOf(budgets.size()));
                                   budgetsList = budgets;
                                   progressDialogClass.dismissDialog();
                                   initViews(budgets);

                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.d("Budget", e.getMessage());
                               }
                           }
                    );
    }

    public void setComplexDetails(){
        complexDetailsBinding = DataBindingUtil.bind(budgetFragmentBinding.layoutBudgetList.getRoot());

    }


    private void openDB(){
        BudgetDAO budgetDAO = AppRoomDatabase.getInstance(budgetFragments.getContext().getApplicationContext()).getBudgetDao();
        budgetMethods = BudgetMethods.getInstance(budgetDAO);
    }

}

package ro.disertatie.cleanbud.View.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Activities.BudgetCreatorActivity;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.Income;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.ViewModel.BudgetFragmentViewModel;
import ro.disertatie.cleanbud.databinding.BudgetFragmentBinding;

import static android.app.Activity.RESULT_OK;
import static ro.disertatie.cleanbud.View.Utils.Constants.ADD_BUDGET_KEY;
import static ro.disertatie.cleanbud.View.Utils.Constants.ADD_EXPENSE_KEY;
import static ro.disertatie.cleanbud.View.Utils.Constants.ADD_INCOME_KEY;
import static ro.disertatie.cleanbud.View.Utils.Constants.UPDATE_EXPENSE_KEY;
import static ro.disertatie.cleanbud.View.Utils.Constants.UPDATE_INCOME_KEY;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BudgetFragments extends Fragment implements Toolbar.OnMenuItemClickListener {
    private BudgetInteractionListener listener;
    private BudgetFragmentViewModel budgetFragmentViewModel;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        BudgetFragmentBinding budgetFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.budget_fragment,container,false);
         budgetFragmentViewModel = new BudgetFragmentViewModel(this,budgetFragmentBinding);
        budgetFragmentViewModel.setComplexDetails();
        budgetFragmentViewModel.fabAddClick();
        budgetFragmentViewModel.leftArrowClick();
        budgetFragmentViewModel.rightArrowClick();
        budgetFragmentViewModel.expenseClick();
        budgetFragmentViewModel.incomeClick();
        budgetFragmentViewModel.lvTransactionClick();
        budgetFragmentBinding.layoutBudgetList.budgetToolbar.setOnMenuItemClickListener(this);
        budgetFragmentBinding.layoutBudgetList.budgetToolbar.inflateMenu(R.menu.menu_budget_creator);

        return budgetFragmentBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if (requestCode == Constants.REQUEST_BUDGET_CREATOR){
                Budget budget = (Budget) data.getSerializableExtra(ADD_BUDGET_KEY);
                if(budget!=null){
                    budgetFragmentViewModel.addNewBudget(budget);
                    budgetFragmentViewModel.getBudgets();
                }

            }else if(requestCode == Constants.REQUEST_EXPENSE_CREATOR){
                Expense expense = (Expense) data.getSerializableExtra(ADD_EXPENSE_KEY);
                if(expense!=null){
                   budgetFragmentViewModel.updateBudget(expense);
                   budgetFragmentViewModel.getBudgets();
                }
            }else if(requestCode == Constants.REQUEST_INCOME_CREATOR) {
                Income income = (Income) data.getSerializableExtra(ADD_INCOME_KEY);
                if(income!=null){
                    budgetFragmentViewModel.updateBudget(income);
                    budgetFragmentViewModel.getBudgets();
                }
            }else if(requestCode == Constants.REQUEST_INCOME_UPDATE){
                Income expense = (Income) data.getSerializableExtra(UPDATE_INCOME_KEY);
                if(expense!=null){
                    budgetFragmentViewModel.updateBudgetAfterEdit(expense);
                    budgetFragmentViewModel.getBudgets();
                }

            }else if(requestCode == Constants.REQUEST_EXPENSE_UPDATE){
                Expense expense = (Expense) data.getSerializableExtra(UPDATE_EXPENSE_KEY);
                if(expense!=null){
                    budgetFragmentViewModel.updateBudgetAfterEdit(expense);
                    budgetFragmentViewModel.getBudgets();
                }
            }
        }
    }





    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnHomeFragmentInteractionListener) {
            listener = (BudgetInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_delete:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Are you sure you want to delete this budget?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                budgetFragmentViewModel.deleteBudget();

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


                return true;
            case R.id.menu_main_addnew:
                Intent intent = new Intent(getContext(), BudgetCreatorActivity.class);
                startActivityForResult(intent, Constants.REQUEST_BUDGET_CREATOR);
                return true;
            case R.id.menu_main_pdf:
                budgetFragmentViewModel.createPDF();
                return true;
        }
        return false;
    }




    public interface BudgetInteractionListener{
        void onBudgetListener();
        void onBackButtonPressedBudget(String fragment);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            budgetFragmentViewModel.getBudgets();
        }
    }
}

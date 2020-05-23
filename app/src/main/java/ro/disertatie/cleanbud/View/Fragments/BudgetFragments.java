package ro.disertatie.cleanbud.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Activities.BudgetCreatorActivity;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.ViewModel.BudgetFragmentViewModel;
import ro.disertatie.cleanbud.databinding.BudgetFragmentBinding;
import ro.disertatie.cleanbud.databinding.ReportsFragmentBinding;

import static android.app.Activity.RESULT_OK;
import static ro.disertatie.cleanbud.View.Utils.Constants.ADD_BUDGET_KEY;

public class BudgetFragments extends Fragment {
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



        return budgetFragmentBinding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if (requestCode == Constants.REQUEST_BUDGET_CREATOR){
                Budget budget = (Budget) data.getSerializableExtra(ADD_BUDGET_KEY);
                if(budget!=null){
                    budgetFragmentViewModel.addNewBudget(budget);
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



    public interface BudgetInteractionListener{
        void onBudgetListener();
        void onBackButtonPressedBudget(String fragment);
    }
}

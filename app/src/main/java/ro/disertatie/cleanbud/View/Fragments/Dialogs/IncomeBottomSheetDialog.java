package ro.disertatie.cleanbud.View.Fragments.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.ViewModel.IncomeCreatorViewModel;
import ro.disertatie.cleanbud.databinding.BottomSheetBinding;

public class IncomeBottomSheetDialog extends BottomSheetDialogFragment  {
    private IncomeCreatorViewModel incomeCreatorViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.bottom_sheet,container,false);
        BottomSheetBinding budgetFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.bottom_sheet,container,false);
        incomeCreatorViewModel = new IncomeCreatorViewModel(this,budgetFragmentBinding);

        incomeCreatorViewModel.selectCategory();
        incomeCreatorViewModel.addIncomeDialog();

        return budgetFragmentBinding.getRoot();

    }




}

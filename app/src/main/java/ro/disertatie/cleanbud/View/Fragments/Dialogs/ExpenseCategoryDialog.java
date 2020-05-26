package ro.disertatie.cleanbud.View.Fragments.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Adapter.ExpensesCategoryAdapter;
import ro.disertatie.cleanbud.View.Models.ExpenseCategory;

public class ExpenseCategoryDialog extends DialogFragment {
    GridView gridView;
    Bundle bundle;
    Integer currentColor;
    Boolean status;
    OnCompleteListenerColor listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.expense_category_dialog, container, false);
        gridView = view.findViewById(R.id.gridView1);


        ExpensesCategoryAdapter expensesCategoryAdapter = new ExpensesCategoryAdapter(getContext(), ExpenseCategory.populateExpenseTypes());
        gridView.setAdapter(expensesCategoryAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onClickCategoryExpense(position);
                dismiss();
            }
        });


        return view;
    }
    public interface OnCompleteListenerColor{
        void onClickCategoryExpense(Integer position);
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            this.listener = (ExpenseCategoryDialog.OnCompleteListenerColor)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
}
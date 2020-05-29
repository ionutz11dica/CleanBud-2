package ro.disertatie.cleanbud.View.Fragments.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.EconomyBudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.EconomyBudgetMethods;
import ro.disertatie.cleanbud.View.Models.EconomyBudget;
import ro.disertatie.cleanbud.View.Uitility.Utility;
import ro.disertatie.cleanbud.View.Utils.StaticVar;

public class CreateEconomyBudgetDialog extends DialogFragment {
    private NumberPicker picker1;
    private TextInputEditText tieAmount;
    private Button btnSaved;
    private String[] pickerVals;
    private EconomyBudget economyBudget;

    private EconomyBudgetMethods economyBudgetMethods;
    private EconomyBudgetDAO economyBudgetDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.economy_budget_dialog,container,false);
        openDb();
        picker1 = v.findViewById(R.id.numberpicker_main_picker);
        tieAmount = v.findViewById(R.id.tie_amount_saved);
        btnSaved = v.findViewById(R.id.add_saving_budget);
        picker1.setMaxValue(2);
        picker1.setMinValue(0);
        pickerVals = new String[]{"5%","10%","15%"};
        picker1.setDisplayedValues(pickerVals);
        economyBudget = new EconomyBudget();
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = picker1.getValue();
                switch (valuePicker1) {
                    case 0:
                        economyBudget.setPercentage(5);
                        break;
                    case 1:
                        economyBudget.setPercentage(10);
                        break;
                    case 2:
                        economyBudget.setPercentage(15);
                        break;

                }
            }
        });


        btnSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    economyBudget.setAmount(Float.parseFloat(tieAmount.getText().toString()));
                    economyBudget.setUserId(StaticVar.USER_ID);
                    economyBudgetMethods.insertEconomyBudget(economyBudget);
                    Intent intent = new Intent();
                  getTargetFragment().onActivityResult(
                   getTargetRequestCode(), Activity.RESULT_OK, intent);
                   dismiss();
                }
            }
        });


        return v;
    }

    private boolean validate(){
        if(Utility.isEmptyOrNull(tieAmount.getText().toString())){
            tieAmount.setError("Invalid Input");
            return false;
        }

        return true;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
    void openDb(){
        economyBudgetDAO = AppRoomDatabase.getInstance(getContext()).economyBudgetDAO();
        economyBudgetMethods = EconomyBudgetMethods.getInstance(economyBudgetDAO);
    }

}

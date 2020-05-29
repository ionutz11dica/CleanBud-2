package ro.disertatie.cleanbud.View.ViewModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.ExpenseDAO;
import ro.disertatie.cleanbud.View.Database.DAO.IncomeDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.ExpenseMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.IncomeMethods;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.IncomeBottomSheetDialog;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.Income;
import ro.disertatie.cleanbud.View.Uitility.Utility;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.databinding.BottomSheetBinding;

public class IncomeCreatorViewModel {
    private IncomeBottomSheetDialog incomeBottomSheetDialog;
    private BottomSheetBinding bottomSheetBinding;
    private ImageButton[] buttons ;
    private IncomeMethods incomeMethods;
    private int selectedCateg = -1;
    private int incomeId = 0;
    private boolean isUpdate = false;
    private int budgetId = 0;
//    private IncomeBottomSheetDialog.BottomSheetListener listener;

    String myFormat = "dd-MM-yyyy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    public IncomeCreatorViewModel(IncomeBottomSheetDialog incomeBottomSheetDialog, BottomSheetBinding bottomSheetBinding) {
        this.incomeBottomSheetDialog = incomeBottomSheetDialog;
        this.bottomSheetBinding = bottomSheetBinding;
        openDB();
        buttons = new ImageButton[]{bottomSheetBinding.ibSalary,bottomSheetBinding.ibFree,bottomSheetBinding.ibSales,bottomSheetBinding.ibCash,bottomSheetBinding.ibRent};
    }


    private Income createIncome() throws ParseException {
        Income income = new Income();
        income.setTitleIncome(bottomSheetBinding.etBsTitleIncome.getText().toString());
        income.setAmountIncome(Float.parseFloat(bottomSheetBinding.etBsAmountIncome.getText().toString()));
        income.setDateIncome(sdf.parse(bottomSheetBinding.tieDateIncome.getText().toString()));
        income.setBudgetId(budgetId);
        income.setIncomeCategoryId(selectedCateg);
        return income;
    }

    public void setAlphaForButtons(int pos){
        for ( int i = 0 ;i < buttons.length;i++){
            if(pos != -1){
                if(pos == i){
                    buttons[i].setAlpha(0.5f);
                    selectedCateg = i;
                }else{
                    buttons[i].setAlpha(1f);
                }
            }else{
                buttons[i].setAlpha(1f);
            }

        }
    }

    public void selectCategory(){
        for(int i =0 ;i<buttons.length;i++){
            final int  pos = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedCateg == pos){
                        selectedCateg = -1;
                        setAlphaForButtons(selectedCateg);
                    }else{
                        selectedCateg = pos;
                        setAlphaForButtons(selectedCateg);

                    }



                }
            });
        }
    }


    public void addIncomeDialog(){
        bottomSheetBinding.addIncomeBs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    try {
                        sendResult();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else {

                }
            }
        });
    }



    public void fillIncome(Bundle bundle){
       if(bundle != null) {
           Income income = (Income) bundle.getSerializable(Constants.UPDATE_INCOME_KEY);
           int idbdg = bundle.getInt(Constants.BUDGET_ID_KEY,-1);
           if (income !=null){
              isUpdate = true;
              incomeId = income.getIncomeId();
              setAlphaForButtons(income.getIncomeCategoryId());
              bottomSheetBinding.etBsTitleIncome.setText(income.getTitleIncome());
              bottomSheetBinding.etBsAmountIncome.setText(String.valueOf(income.getAmountIncome()));
              bottomSheetBinding.tieDateIncome.setText(sdf.format(income.getDateIncome()));
           }
           if (idbdg != -1) {
               budgetId = idbdg;
           }
       }
    }



    private void sendResult() throws ParseException {
        Income income = createIncome();
        Intent intent = new Intent();
        if(isUpdate){
            income.setIncomeId(incomeId);
            intent.putExtra(Constants.UPDATE_INCOME_KEY,income);

        }else{
            intent.putExtra(Constants.ADD_INCOME_KEY,income);

        }

        incomeMethods.insertIncome(income);
        incomeBottomSheetDialog.getTargetFragment().onActivityResult(
                incomeBottomSheetDialog.getTargetRequestCode(), Activity.RESULT_OK, intent);
        incomeBottomSheetDialog.dismiss();
    }

    private boolean isValid() {
        if (Utility.isEmptyOrNull(bottomSheetBinding.etBsTitleIncome.getText().toString())) {
            bottomSheetBinding.etBsTitleIncome.setError("Invalid Input");
            return false;
        } else if (Utility.isEmptyOrNull(bottomSheetBinding.etBsAmountIncome.getText().toString())) {
            bottomSheetBinding.etBsAmountIncome.setError("Invalid Input");
            return false;
        } else if (Utility.isEmptyOrNull(bottomSheetBinding.tieDateIncome.getText().toString()) || !isValidDate(bottomSheetBinding.tieDateIncome.getText().toString())) {
            bottomSheetBinding.tieDateIncome.setError("Invalid Input");
            return false;
        }else if (selectedCateg == -1){
            Toast.makeText(incomeBottomSheetDialog.getContext(),"Select a category",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }
    private boolean isValidDate (String dateString){
        if(dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")){
            String[] t = dateString.split("-");
            return (Integer.parseInt(t[0]) <= 31 && Integer.parseInt(t[1]) <= 12 && Integer.parseInt(t[2]) <= 2100);

        }
        return false;
    }

    private void openDB(){
        IncomeDAO incomeDAO = AppRoomDatabase.getInstance(incomeBottomSheetDialog.getContext()).incomeDAO();
        incomeMethods = IncomeMethods.getInstance(incomeDAO);

    }


}

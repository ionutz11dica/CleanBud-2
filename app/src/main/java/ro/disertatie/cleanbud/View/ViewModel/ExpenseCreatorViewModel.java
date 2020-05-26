package ro.disertatie.cleanbud.View.ViewModel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ro.disertatie.cleanbud.View.Activities.ExpenseCreatorActivity;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAO.ExpenseDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.BudgetMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.ExpenseMethods;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.ExpenseCategoryDialog;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Uitility.Utility;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.databinding.ActivityExpenseCreatorBinding;

public class ExpenseCreatorViewModel {
    private ExpenseCreatorActivity expenseCreatorActivity;
    private ActivityExpenseCreatorBinding activityExpenseCreatorBinding;
    private final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private String myFormat = "dd,MMM,yyyy"; //In which you need put here
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private int budgetId;
    private int categId;
    private ExpenseMethods expenseMethods;


    public ExpenseCreatorViewModel(ExpenseCreatorActivity expenseCreatorActivity, ActivityExpenseCreatorBinding activityExpenseCreatorBinding) {
        this.expenseCreatorActivity = expenseCreatorActivity;
        this.activityExpenseCreatorBinding = activityExpenseCreatorBinding;
        openDB();
        setPickerDate();
    }

    public void parseDataFromScanner(String scannedData){
        if (scannedData.matches("(?i).*DATA.*")){
            Toast.makeText(expenseCreatorActivity.getApplicationContext(),"Merge",Toast.LENGTH_LONG).show();
        }
    }


    private void updateDateLabel(){


        activityExpenseCreatorBinding.tvDateExpense.setText(sdf.format(myCalendar.getTime()));
    }

    public void openDatePickerDialog(){
        activityExpenseCreatorBinding.addDateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(expenseCreatorActivity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    private void setPickerDate(){
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };
    }

    public void setReceiptImage(Bitmap bitmap) {
        activityExpenseCreatorBinding.imvReceipt.setImageBitmap(bitmap);
    }



    private boolean isValid(){
        if (Utility.isEmptyOrNull(activityExpenseCreatorBinding.etTitleExpense.getText().toString())){
            activityExpenseCreatorBinding.etTitleExpense.setError("Invalid Input");
            return false;
        } else if (Utility.isEmptyOrNull(activityExpenseCreatorBinding.etSumExpense.getText().toString())){
            activityExpenseCreatorBinding.etSumExpense.setError("Invalid Input");
            return false;
        }
        return true;
    }
    public void addDateExpenseClick(){
        activityExpenseCreatorBinding.addDateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
                //aici trebuie sa adaugam si in baza de date
                //si sa ducem si intentul inapoi
            }
        });
    }


    public void addExpenseCategory(){
        activityExpenseCreatorBinding.selectCategoryExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseCategoryDialog expenseCategoryDialog = new ExpenseCategoryDialog();
                expenseCategoryDialog.show(expenseCreatorActivity.getSupportFragmentManager(),"expcateg");
            }
        });
    }

    public void updateCategory(String categ) {
        activityExpenseCreatorBinding.tvExpenseCategory.setText(categ);
    }

    public void categoryId(int categ) {
         this.categId = categ;
    }

    public void addExpenseClick(){
        activityExpenseCreatorBinding.btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    try {
                       Expense expense = createExpense();
                       if (expense !=null){
                            expenseMethods.insertExpense(expense);
                           Intent intent = expenseCreatorActivity.getIntent();
                           intent.putExtra(Constants.ADD_EXPENSE_KEY,expense);
                            expenseCreatorActivity.setResult(Activity.RESULT_OK,intent);
                            expenseCreatorActivity.finish();
                       }else{

                       }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //aici trebuie sa adaugam si in baza de date
                //si sa ducem si intentul inapoi
            }
        });
    }

    private Expense createExpense() throws ParseException {
        if (budgetId == -1 ||this.categId == -1 ){
            return null;
        }
        Expense expense = new Expense();
        expense.setAmountExpense(Float.parseFloat(activityExpenseCreatorBinding.etSumExpense.getText().toString()));
        expense.setTitleExpense(activityExpenseCreatorBinding.etTitleExpense.getText().toString());

        expense.setBudgetId(budgetId);
        if(activityExpenseCreatorBinding.tvDateExpense.getText().toString().equals("dd,MMM,yyyy")){
            expense.setExpenseDate(new Date());
        }else{
            expense.setExpenseDate(sdf.parse(activityExpenseCreatorBinding.tvDateExpense.getText().toString()));

        }

        expense.setExpenseCategoryId(categId);
        expense.setDescription(activityExpenseCreatorBinding.etExpenseDescription.getText().toString());
        return expense;
    }

    private void openDB(){
        ExpenseDAO expenseDAO = AppRoomDatabase.getInstance(expenseCreatorActivity.getApplicationContext()).expenseDAO();
        expenseMethods = ExpenseMethods.getInstance(expenseDAO);

    }

}

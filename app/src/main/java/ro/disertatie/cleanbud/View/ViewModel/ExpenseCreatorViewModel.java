package ro.disertatie.cleanbud.View.ViewModel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ro.disertatie.cleanbud.View.Activities.ExpenseCreatorActivity;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.ExpenseDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.ExpenseMethods;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.ExpenseCategoryDialog;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.ExpenseCategory;
import ro.disertatie.cleanbud.View.Uitility.Utility;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.databinding.ActivityExpenseCreatorBinding;


public class ExpenseCreatorViewModel {
    private ExpenseCreatorActivity expenseCreatorActivity;
    private ActivityExpenseCreatorBinding activityExpenseCreatorBinding;
    private final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private String myFormat = "dd,MM,yyyy"; //In which you need put here
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private int budgetId;
    private int categId;
    private ExpenseMethods expenseMethods;
    private boolean isUpdate = false;
    private int expenseId = 0;

    public ExpenseCreatorViewModel(ExpenseCreatorActivity expenseCreatorActivity, ActivityExpenseCreatorBinding activityExpenseCreatorBinding) {
        this.expenseCreatorActivity = expenseCreatorActivity;
        this.activityExpenseCreatorBinding = activityExpenseCreatorBinding;
        openDB();
        setPickerDate();
    }

    public void parseDataFromScanner(String scannedData){

        String[] antrenament = new String[]{"TOTAL","DATA","SUBTOTAL","DATE"};
        String[] rows = scannedData.split("\n");
        String regex = "([0-9]{2}).([0-9]{2}).([0-9]{4})";

        for(int i = 0;i <rows.length;i++){
            Matcher matcher = Pattern.compile(regex).matcher(rows[i]);
            for(int j = 0 ;j <antrenament.length;j++){

            }
            if(matcher.find()){
//                Toast.makeText(expenseCreatorActivity.getApplicationContext(),rows[i].replace(),Toast.LENGTH_LONG).show();
            }
        }

        if (scannedData.matches("(?i).*TOTAL.*")){
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
                           if(isUpdate){
                               expense.setExpenseId(expenseId);
                               expenseMethods.insertExpense(expense);
                               Intent intent = expenseCreatorActivity.getIntent();
                               intent.putExtra(Constants.UPDATE_EXPENSE_KEY,expense);
                               expenseCreatorActivity.setResult(Activity.RESULT_OK,intent);
                               expenseCreatorActivity.finish();
                           }else{
                               expenseMethods.insertExpense(expense);
                               Intent intent = expenseCreatorActivity.getIntent();
                               intent.putExtra(Constants.ADD_EXPENSE_KEY,expense);
                               expenseCreatorActivity.setResult(Activity.RESULT_OK,intent);
                               expenseCreatorActivity.finish();
                           }

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
        if(activityExpenseCreatorBinding.tvDateExpense.getText().toString().equals("dd,MM,yyyy")){
            expense.setExpenseDate(new Date());
        }else{
            expense.setExpenseDate(sdf.parse(activityExpenseCreatorBinding.tvDateExpense.getText().toString()));

        }

        expense.setExpenseCategoryId(categId);
        expense.setDescription(activityExpenseCreatorBinding.etExpenseDescription.getText().toString());
        return expense;
    }


    public void fillComponentsForEdit(Intent intent){
        if(intent.hasExtra(Constants.UPDATE_EXPENSE_KEY)){
            isUpdate = true;
            Expense expense = (Expense) intent.getSerializableExtra(Constants.UPDATE_EXPENSE_KEY);

            DateFormat df = new SimpleDateFormat("dd,MM,yyyy");
            if(expense != null){
                expenseId = expense.getExpenseId();
                activityExpenseCreatorBinding.etTitleExpense.setText(expense.getTitleExpense());
                for(int i =0 ;i < ExpenseCategory.populateExpenseTypes().length;i++){
                    activityExpenseCreatorBinding.tvExpenseCategory.setText(ExpenseCategory.populateExpenseTypes()[i].getTitleExpCategory());
                }
                activityExpenseCreatorBinding.etSumExpense.setText(String.valueOf(expense.getAmountExpense()));
                activityExpenseCreatorBinding.etExpenseDescription.setText(expense.getDescription());
                activityExpenseCreatorBinding.tvDateExpense.setText(df.format(expense.getExpenseDate()));
            }
        }
    }

    private void openDB(){
        ExpenseDAO expenseDAO = AppRoomDatabase.getInstance(expenseCreatorActivity.getApplicationContext()).expenseDAO();
        expenseMethods = ExpenseMethods.getInstance(expenseDAO);

    }

}

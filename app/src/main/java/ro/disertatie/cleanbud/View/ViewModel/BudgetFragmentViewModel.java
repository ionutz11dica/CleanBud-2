package ro.disertatie.cleanbud.View.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.OnMenuItemClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Activities.BudgetCreatorActivity;
import ro.disertatie.cleanbud.View.Activities.ExpenseCreatorActivity;
import ro.disertatie.cleanbud.View.Adapter.BudgetTypeRecyclerViewAdapter;
import ro.disertatie.cleanbud.View.Adapter.RecordBudgetAdapter;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAO.ExpenseDAO;
import ro.disertatie.cleanbud.View.Database.DAO.IncomeDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.BudgetMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.ExpenseMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.IncomeMethods;
import ro.disertatie.cleanbud.View.Fragments.BudgetFragments;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.IncomeBottomSheetDialog;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.IconMenuItem;
import ro.disertatie.cleanbud.View.Models.Income;
import ro.disertatie.cleanbud.View.Models.Record;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.Utils.RecordProtocol;
import ro.disertatie.cleanbud.View.Utils.StaticVar;
import ro.disertatie.cleanbud.View.View.ProgressDialogClass;
import ro.disertatie.cleanbud.databinding.BudgetComplexDetailsBinding;
import ro.disertatie.cleanbud.databinding.BudgetFragmentBinding;

public class BudgetFragmentViewModel {
    private BudgetFragments budgetFragments;
    private BudgetFragmentBinding budgetFragmentBinding;
    private BudgetTypeRecyclerViewAdapter adapterBud;
    private BudgetMethods budgetMethods;
    private ExpenseMethods expenseMethods;
    private IncomeMethods incomeMethods;
    private List<Budget> budgetsList;
    private int cursorBudgets = 0;
    private ProgressDialogClass progressDialogClass;
    private BudgetFragments.BudgetInteractionListener listener;
    private BudgetComplexDetailsBinding complexDetailsBinding;
    private Bitmap scaledBmp;
    private  RecordBudgetAdapter recordBudgetAdapter;

    private int selectedPos = 0;


    private ArrayList<RecordProtocol> protocols = new ArrayList<>();
    private CustomPowerMenu customPowerMenu;

    public BudgetFragmentViewModel(BudgetFragments budgetFragments, BudgetFragmentBinding budgetFragmentBinding) {
        this.budgetFragments = budgetFragments;
        this.budgetFragmentBinding = budgetFragmentBinding;
        this.budgetsList = new ArrayList<>();
        this.progressDialogClass = new ProgressDialogClass(budgetFragments.getActivity());
        listener = (BudgetFragments.BudgetInteractionListener) budgetFragments.getContext();
        openDB();
        Bitmap bmp = BitmapFactory.decodeResource(budgetFragments.getResources(), R.drawable.imagepdf);
        scaledBmp = Bitmap.createScaledBitmap(bmp,1200,518,false);
//        getBudgets();
        RxJavaPlugins.setErrorHandler(throwable -> {});


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews(List<Budget> budgets){
        if (budgets.isEmpty()){
            initToolbar();

        }else{
            initBudgetsView();
            changeNextBudget();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initBudgetsView(){
        budgetFragmentBinding.llPlaceholder.setVisibility(View.GONE);
        budgetFragmentBinding.fabAddBudget.setVisibility(View.GONE);
        budgetFragmentBinding.budgetToolbar.setVisibility(View.GONE);
        complexDetailsBinding.budgetToolbar.setTitleTextAppearance(budgetFragments.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        complexDetailsBinding.budgetToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        budgetFragmentBinding.layoutBudgetList.getRoot().setVisibility(View.VISIBLE);







        backListener();
    }

    private void initRecords(List<Expense> expenses,List<Income> incomes){
        if (expenses!=null){
            this.protocols.addAll(expenses);
        }
        if(incomes !=null){
            this.protocols.addAll(incomes);

        }


            recordBudgetAdapter = new RecordBudgetAdapter(budgetFragments.getActivity(), protocols);


            budgetFragmentBinding.layoutBudgetList.recordLv.post(new Runnable() {
                @Override
                public void run() {
                    budgetFragmentBinding.layoutBudgetList.recordLv.setAdapter(recordBudgetAdapter);
                }
            });

    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        budgetFragmentBinding.budgetToolbar.setTitleTextAppearance(budgetFragments.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        budgetFragmentBinding.budgetToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        budgetFragmentBinding.budgetToolbar.setVisibility(View.VISIBLE);
        budgetFragmentBinding.llPlaceholder.setVisibility(View.VISIBLE);
        complexDetailsBinding.getRoot().setVisibility(View.GONE);
        budgetFragmentBinding.fabAddBudget.setVisibility(View.VISIBLE);
        backListenerNoList();
    }


    private void getBudgetsExpenses(Integer budgetId){
        progressDialogClass.showDialog("Fetching data..","Please wait");
        Single<List<Expense>> single = expenseMethods.getAllBudgetExpenses(budgetId);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Expense>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Expense> expenses) {
                        initRecords(expenses,null);
                        getBudgetsIncomes(budgetsList.get(cursorBudgets).getBudgetId());
                        progressDialogClass.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
     }

    private void getBudgetsIncomes(Integer budgetId){
        progressDialogClass.showDialog("Fetching data..","Please wait");
        Single<List<Income>> single = incomeMethods.getAllIncomes(budgetId);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Income>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Income> incomes) {
                        initRecords(null,incomes);
                        progressDialogClass.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        complexDetailsBinding.budgetToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressedBudget("homeF");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListenerNoList(){
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
                budgetFragments.startActivityForResult(intent, Constants.REQUEST_BUDGET_CREATOR);
            }
        });
    }

    public void leftArrowClick(){
        complexDetailsBinding.leftChangeArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cursorBudgets != 0){
                    cursorBudgets -= 1;
                    changeNextBudget();

                    getBudgets();

                }

            }
        });
    }

    private OnMenuItemClickListener<IconMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<IconMenuItem>() {
        @Override
        public void onItemClick(int position, IconMenuItem item) {
            Toast.makeText(budgetFragments.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            customPowerMenu.dismiss();
        }
    };

    public void expenseClick(){
        complexDetailsBinding.addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenu();
            }
        });
    }

    public void incomeClick(){
        complexDetailsBinding.addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment bottomSheetDialog = new IncomeBottomSheetDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BUDGET_ID_KEY,budgetsList.get(cursorBudgets).getBudgetId());
                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.setTargetFragment(budgetFragments,Constants.REQUEST_INCOME_CREATOR);
                bottomSheetDialog.show(budgetFragments.getActivity().getSupportFragmentManager(),"incomecreator");
//                bottomSheetDialog.show();
            }
        });
    }


    public void rightArrowClick(){
        complexDetailsBinding.rightChangeArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(budgetsList.size() != 1 && cursorBudgets != budgetsList.size() - 1){

                    cursorBudgets += 1;
                    changeNextBudget();
                    getBudgets();
                }
            }
        });

    }

    private void changeNextBudget(){
        if (!budgetsList.isEmpty())
            complexDetailsBinding.budgetToolbar.setTitle(budgetsList.get(cursorBudgets).getTitle());
        complexDetailsBinding.budgetSumTv.setText(String.format("%.1f",budgetsList.get(cursorBudgets).getCurrentAmount()));
//            complexDetailsBinding.budgetSumTv.setText(String.valueOf(Math.rbudgetsList.get(cursorBudgets).getCurrentAmount()));
            complexDetailsBinding.budgetLeftAmount.setText(String.valueOf(budgetsList.get(cursorBudgets).getInitialAmount()) +" initial");
            switch (budgetsList.get(cursorBudgets).getCurrencyId()){
                case 0:
                    complexDetailsBinding.budgetCurrencyTv.setText("$");
                    break;
                case 1:
                    complexDetailsBinding.budgetCurrencyTv.setText("LEI");
                    break;
                case 2:
                    complexDetailsBinding.budgetCurrencyTv.setText("\u20ac");
                    break;

            }

//        budgetFragmentBinding.layoutBudgetList.budgetCurrencyTv.setText(String.valueOf(budgetsList.get(cursorBudgets).getAmount()));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    public void updateBudget(RecordProtocol recordProtocol){
        Budget budget = budgetsList.get(cursorBudgets);

        if(recordProtocol instanceof Expense) {
            Expense expense = (Expense) recordProtocol;

            budget.setCurrentAmount(budget.getCurrentAmount() - expense.getAmountExpense());




        }else{
            Income income = (Income) recordProtocol;
            budget.setCurrentAmount(budget.getCurrentAmount() + income.getAmountIncome());
        }
        budgetMethods.updateBudget(budget);

    }


    public void updateBudgetAfterEdit(RecordProtocol recordProtocol){
        Budget budget = budgetsList.get(cursorBudgets);

        if(recordProtocol instanceof Expense) {
            Expense oldExp = (Expense) protocols.get(selectedPos);
            float oldExpAmount = oldExp.getAmountExpense();
            Expense expense = (Expense) recordProtocol;
            float newExpAmount = expense.getAmountExpense();

            float diff = oldExpAmount - newExpAmount;

                budget.setCurrentAmount(budget.getCurrentAmount() + diff);

        }else{

            Income oldExp = (Income) protocols.get(selectedPos);
            float oldExpAmount = oldExp.getAmountIncome();
            Income expense = (Income) recordProtocol;
            float newExpAmount = expense.getAmountIncome();

            float diff = newExpAmount - oldExpAmount;

            budget.setCurrentAmount(budget.getCurrentAmount() + diff);

        }
        budgetMethods.updateBudget(budget);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void deleteBudget(){
        budgetMethods.deleteBudget(budgetsList.get(cursorBudgets).getBudgetId());
        budgetsList.remove(budgetsList.get(cursorBudgets));

            initViews(budgetsList);

    }

    public void getBudgets(){
        protocols = new ArrayList<>();
        progressDialogClass.showDialog("Please wait..","Fetching data from database..");

        Single<List<Budget>> single = budgetMethods.getAllBudgets(StaticVar.USER_ID);
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
                                   getBudgetsExpenses(budgets.get(cursorBudgets).getBudgetId());


                                   initViews(budgets);

                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.d("Budget", e.getMessage());
                               }
                           }
                    );
    }


    public void lvTransactionClick(){
        budgetFragmentBinding.layoutBudgetList.recordLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                if(protocols.get(position) instanceof Expense) {
                    Expense expense = (Expense) protocols.get(position);
                    Intent intent = new Intent(budgetFragments.getContext(),ExpenseCreatorActivity.class);
                    intent.putExtra(Constants.UPDATE_EXPENSE_KEY,expense );
                    intent.putExtra(Constants.IS_SCAN_KEY,false);
                    intent.putExtra(Constants.BUDGET_ID_KEY,budgetsList.get(cursorBudgets).getBudgetId());

                    budgetFragments.startActivityForResult(intent,Constants.REQUEST_EXPENSE_UPDATE);
                }else {
                    Income income = (Income) protocols.get(position);
                    IncomeBottomSheetDialog bottomSheetDialog = new IncomeBottomSheetDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.BUDGET_ID_KEY,budgetsList.get(cursorBudgets).getBudgetId());
                    bundle.putSerializable(Constants.UPDATE_INCOME_KEY,income);
                    bottomSheetDialog.setArguments(bundle);
                    bottomSheetDialog.setTargetFragment(budgetFragments,Constants.REQUEST_INCOME_UPDATE);
                    bottomSheetDialog.show(budgetFragments.getActivity().getSupportFragmentManager(),"incomecreator");
                }
            }
        });
    }

    public void setComplexDetails(){
        complexDetailsBinding = DataBindingUtil.bind(budgetFragmentBinding.layoutBudgetList.getRoot());
    }

    private void createMenu(){
        PopupMenu popup = new PopupMenu(budgetFragments.getContext(), complexDetailsBinding.addExpense);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu_expense_type, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(budgetFragments.getActivity(), ExpenseCreatorActivity.class);
                intent.putExtra(Constants.BUDGET_ID_KEY,budgetsList.get(cursorBudgets).getBudgetId());
                intent.putExtra(Constants.CURRENT_BUDGET_KEY,budgetsList.get(cursorBudgets).getCurrentAmount());
                switch (item.getItemId()) {

                    case R.id.manual_menu_expense :
                        intent.putExtra(Constants.IS_SCAN_KEY,false);

                        budgetFragments.startActivityForResult(intent,Constants.REQUEST_EXPENSE_CREATOR);
                        break;

                    //fa ceva aici
                    case R.id.scan_menu_expense :
                        intent.putExtra(Constants.IS_SCAN_KEY,true);

                        budgetFragments.startActivityForResult(intent,Constants.REQUEST_EXPENSE_CREATOR);
                        break;
                        // sa ma duc pe scan
                        // sa fac crop de imagine sa o salvez in tel si sa iau uri
                }
                return true;
            }
        });

        popup.show(); //showing popup menu
    }




    public void createPDF(){
        int pageWidth = 1200;
        Date date = new Date();


        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth,2010,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(scaledBmp,0,0,paint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        titlePaint.setTextSize(70);


        paint.setColor(Color.rgb(0,113,118));
        paint.setTextSize(30f);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        titlePaint.setTextSize(70f);
        canvas.drawText("Resume "+budgetsList.get(cursorBudgets).getTitle(),pageWidth/2,500,titlePaint);


        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        paint.setTextSize(35f);
        canvas.drawText("User Name: " + "Madalina",20,590,paint);
        canvas.drawText("User Email: " + "madalina@yahoo.com",20,640,paint);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        canvas.drawText("Date C.: "+ dateFormat.format(date),pageWidth-330,640,paint);


        dateFormat = new SimpleDateFormat("HH:mm:ss");
        canvas.drawText("Hour : "+ dateFormat.format(date),pageWidth-330,690,paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(20,780,pageWidth-20,860,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("No.",40,830,paint);
        canvas.drawText("Title Record",200,830,paint);
        canvas.drawText("Date Expense",680,830,paint);
//        canvas.drawText("Cur. Amount",750,830,paint);
        canvas.drawText("Total",1050,830,paint);

        canvas.drawLine(180,790,180,840,paint);
        canvas.drawLine(680,790,680,840,paint);
//        canvas.drawLine(880,790,880,840,paint);
        canvas.drawLine(1030,790,1030,840,paint);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        int pos = 0;
        float total = 0.0f;
        float totalExpenses = 0.0f;
        float totalIncomes = 0.0f;
         for(int i = 0 ;i < protocols.size();i++){
             int constant = 60;
            if (protocols.get(i) instanceof Expense) {
                Expense expense = (Expense) protocols.get(i);
                if(i==0){
                    canvas.drawText(i +".",40,950,paint);
                    canvas.drawText(i +".",40,950,paint);
                    canvas.drawText(expense.getTitleExpense(),200,950,paint);
                    canvas.drawText(dateFormat.format(expense.getExpenseDate()),680,950,paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("-"+ expense.getAmountExpense(),pageWidth-40,950,paint);
                    paint.setTextAlign(Paint.Align.LEFT);
                    total -= expense.getAmountExpense();
                    totalExpenses += expense.getAmountExpense();
                }else{
                    pos = 950+i*constant;
                    canvas.drawText(i +".",40,pos,paint);
                    canvas.drawText(expense.getTitleExpense(),200,pos,paint);
                    canvas.drawText(dateFormat.format(expense.getExpenseDate()),680,pos,paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("-"+ expense.getAmountExpense(),pageWidth-40,pos,paint);
                    paint.setTextAlign(Paint.Align.LEFT);
                    total -= expense.getAmountExpense();
                    totalExpenses += expense.getAmountExpense();
                }

            }else{
                Income income = (Income) protocols.get(i);
                if(i==0){
                    canvas.drawText(i +".",40,950,paint);
                    canvas.drawText(i +".",40,950,paint);
                    canvas.drawText(income.getTitleIncome(),200,950,paint);
                    canvas.drawText(dateFormat.format(income.getDateIncome()),680,950,paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("+"+ income.getAmountIncome(),pageWidth-40,950,paint);
                    paint.setTextAlign(Paint.Align.LEFT);
                    total += income.getAmountIncome();
                    totalIncomes += income.getAmountIncome();
                }else{
                    pos = 950+i*constant;
                    canvas.drawText(i +".",40,pos,paint);
                    canvas.drawText(income.getTitleIncome(),200,pos,paint);
                    canvas.drawText(dateFormat.format(income.getDateIncome()),680,pos,paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("+"+ income.getAmountIncome(),pageWidth-50,pos,paint);
                    paint.setTextAlign(Paint.Align.LEFT);
                    total += income.getAmountIncome();
                    totalIncomes += income.getAmountIncome();

                }
            }
         }

         canvas.drawLine(680,pos+50,pageWidth - 20, pos+50,paint);
         canvas.drawText("Total Exp",700,pos+90,paint);
         canvas.drawText(":",900,pos+90,paint);
         paint.setTextAlign(Paint.Align.RIGHT);
         canvas.drawText("-"+ totalExpenses,pageWidth-40,pos+90,paint);

         paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Total Inc.",700,pos+150,paint);
        canvas.drawText(":",900,pos+150,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(String.valueOf(totalIncomes),pageWidth-40,pos+150,paint);
        paint.setTextAlign(Paint.Align.LEFT);

        paint.setColor(Color.rgb(247,147,30));
        canvas.drawRect(680,pos+200,pageWidth-20,pos+300,paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(50f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Total",700,pos+265,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(String.valueOf(total),pageWidth-40,pos+265,paint);


        pdfDocument.finishPage(page);

        String filename = Calendar.getInstance().getTimeInMillis() +".pdf";

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File file = new File(Environment.getExternalStorageDirectory(), "/" + filename);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        SharedPreferences sharedPreferences = budgetFragments.getActivity().getSharedPreferences(Constants.PREF_USER_LOGIN, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.EMAIL_PREF,"test@yahoo.com");
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Report Expenses-Incomes" );
        shareIntent.putExtra(Intent.EXTRA_TEXT, "You can find the information about your budget transactions in the attached PDF file.");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));



        budgetFragments.startActivity(Intent.createChooser(shareIntent,"Send email"));
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private void openDB(){
        BudgetDAO budgetDAO = AppRoomDatabase.getInstance(budgetFragments.getContext().getApplicationContext()).getBudgetDao();
        budgetMethods = BudgetMethods.getInstance(budgetDAO);
        ExpenseDAO expenseDAO = AppRoomDatabase.getInstance(budgetFragments.getContext().getApplicationContext()).expenseDAO();
        expenseMethods = ExpenseMethods.getInstance(expenseDAO);

        IncomeDAO incomeDAO = AppRoomDatabase.getInstance(budgetFragments.getContext().getApplicationContext()).incomeDAO();
        incomeMethods = IncomeMethods.getInstance(incomeDAO);
    }

}

package licenta.books.cleanbud.View.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.Adapter.BudgetTypeRecyclerViewAdapter;
import licenta.books.cleanbud.View.Models.BudgetType;
import licenta.books.cleanbud.View.Utils.SpinnerClass;

public class BudgetCreatorActivity extends AppCompatActivity implements  BudgetTypeRecyclerViewAdapter.ItemClickListener{
    @BindView(R.id.budget_creator_toolbar)
    Toolbar toolbar;

    @BindView(R.id.state_budget_image)
    FloatingActionButton fabState;

    @BindView(R.id.ed_budget_title)
    EditText etBudgetTitle;

    @BindView(R.id.et_budget_description)
    EditText etBudgetDescripton;

    @BindView(R.id.typeState_spn)
    Spinner spnTypeState;

    @BindView(R.id.budget_types_recycler)
    RecyclerView typesBudget;

    BudgetTypeRecyclerViewAdapter adapterBud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_creator);
        ButterKnife.bind(this);
        setUpToolbar();
        onActionChangeEt();

        List<SpinnerClass> spinnerClassList = new ArrayList<>();
        spinnerClassList.add(new SpinnerClass(R.drawable.ic_write,"In Progress"));
        spinnerClassList.add(new SpinnerClass(R.drawable.ic_paid,"Paid"));

        SpinnerAdapter adapter = new licenta.books.cleanbud.View.Adapter.SpinnerAdapter(BudgetCreatorActivity.this,
                R.layout.row_spinner, R.id.state_tv,spinnerClassList);
        spnTypeState.setAdapter(adapter);
        spnTypeState.setSelection(1);

        String month = getMonthForInt(Calendar.MONTH);

        etBudgetTitle.setText(month.substring(0,1).toUpperCase() + month.substring(1) + " " + Calendar.getInstance().get(Calendar.YEAR));


        List<BudgetType> budgetTypes = new ArrayList<>();
        budgetTypes.add(new BudgetType("Long Term Bud.",R.drawable.ic_long_term));
        budgetTypes.add(new BudgetType("Short Term Bud.",R.drawable.ic_short));
        budgetTypes.add(new BudgetType("Flexible  Bud.",R.drawable.ic_flexible));
        budgetTypes.add(new BudgetType("Financial Bud.",R.drawable.ic_help));
        budgetTypes.add(new BudgetType("Expense Bud.",R.drawable.ic_budget_expense));

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#e3b04b"));
        colors.add(Color.parseColor("#1089ff"));
        colors.add(Color.parseColor("#53d397"));

        colors.add(Color.parseColor("#f73859"));
        colors.add(Color.parseColor("#ef6c35"));


        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(BudgetCreatorActivity.this, LinearLayoutManager.HORIZONTAL, false);
        typesBudget.setLayoutManager(horizontalLayoutManagaer);
        adapterBud = new BudgetTypeRecyclerViewAdapter(this,colors,budgetTypes);
        adapterBud.setClickListener(this);
        typesBudget.setAdapter(adapterBud);
    }

    private void setUpToolbar() {
        toolbar.inflateMenu(R.menu.menu_budget_creator);

        toolbar.setTitleTextAppearance(getApplicationContext(),R.style.Widget_AppCompat_ActionBar_Solid);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
    }

    public void onActionChangeEt(){
        etBudgetDescripton.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do something, e.g. set your TextView here via .setText()
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }




    

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }


    @Override
    public void onItemClick(View view, int position) {
        view.setSelected(true);
    }
}

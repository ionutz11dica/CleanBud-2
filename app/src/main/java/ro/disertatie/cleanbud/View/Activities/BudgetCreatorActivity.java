package ro.disertatie.cleanbud.View.Activities;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.DataUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Adapter.BudgetTypeRecyclerViewAdapter;
import ro.disertatie.cleanbud.View.Models.BudgetType;
import ro.disertatie.cleanbud.View.Utils.SpinnerClass;
import ro.disertatie.cleanbud.View.ViewModel.BudgetCreatorViewModel;
import ro.disertatie.cleanbud.databinding.ActivityBudgetCreatorBinding;

public class BudgetCreatorActivity extends AppCompatActivity implements  BudgetTypeRecyclerViewAdapter.ItemClickListener{
    public  int selectedBudgetType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBudgetCreatorBinding activityBudgetCreatorBinding = DataBindingUtil.setContentView(this,R.layout.activity_budget_creator);
        BudgetCreatorViewModel viewModel = new BudgetCreatorViewModel(this,activityBudgetCreatorBinding);
        viewModel.onActionChangeEt();
        viewModel.setAdapterSpinner();
        viewModel.setBudgetTypeAdapter();
        viewModel.setDescriptionBudget();
        viewModel.setUpToolbar();
        viewModel.addNewBudgetOnClick(getIntent());
    }

    @Override
    public void onItemClick(View view, int position) {
        view.setSelected(true);
        Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
        view.setAlpha((float) 0.5);
        selectedBudgetType = position;
    }
}

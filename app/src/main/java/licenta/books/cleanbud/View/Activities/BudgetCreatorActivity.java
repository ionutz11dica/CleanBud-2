package licenta.books.cleanbud.View.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.Utils.SpinnerClass;

public class BudgetCreatorActivity extends AppCompatActivity {
    @BindView(R.id.budget_creator_toolbar)
    Toolbar toolbar;

    @BindView(R.id.state_budget_image)
    FloatingActionButton fabState;

    @BindView(R.id.ed_budget_title)
    EditText etBudgetTitle;

    @BindView(R.id.typeState_spn)
    Spinner spnTypeState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_creator);
        ButterKnife.bind(this);
        setUpToolbar();

        List<SpinnerClass> spinnerClassList = new ArrayList<>();
        spinnerClassList.add(new SpinnerClass(R.drawable.ic_write,"In Progress"));
        spinnerClassList.add(new SpinnerClass(R.drawable.ic_paid,"Paid"));

        SpinnerAdapter adapter = new licenta.books.cleanbud.View.Adapter.SpinnerAdapter(BudgetCreatorActivity.this,
                R.layout.row_spinner, R.id.state_tv,spinnerClassList);
        spnTypeState.setAdapter(adapter);
        spnTypeState.setSelection(1);

    }

    private void setUpToolbar() {
        toolbar.inflateMenu(R.menu.menu_budget_creator);

        toolbar.setTitleTextAppearance(getApplicationContext(),R.style.Widget_AppCompat_ActionBar_Solid);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
    }


}

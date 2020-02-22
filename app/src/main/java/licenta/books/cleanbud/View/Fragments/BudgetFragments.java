package licenta.books.cleanbud.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.Activities.BudgetCreatorActivity;
import licenta.books.cleanbud.View.Utils.Constants;

import static android.app.Activity.RESULT_OK;

public class BudgetFragments extends Fragment {
    private BudgetInteractionListener listener;

    @BindView(R.id.budget_toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_add_budget)
    FloatingActionButton fabAdd;

    @BindView(R.id.ll_placeholder)
    LinearLayout llPlaceHolder;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.budget_fragment, container, false);
        ButterKnife.bind(this,view);
        toolbar.setTitleTextAppearance(getContext(),R.style.Widget_AppCompat_ActionBar_Solid);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


        backListener();
        fabAddClick();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressedBudget("homeF");
            }
        });
    }


    private void fabAddClick(){
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BudgetCreatorActivity.class);
                Objects.requireNonNull(getActivity()).startActivityForResult(intent, Constants.REQUEST_BUDGET_CREATOR);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if (requestCode == Constants.REQUEST_BUDGET_CREATOR){
                // ------ for complete
            }
        }
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnHomeFragmentInteractionListener) {
            listener = (BudgetInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }



    public interface BudgetInteractionListener{
        void onBudgetListener();
        void onBackButtonPressedBudget(String fragment);
    }
}

package ro.disertatie.cleanbud.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.disertatie.cleanbud.R;

public class HomeFragment extends Fragment {

    //Delegate -> Listener
    private OnHomeFragmentInteractionListener listener;

    //Attributes
    @BindView(R.id.cv_myreports)
    CardView cvReports;

    @BindView(R.id.cv_myBudget)
    CardView cvBudgets;

    @BindView(R.id.cv_currency)
    CardView cvCurrency;

    @BindView(R.id.home_toolbar)
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        ButterKnife.bind(this,view);
        toolbar.setTitle("Home");
        toolbar.setTitleTextAppearance(getContext(),R.style.Widget_AppCompat_ActionBar_Solid);
        currencyClick();
        budgetsClick();
        myReportsClick();
        return view;
    }

    private void currencyClick(){
        cvCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHomeButtonsPressed(3);
            }
        });
    }

    private void myReportsClick(){
        cvReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHomeButtonsPressed(1);
            }
        });
    }

    private void budgetsClick(){
        cvBudgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHomeButtonsPressed(2);
            }
        });
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            listener = (OnHomeFragmentInteractionListener) context;
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

    public interface OnHomeFragmentInteractionListener{
        void onHomeButtonsPressed(int idBtn);
    }
}

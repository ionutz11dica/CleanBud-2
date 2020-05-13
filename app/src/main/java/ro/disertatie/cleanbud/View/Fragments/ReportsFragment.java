package ro.disertatie.cleanbud.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.databinding.ReportsFragmentBinding;

import static android.app.Activity.RESULT_OK;

public class ReportsFragment  extends Fragment {



    private ReportInteractionListener listener;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportsFragmentBinding reportsFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.reports_fragment,container,false);


        return reportsFragmentBinding.getRoot();
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
            listener = (ReportInteractionListener) context;
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



    public interface ReportInteractionListener{

    }
}

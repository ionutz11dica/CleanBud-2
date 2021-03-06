package ro.disertatie.cleanbud.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.ViewModel.AnalyticsViewModel;
import ro.disertatie.cleanbud.databinding.AnalyticsFragmentBinding;

public class AnalyticsFragment extends Fragment {
    private AnalyticsViewModel analyticsViewModel;
    private AnalyticsFragmentBinding analyticsFragmentBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         analyticsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.analytics_fragment,container,false);
        analyticsViewModel = new AnalyticsViewModel(this,analyticsFragmentBinding);
        analyticsViewModel.getDataForPieChart();
        analyticsViewModel.getDataForLinearGraph(0);
        analyticsViewModel.tabClick();
        analyticsViewModel.getDataForCubicLineGraphWeekly();
        return analyticsFragmentBinding.getRoot();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface AnalyticsListener{
        void backPressedAnalytics(String string);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            analyticsFragmentBinding.incomeVsExpense.setVisibility(View.GONE);
            analyticsViewModel.getDataForLinearGraph(analyticsViewModel.getCurrency());
            analyticsFragmentBinding.incomeVsExpense.setVisibility(View.VISIBLE);
            analyticsViewModel.getDataForPieChart();
            if(analyticsViewModel.isWeekly()){
                analyticsViewModel.getDataForCubicLineGraphWeekly();
            }else{
                analyticsViewModel.getDataForCubicLineGraph();
            }
        }
    }
}

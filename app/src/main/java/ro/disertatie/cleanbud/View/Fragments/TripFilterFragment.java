package ro.disertatie.cleanbud.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.ViewModel.TripsFilterViewModel;
import ro.disertatie.cleanbud.databinding.ReportsFragmentBinding;
import ro.disertatie.cleanbud.databinding.TripFilterFragmentBinding;

public class TripFilterFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TripFilterFragmentBinding tripFilterFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.trip_filter_fragment,container,false);
        TripsFilterViewModel tripsFilterViewModel = new TripsFilterViewModel(this,tripFilterFragmentBinding);
        tripsFilterViewModel.initProgressPrice();

        return tripFilterFragmentBinding.getRoot();
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnHomeFragmentInteractionListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

package ro.disertatie.cleanbud.View.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.CreateEconomyBudgetDialog;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.Utils.StaticVar;
import ro.disertatie.cleanbud.View.ViewModel.TripsFilterViewModel;
import ro.disertatie.cleanbud.databinding.TripFilterFragmentBinding;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TripFilterFragment extends DialogFragment implements Toolbar.OnMenuItemClickListener {

    private TripsFilterViewModel tripsFilterViewModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TripFilterFragmentBinding tripFilterFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.trip_filter_fragment,container,false);
         tripsFilterViewModel = new TripsFilterViewModel(this,tripFilterFragmentBinding);

        tripsFilterViewModel.openDb();
        tripsFilterViewModel.getSavings(StaticVar.USER_ID);
        tripsFilterViewModel.addNoGuest();
        tripsFilterViewModel.addNoRooms();
        tripsFilterViewModel.applyFilterClick();
        tripsFilterViewModel.minusNoGuest();
        tripsFilterViewModel.minusNoRooms();
        tripsFilterViewModel.openDatePickerDialog();
        tripsFilterViewModel.openDatePickerDialogDepart();


        tripFilterFragmentBinding.tripToolbar.setOnMenuItemClickListener(this);
       tripFilterFragmentBinding.tripToolbar.inflateMenu(R.menu.menu_reset_filter);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data != null){
            if (requestCode == Constants.REQUEST_EDIT_SAVING_BUDGET){
                float amount = data.getFloatExtra(Constants.AMOUNT_SAVINGS_KEY,0);
                tripsFilterViewModel.retrieveSavings(amount);
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_reset:
                tripsFilterViewModel.resetFilters();
                return true;
            case R.id.menu_main_fav:
                tripsFilterViewModel.getListener().onBackButtonPressedTripFilter("favoriteF");
                return true;
            case R.id.menu_main_edit:
                tripsFilterViewModel.editSavings();
                return true;
        }

        return false;
    }

    public interface TripFilterListener{
        void onBackButtonPressedTripFilter(String string);
        void passDataToHotels(ArrayList<ResultObjectHotel> list,String fragment);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            tripsFilterViewModel.getSavings(StaticVar.USER_ID);
        }
    }
}

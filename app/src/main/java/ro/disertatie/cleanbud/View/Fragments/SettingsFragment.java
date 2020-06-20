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

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.ViewModel.SettingsViewModel;
import ro.disertatie.cleanbud.databinding.ReportsFragmentBinding;
import ro.disertatie.cleanbud.databinding.SettingsFragmentBinding;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SettingsFragmentBinding settingsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment,container,false);
        SettingsViewModel settingsViewModel = new SettingsViewModel(this,settingsFragmentBinding);


        return settingsFragmentBinding.getRoot();
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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface SettingsListener{
        void backButtonSettings(String string);
    }
}

package ro.disertatie.cleanbud.View.ViewModel;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Fragments.SettingsFragment;
import ro.disertatie.cleanbud.databinding.SettingsFragmentBinding;

public class SettingsViewModel {
    private SettingsFragment.SettingsListener listener;
    private SettingsFragment settingsFragment;
    private SettingsFragmentBinding settingsFragmentBinding;

    public SettingsViewModel(SettingsFragment settingsFragment, SettingsFragmentBinding settingsFragmentBinding) {
        this.settingsFragment = settingsFragment;
        this.settingsFragmentBinding = settingsFragmentBinding;
        listener = (SettingsFragment.SettingsListener) settingsFragment.getActivity();
        initToolbar();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        settingsFragmentBinding.settingsToolbar.setTitleTextAppearance(settingsFragment.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        settingsFragmentBinding.settingsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        backListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        settingsFragmentBinding.settingsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backButtonSettings("homeF");
            }
        });
    }
}

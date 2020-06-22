package ro.disertatie.cleanbud.View.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.RequiresApi;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Fragments.SettingsFragment;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.databinding.SettingsFragmentBinding;

public class SettingsViewModel {
    private SettingsFragment.SettingsListener listener;
    private SettingsFragment settingsFragment;
    private SettingsFragmentBinding settingsFragmentBinding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingsViewModel(SettingsFragment settingsFragment, SettingsFragmentBinding settingsFragmentBinding) {
        this.settingsFragment = settingsFragment;
        this.settingsFragmentBinding = settingsFragmentBinding;
        listener = (SettingsFragment.SettingsListener) settingsFragment.getActivity();
        initToolbar();
        setToggleButtonClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        settingsFragmentBinding.settingsToolbar.setTitleTextAppearance(settingsFragment.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        settingsFragmentBinding.settingsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        sharedPreferences = settingsFragment.getActivity().getSharedPreferences(Constants.NOTIFICATION_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isChecked = sharedPreferences.getBoolean(Constants.NOTIFICATION_KEY,true);
        if (isChecked){
            settingsFragmentBinding.notificationSwitch.setChecked(true);
        }else{
            settingsFragmentBinding.notificationSwitch.setChecked(false);

        }
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

    private void setToggleButtonClick(){
        settingsFragmentBinding.notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean(Constants.NOTIFICATION_KEY, true);
                }else{
                    editor.putBoolean(Constants.NOTIFICATION_KEY, false);
                }
                editor.apply();
            }
        });
    }
}

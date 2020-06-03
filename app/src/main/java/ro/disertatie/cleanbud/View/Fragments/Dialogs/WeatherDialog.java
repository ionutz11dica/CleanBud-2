package ro.disertatie.cleanbud.View.Fragments.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Models.ApiModels.Weather.WeatherResponse;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.ViewModel.WeatherViewModel;
import ro.disertatie.cleanbud.databinding.WeatherDetailsDialogBinding;

import static ro.disertatie.cleanbud.View.Uitility.Utility.dateConverter;
import static ro.disertatie.cleanbud.View.Uitility.Utility.timeConverter;

public class WeatherDialog extends DialogFragment {
    private WeatherViewModel weatherViewModel;
    private WeatherDetailsDialogBinding weatherDetailsDialogBinding;

    private double latitude = 0.0;
    private double longitude = 0.0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         weatherDetailsDialogBinding = DataBindingUtil.inflate(inflater,R.layout.weather_details_dialog,container,false);

        return weatherDetailsDialogBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        if(bundle!=null){
            latitude = bundle.getDouble(Constants.LAT_KEY);
            longitude = bundle.getDouble(Constants.LON_KEY);
        }
        weatherViewModel.getWeatherDataByLatLon(String.valueOf(latitude),String.valueOf(longitude),Constants.METRIC);

        weatherViewModel.locationData.observe(getViewLifecycleOwner(), new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                weatherDetailsDialogBinding.lytLocation.setVisibility(View.VISIBLE);
                String[] day = new String[]{"01d","02d","03d","04d","09d","10d","11d","13d","50d"};
                String[] night = new String[]{ "01n","02n","03n","04n","09n","10n","11n","13n","50n"};

                for(int i = 0; i <day.length;i++){
                    if(weatherResponse.getWeather().get(0).getIcon().equals(day[i])){
                        weatherDetailsDialogBinding.lytLocation.setBackgroundResource( weatherDetailsDialogBinding.lytLocation.getResources().getIdentifier("ic_background_daylight","drawable",  weatherDetailsDialogBinding.lytLocation.getContext().getPackageName()));

                    }else if(weatherResponse.getWeather().get(0).getIcon().equals(night[i])){
                        weatherDetailsDialogBinding.lytLocation.setBackgroundResource( weatherDetailsDialogBinding.lytLocation.getResources().getIdentifier("ic_background_night","drawable",  weatherDetailsDialogBinding.lytLocation.getContext().getPackageName()));

                    }
                }

                weatherDetailsDialogBinding.setLocationGPS(weatherResponse);
                weatherDetailsDialogBinding.tvDate.setText(dateConverter());
                weatherDetailsDialogBinding.tvTemperature.setText(String.valueOf(Math.round(weatherResponse.getMain().getTemp())));
                weatherDetailsDialogBinding.tvSunrise.setText(timeConverter(weatherResponse.getSys().getSunrise()));
                weatherDetailsDialogBinding.tvSunset.setText(timeConverter(weatherResponse.getSys().getSunset()));
                weatherDetailsDialogBinding.imgState.setImageResource(getResources().getIdentifier("ic_"+weatherResponse.getWeather().get(0).getIcon(), "drawable", view.getContext().getPackageName()));

            }
        });

        weatherViewModel.locationLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    weatherDetailsDialogBinding.locationLoading.setVisibility(View.VISIBLE);
                    weatherDetailsDialogBinding.lytLocation.setVisibility(View.GONE);
                }else{
                    weatherDetailsDialogBinding.locationLoading.setVisibility(View.GONE);

                }
            }
        });

    }
}

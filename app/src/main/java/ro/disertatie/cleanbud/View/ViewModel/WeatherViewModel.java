package ro.disertatie.cleanbud.View.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.WeatherDialog;
import ro.disertatie.cleanbud.databinding.WeatherDetailsDialogBinding;
import ro.disertatie.cleanbud.View.API.APIClient;
import ro.disertatie.cleanbud.View.API.APIService;
import ro.disertatie.cleanbud.View.Models.ApiModels.Weather.WeatherResponse;

public class WeatherViewModel  extends AndroidViewModel{

    private APIService apiService = APIClient.getRetrofit4().create(APIService.class);

    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<WeatherResponse> locationData =new MutableLiveData<WeatherResponse>();
    public MutableLiveData<Boolean> locationLoading =new MutableLiveData<Boolean>();

    public WeatherViewModel(@NonNull Application application) {
        super(application);
    }


    public void getWeatherDataByLatLon(String lat, String lon, String units){
        locationLoading.setValue(true);
        disposable.add(apiService.getWeatherByGPS(lat,lon,units)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<WeatherResponse>() {
                @Override
                public void onSuccess(WeatherResponse weatherResponse) {
                    locationData.setValue(weatherResponse);
                    locationLoading.setValue(false);
                }

                @Override
                public void onError(Throwable e) {

                }
            })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

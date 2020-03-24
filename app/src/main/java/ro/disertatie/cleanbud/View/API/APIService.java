package ro.disertatie.cleanbud.View.API;

import ro.disertatie.cleanbud.View.Utils.CurrencyApi;
import retrofit2.http.GET;
import retrofit2.Call;

public interface APIService {


    @GET("latest")
    Call<CurrencyApi> getCurrencyRates();

}

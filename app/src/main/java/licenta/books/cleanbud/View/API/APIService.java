package licenta.books.cleanbud.View.API;

import licenta.books.cleanbud.View.Utils.CurrencyApi;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.Call;

public interface APIService {


    @GET("latest?access_key=d101c84835106030cf82eaa709767382")
    Call<CurrencyApi> getCurrencyRates();

}

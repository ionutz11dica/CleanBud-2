package ro.disertatie.cleanbud.View.API;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.DataHotel;
import ro.disertatie.cleanbud.View.Models.ApiModels.Location.DataLocation;
import ro.disertatie.cleanbud.View.Utils.CurrencyApi;
import retrofit2.http.GET;
import retrofit2.Call;

public interface APIService {


    @GET("latest")
    Call<CurrencyApi> getCurrencyRates();

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadAttachment(@Part MultipartBody.Part filePart);

    //Trip Advisor
    @Headers({"x-rapidapi-host:tripadvisor1.p.rapidapi.com","x-rapidapi-key:57f292b33cmsh4de81391e4cadfap19928bjsne92187793d2d"})
    @GET("locations/search")
    Call<DataLocation> getLocations(@Query("limit") String limit, @Query("sort")String sort, @Query("query") String query);

    @Headers({"x-rapidapi-host:tripadvisor1.p.rapidapi.com","x-rapidapi-key:57f292b33cmsh4de81391e4cadfap19928bjsne92187793d2d"})
    @GET("hotels/list")
    Call<DataHotel> getHotels(@QueryMap Map<String,String> options);

}

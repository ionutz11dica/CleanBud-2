package ro.disertatie.cleanbud.View.API;

import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import ro.disertatie.cleanbud.View.Utils.CurrencyApi;
import retrofit2.http.GET;
import retrofit2.Call;

public interface APIService {


    @GET("latest")
    Call<CurrencyApi> getCurrencyRates();

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadAttachment(@Part MultipartBody.Part filePart);

}

package ro.disertatie.cleanbud.View.API;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final int DEFAULT_TIMEOUT = 15;
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;



    public static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()

                    .baseUrl("https://api.exchangeratesapi.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static synchronized Retrofit getRetrofit2() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.6:5000/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }

}

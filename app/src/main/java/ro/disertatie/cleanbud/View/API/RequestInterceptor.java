package ro.disertatie.cleanbud.View.API;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.Url;
import ro.disertatie.cleanbud.View.Utils.Constants;

public class RequestInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originalReq = chain.request();
        HttpUrl originalHttpUrl = originalReq.url();

        HttpUrl url = originalHttpUrl.newBuilder().addQueryParameter("appid", Constants.API_KEY).build();
        Request request = originalReq.newBuilder().url(url).build();

        return chain.proceed(request);
    }
}

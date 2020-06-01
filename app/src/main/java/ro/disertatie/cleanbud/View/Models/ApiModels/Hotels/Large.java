package ro.disertatie.cleanbud.View.Models.ApiModels.Hotels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Large implements Serializable {
    @SerializedName("url")
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

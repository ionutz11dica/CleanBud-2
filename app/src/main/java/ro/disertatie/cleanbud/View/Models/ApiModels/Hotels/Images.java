package ro.disertatie.cleanbud.View.Models.ApiModels.Hotels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.Large;

public class Images implements Serializable {
    @SerializedName("large")
    Large large;

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }
}

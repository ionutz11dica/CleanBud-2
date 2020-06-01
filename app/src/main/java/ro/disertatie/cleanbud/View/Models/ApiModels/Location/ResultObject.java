package ro.disertatie.cleanbud.View.Models.ApiModels.Location;

import com.google.gson.annotations.SerializedName;

public class ResultObject {
    @SerializedName("location_id")
    private
    String location_id;


    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }
}

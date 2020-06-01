package ro.disertatie.cleanbud.View.Models.ApiModels.Hotels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HotelDetails  {
    @SerializedName("location_id")
    String location_id;
    @SerializedName("name")
    String name;

    @SerializedName("location_string")
    String locationString;

    Photo photo;

    @SerializedName("price")
    String price;


}

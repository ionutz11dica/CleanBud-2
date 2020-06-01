package ro.disertatie.cleanbud.View.Models.ApiModels.Hotels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultObjectHotel implements Serializable {
    @SerializedName("location_id")
    String location_id;
    @SerializedName("name")
    String name;

    @SerializedName("location_string")
    String locationString;

    Photo photo;

    @SerializedName("price")
    String price;


    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

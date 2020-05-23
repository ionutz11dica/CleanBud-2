package ro.disertatie.cleanbud.View.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip")
public class Trip {

    @PrimaryKey
    private int tripId;
    private float price;
    private String location;
    private String description;
    private String url;

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

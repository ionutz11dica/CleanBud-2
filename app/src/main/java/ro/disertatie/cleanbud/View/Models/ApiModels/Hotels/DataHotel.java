package ro.disertatie.cleanbud.View.Models.ApiModels.Hotels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DataHotel implements Serializable {
    @SerializedName("data")
    ArrayList<ResultObjectHotel> list;

    public ArrayList<ResultObjectHotel> getList() {
        return list;
    }

    public void setList(ArrayList<ResultObjectHotel> list) {
        this.list = list;
    }
}

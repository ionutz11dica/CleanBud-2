package ro.disertatie.cleanbud.View.Models.ApiModels.Location;

import com.google.gson.annotations.SerializedName;

public class DataLocation {
    @SerializedName("data")
    ResponseObject[] resultObject;

    public ResponseObject[] getResultObject() {
        return resultObject;
    }

    public void setResultObject(ResponseObject[] resultObject) {
        this.resultObject = resultObject;
    }
}

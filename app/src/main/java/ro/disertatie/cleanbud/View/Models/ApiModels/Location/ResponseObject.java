package ro.disertatie.cleanbud.View.Models.ApiModels.Location;

import com.google.gson.annotations.SerializedName;

public class ResponseObject {
    @SerializedName("result_object")
    ResultObject resultObject;

    public ResultObject getResultObject() {
        return resultObject;
    }

    public void setResultObject(ResultObject resultObject) {
        this.resultObject = resultObject;
    }
}

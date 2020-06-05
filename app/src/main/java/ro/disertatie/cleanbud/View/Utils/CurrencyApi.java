package ro.disertatie.cleanbud.View.Utils;

import androidx.room.Ignore;

import java.util.HashMap;

public class CurrencyApi {
    private Boolean success;
    private long timestamp;
    private String base;
    private String date;
    private HashMap<String,Float> rates;

    public CurrencyApi(Boolean success, long timestamp, String base, String date, HashMap<String, Float> rates) {
        this.success = success;
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }



    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, Float> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Float> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyApi{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}

package ro.disertatie.cleanbud.View.Models;


import android.content.Context;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import ro.disertatie.cleanbud.View.Database.DAOMethods.CurrencyMethods;

@Entity(tableName = "currency")
public class Currency {

    @PrimaryKey
    private Integer currencyId;
    private String currencyName;
    private String currencyShortCut;
    private int drawable;
    private float value;


    public Currency(Integer id, String currencyName, String currencyShortCut, int drawable, float value) {
        this.currencyId = id;
        this.currencyName = currencyName;
        this.currencyShortCut = currencyShortCut;
        this.drawable = drawable;
        this.value = value;
    }

    public Currency() {
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }


    public String getCurrencyShortCut() {
        return currencyShortCut;
    }

    public void setCurrencyShortCut(String currencyShortCut) {
        this.currencyShortCut = currencyShortCut;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }


}

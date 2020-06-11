package ro.disertatie.cleanbud.View.Models;

import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;

public class ExpensePOJO implements Serializable {
    private Integer budgetId;
    private float expAmount;
//    private float incAmount;


    @TypeConverters({TimestampConverter.class})
    private Date timestampExp;


    public Date getTimestampExp() {
        return timestampExp;
    }

    public void setTimestampExp(Date timestampExp) {
        this.timestampExp = timestampExp;
    }

    public ExpensePOJO() {
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public float getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(float expAmount) {
        this.expAmount = expAmount;
    }

//    public float getIncAmount() {
//        return incAmount;
//    }
//
//    public void setIncAmount(float incAmount) {
//        this.incAmount = incAmount;
//    }
}

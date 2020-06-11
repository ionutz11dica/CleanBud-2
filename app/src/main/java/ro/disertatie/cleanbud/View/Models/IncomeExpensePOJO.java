package ro.disertatie.cleanbud.View.Models;

import java.io.Serializable;

public class IncomeExpensePOJO implements Serializable {
    private Integer budgetId;
    private float expAmount;
    private float incAmount;


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

    public float getIncAmount() {
        return incAmount;
    }

    public void setIncAmount(float incAmount) {
        this.incAmount = incAmount;
    }
}

package ro.disertatie.cleanbud.View.Models;

import java.io.Serializable;

public class BudgetPOJO implements Serializable {
    private int currencyId;
    private float amountSum;

    public BudgetPOJO() {
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public float getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(float amountSum) {
        this.amountSum = amountSum;
    }
}

package ro.disertatie.cleanbud.View.Models;


import java.util.Date;

public class Income {

    long id;
    private float amountIncome;
    private Date dateIncome;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmountIncome() {
        return amountIncome;
    }

    public void setAmountIncome(float amountIncome) {
        this.amountIncome = amountIncome;
    }

    public Date getDateIncome() {
        return dateIncome;
    }

    public void setDateIncome(Date dateIncome) {
        this.dateIncome = dateIncome;
    }
}

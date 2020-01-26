package licenta.books.cleanbud.View.Models;


import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Income {
    @Id
    long id;
    private float amountIncome;
    private Date dateIncome;

    private ToOne<IncomeType> incomeType;
    ToOne<Record> record;

    public ToOne<IncomeType> getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(ToOne<IncomeType> incomeTypeToOne) {
        this.incomeType = incomeTypeToOne;
    }

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

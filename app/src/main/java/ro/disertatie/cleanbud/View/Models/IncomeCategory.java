package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "income_category")
public class IncomeCategory {

    @PrimaryKey(autoGenerate = true)
    private Integer incomeCategoryId;
    private String titleIncome;


    public Integer getIncomeCategoryId() {
        return incomeCategoryId;
    }

    public void setIncomeCategoryId(Integer incomeCategoryId) {
        this.incomeCategoryId = incomeCategoryId;
    }

    public String getTitleIncome() {
        return titleIncome;
    }

    public void setTitleIncome(String titleIncome) {
        this.titleIncome = titleIncome;
    }
}

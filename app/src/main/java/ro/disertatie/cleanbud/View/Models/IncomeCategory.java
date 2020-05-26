package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ro.disertatie.cleanbud.R;

@Entity(tableName = "income_category")
public class IncomeCategory {

    @PrimaryKey(autoGenerate = true)
    private Integer incomeCategoryId;
    private String titleIncome;
    private Integer imgIncome;

    public IncomeCategory(Integer incomeCategoryId, String titleIncome, Integer imgIncome) {
        this.incomeCategoryId = incomeCategoryId;
        this.titleIncome = titleIncome;
        this.imgIncome = imgIncome;
    }


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




    public Integer getImgIncome() {
        return imgIncome;
    }

    public void setImgIncome(Integer imgIncome) {
        this.imgIncome = imgIncome;
    }


    public static IncomeCategory[] populateIncomeTypes() {
        return new IncomeCategory[]{
                new IncomeCategory(0, "Salary", R.drawable.ic_payment),
                new IncomeCategory(1, "Freelancing", R.drawable.ic_work_from_home),
                new IncomeCategory(2, "Sales", R.drawable.ic_discount),
                new IncomeCategory(3, "CashBack", R.drawable.ic_cashback),
                new IncomeCategory(4, "Rent", R.drawable.ic_rent)
        };
    }
}

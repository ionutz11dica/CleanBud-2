package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ro.disertatie.cleanbud.R;

@Entity(tableName = "budgetType")
public class BudgetType {
    @PrimaryKey
    private Integer budgetTypeId;
    private String title;
    private int idImage;


    public BudgetType(Integer budgetTypeId, String title, int idImage) {
        this.budgetTypeId = budgetTypeId;
        this.title = title;
        this.idImage = idImage;
    }

    public Integer getBudgetTypeId() {
        return budgetTypeId;
    }

    public void setBudgetTypeId(Integer budgetTypeId) {
        this.budgetTypeId = budgetTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public static BudgetType[] populateBudgetTypes(){
        return new BudgetType[] {
                new BudgetType(0,"Long Term Bud.",R.drawable.ic_long_term),
                new BudgetType(1,"Short Term Bud.",R.drawable.ic_short),
                new BudgetType(2,"Flexible  Bud.",R.drawable.ic_flexible),
                new BudgetType(3,"Financial Bud.",R.drawable.ic_help),
                new BudgetType(4,"Expense Bud.",R.drawable.ic_budget_expense)
        };
    }


}

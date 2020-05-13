package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_category")
public class ExpenseCategory {
    @PrimaryKey(autoGenerate = true)
    private Integer expenseCategoryId;
    private String titleExpCategory;


    public Integer getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(Integer expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public String getTitleExpCategory() {
        return titleExpCategory;
    }

    public void setTitleExpCategory(String titleExpCategory) {
        this.titleExpCategory = titleExpCategory;
    }
}

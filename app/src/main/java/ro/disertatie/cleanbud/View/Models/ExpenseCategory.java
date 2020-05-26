package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ro.disertatie.cleanbud.R;

@Entity(tableName = "expense_category")
public class ExpenseCategory {
    @PrimaryKey(autoGenerate = true)
    private Integer expenseCategoryId;
    private String titleExpCategory;
    private int imageId;

    public ExpenseCategory(Integer expenseCategoryId, String titleExpCategory, int imageId) {
        this.expenseCategoryId = expenseCategoryId;
        this.titleExpCategory = titleExpCategory;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

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

    public static ExpenseCategory[] populateExpenseTypes(){
        return new ExpenseCategory[] {
                new ExpenseCategory(0,"Mobile", R.drawable.ic_smartphone),
                new ExpenseCategory(1,"Internet",R.drawable.ic_internet),
                new ExpenseCategory(2,"Cable Tv",R.drawable.ic_screen),
                new ExpenseCategory(3,"Entertain.",R.drawable.ic_film),
                new ExpenseCategory(4,"Electric",R.drawable.ic_idea),
                new ExpenseCategory(5,"School",R.drawable.ic_book),
                new ExpenseCategory(6,"Donation",R.drawable.ic_heart),
                new ExpenseCategory(7,"Merchants",R.drawable.ic_grocery_bag),
                new ExpenseCategory(8,"Utility",R.drawable.ic_crafts)
        };
    }
}

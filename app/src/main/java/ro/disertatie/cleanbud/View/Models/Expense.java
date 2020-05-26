package ro.disertatie.cleanbud.View.Models;




import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;
import ro.disertatie.cleanbud.View.Models.Converters.UriConverters;
import ro.disertatie.cleanbud.View.Utils.RecordProtocol;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "expense",
        foreignKeys = {
                @ForeignKey(
                        entity = Budget.class,
                        parentColumns = "budgetId",
                        childColumns = "budgetId",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = ExpenseCategory.class,
                        parentColumns = "expenseCategoryId",
                        childColumns = "expenseCategoryId")
        },
        indices = {
                @Index(value = "expenseCategoryId"),
                @Index(value = "budgetId")
        })
public class Expense implements RecordProtocol, Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer expenseId;
    private float amountExpense;
    private String titleExpense;
    @TypeConverters({TimestampConverter.class})
    private Date expenseDate;
    @Nullable
    private String imageUri;
    private String description;
    private Integer expenseCategoryId;
    private Integer budgetId;

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    @Nullable
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(@Nullable String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmountExpense() {
        return amountExpense;
    }

    public void setAmountExpense(float amountExpense) {
        this.amountExpense = amountExpense;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public String getTitleExpense() {
        return titleExpense;
    }

    public void setTitleExpense(String titleExpense) {
        this.titleExpense = titleExpense;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Integer getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(Integer expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    @Override
    public boolean checkRecordType(int bool) {
        return bool != 0;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", amountExpense=" + amountExpense +
                ", titleExpense='" + titleExpense + '\'' +
                ", expenseDate=" + expenseDate +
                ", imageUri='" + imageUri + '\'' +
                ", description='" + description + '\'' +
                ", expenseCategoryId=" + expenseCategoryId +
                ", budgetId=" + budgetId +
                '}';
    }
}

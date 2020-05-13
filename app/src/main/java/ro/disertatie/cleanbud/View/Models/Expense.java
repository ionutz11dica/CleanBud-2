package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "expense",
        foreignKeys = @ForeignKey(entity = Record.class,
                parentColumns = "expenseCategoryId",
                childColumns = "expenseCategoryId"

        ),
        indices = {@Index(value = {"expenseCategoryId"},unique = true)})
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private Integer expenseId;
    private float amountExpense;
    @TypeConverters({TimestampConverter.class})
    private Date expenseDate;
    private Integer expenseCategoryId;
    private Integer recordId;

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
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

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Integer getExpenseCategoryId() {
        return expenseCategoryId;
    }

    public void setExpenseCategoryId(Integer expenseCategoryId) {
        this.expenseCategoryId = expenseCategoryId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
}

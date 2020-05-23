package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;
import ro.disertatie.cleanbud.View.Utils.RecordProtocol;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "expense",
        foreignKeys = {
                @ForeignKey(
                        entity = Record.class,
                        parentColumns = "recordId",
                        childColumns = "recordId",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = ExpenseCategory.class,
                        parentColumns = "expenseCategoryId",
                        childColumns = "expenseCategoryId")
        },
        indices = {
                @Index(value = "expenseCategoryId"),
                @Index(value = "recordId")
        })
public class Expense implements RecordProtocol {

    @PrimaryKey(autoGenerate = true)
    private Integer expenseId;
    private float amountExpense;
    private String titleExpense;
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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Override
    public boolean checkRecordType(int bool) {
        return bool != 0;
    }
}

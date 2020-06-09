package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "budget",
        foreignKeys = {
            @ForeignKey(
                    entity = User.class,
                    parentColumns = "userId",
                    childColumns = "userId",
                    onDelete = CASCADE
            ),
            @ForeignKey(
                    entity = BudgetType.class,
                    parentColumns = "budgetTypeId",
                    childColumns = "budgetTypeId"
            ),
            @ForeignKey(

                    entity = Currency.class,
                    parentColumns = "currencyId",
                    childColumns = "currencyId")
        },
        indices = {
                @Index(value = "userId"),
                @Index(value = "budgetTypeId"),
                @Index(value = "currencyId")
        })
public class Budget implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer budgetId;
    private String title;
    private float initialAmount;
    private float currentAmount;
    @TypeConverters({TimestampConverter.class})
    private Date date;
    private Integer userId;
    private String budgetDescription;
    private Integer budgetTypeId;
    private Integer currencyId;


    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(float initialAmount) {
        this.initialAmount = initialAmount;
    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(float currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBudgetDescription() {
        return budgetDescription;
    }

    public void setBudgetDescription(String budgetDescription) {
        this.budgetDescription = budgetDescription;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getBudgetTypeId() {
        return budgetTypeId;
    }

    public void setBudgetTypeId(Integer budgetTypeId) {
        this.budgetTypeId = budgetTypeId;
    }
}

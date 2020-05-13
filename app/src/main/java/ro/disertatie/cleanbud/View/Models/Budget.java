package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "budget",
        foreignKeys = {
            @ForeignKey(
                    entity = Budget.class,
                    parentColumns = "userId",
                    childColumns = "userId",
                    onDelete = CASCADE
            ),
            @ForeignKey(
                    entity = BudgetType.class,
                    parentColumns = "budgetTypeId",
                    childColumns = "budgetTypeId"
            )
        },
        indices = {
                @Index(value = "userId",unique = true),
                @Index(value = "budgetTypeId")
        })
public class Budget {

    @PrimaryKey(autoGenerate = true)
    private Integer budgetId;
    private String title;
    private float amount;
    @TypeConverters({TimestampConverter.class})
    private Date date;
    private Integer userId;
    private String budgetDescription;
    private Integer budgetTypeId;


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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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

    public Integer getBudgetTypeId() {
        return budgetTypeId;
    }

    public void setBudgetTypeId(Integer budgetTypeId) {
        this.budgetTypeId = budgetTypeId;
    }

    public String getBudgetDescription() {
        return budgetDescription;
    }

    public void setBudgetDescription(String budgetDescription) {
        this.budgetDescription = budgetDescription;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", userId=" + userId +
                ", budgetDescription='" + budgetDescription + '\'' +
                ", budgetTypeId=" + budgetTypeId +
                '}';
    }
}
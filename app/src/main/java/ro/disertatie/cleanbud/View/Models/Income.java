package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "income",
        foreignKeys = @ForeignKey(entity = Record.class,
                parentColumns = "incomeCategoryId",
                childColumns = "incomeCategoryId",
                onDelete = CASCADE
        ),

        indices = {@Index(value = {"incomeCategoryId"},unique = true)})
public class Income {
    @PrimaryKey(autoGenerate = true)
    private Integer incomeId;
    private float amountIncome;
    @TypeConverters({TimestampConverter.class})
    private Date dateIncome;
    private Integer incomeCategoryId;
    private Integer recordId;


    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Integer incomeId) {
        this.incomeId = incomeId;
    }

    public float getAmountIncome() {
        return amountIncome;
    }

    public void setAmountIncome(float amountIncome) {
        this.amountIncome = amountIncome;
    }

    public Date getDateIncome() {
        return dateIncome;
    }

    public void setDateIncome(Date dateIncome) {
        this.dateIncome = dateIncome;
    }

    public Integer getIncomeCategoryId() {
        return incomeCategoryId;
    }

    public void setIncomeCategoryId(Integer incomeCategoryId) {
        this.incomeCategoryId = incomeCategoryId;
    }
}

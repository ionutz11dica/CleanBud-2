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

@Entity(tableName = "income",
        foreignKeys = {
                @ForeignKey(
                        entity = Record.class,
                        parentColumns = "recordId",
                        childColumns = "recordId",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = IncomeCategory.class,
                        parentColumns = "incomeCategoryId",
                        childColumns = "incomeCategoryId")
        },
        indices = {
                @Index(value = "incomeCategoryId"),
                @Index(value = "recordId")
        })
public class Income implements RecordProtocol {
    @PrimaryKey(autoGenerate = true)
    private Integer incomeId;
    private String titleIncome;
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

    public String getTitleIncome() {
        return titleIncome;
    }

    public void setTitleIncome(String titleIncome) {
        this.titleIncome = titleIncome;
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

    @Override
    public boolean checkRecordType(int bool) {
        return bool != 0;
    }
}

package ro.disertatie.cleanbud.View.Models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "record",
        foreignKeys =
            @ForeignKey(
                    entity = Budget.class,
                    parentColumns = "budgetId",
                    childColumns = "budgetId",
                    onDelete = CASCADE,
                    onUpdate = CASCADE
            ),
              indices = {@Index(value = "budgetId"),
                      @Index(value = "expenseCategoryId",unique = true),
                      @Index(value = "incomeCategoryId",unique = true)}

)
public class Record {

    @PrimaryKey(autoGenerate = true)
    private Integer recordId;
    private String titleRecord;
    private Integer budgetId;
    private Integer expenseCategoryId;
    private Integer incomeCategoryId;

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

    public String getTitleRecord() {
        return titleRecord;
    }

    public void setTitleRecord(String titleRecord) {
        this.titleRecord = titleRecord;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public Integer getIncomeCategoryId() {
        return incomeCategoryId;
    }

    public void setIncomeCategoryId(Integer incomeCategoryId) {
        this.incomeCategoryId = incomeCategoryId;
    }
}

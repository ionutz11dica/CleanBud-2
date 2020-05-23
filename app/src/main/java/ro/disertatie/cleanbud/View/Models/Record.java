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
              indices = {@Index(value = "budgetId")}

)
public class Record {

    @PrimaryKey(autoGenerate = true)
    private Integer recordId;
    private String titleRecord;
    private Integer budgetId;
    private byte[] receipt;
    private boolean recordType; // 0 - Expense , 1 - Income


    //type currency


    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public boolean isRecordType() {
        return recordType;
    }

    public void setRecordType(boolean recordType) {
        this.recordType = recordType;
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


}

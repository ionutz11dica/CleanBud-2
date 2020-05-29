package ro.disertatie.cleanbud.View.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "economy_budget",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = CASCADE
        ),
        indices = {@Index(value = {"userId"},unique = true)})
public class EconomyBudget implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer ecoId;
    private float amount;
    private float percentage;
    private Integer userId;

    public Integer getEcoId() {
        return ecoId;
    }

    public void setEcoId(Integer ecoId) {
        this.ecoId = ecoId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

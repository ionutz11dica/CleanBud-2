package licenta.books.cleanbud.View.Models;

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Budget {
    @Id
    long id;
    private String title;
    private float amount;
    private Date date;

    ToOne<User> userToOne;
    ToOne<BudgetType> budgetTypeToOne;

    @Backlink
    ToMany<Record> records;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public ToOne<User> getUserToOne() {
        return userToOne;
    }

    public void setUserToOne(ToOne<User> userToOne) {
        this.userToOne = userToOne;
    }

    public ToOne<BudgetType> getBudgetTypeToOne() {
        return budgetTypeToOne;
    }

    public void setBudgetTypeToOne(ToOne<BudgetType> budgetTypeToOne) {
        this.budgetTypeToOne = budgetTypeToOne;
    }

    public ToMany<Record> getRecords() {
        return records;
    }

    public void setRecords(ToMany<Record> records) {
        this.records = records;
    }
}

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

    ToOne<User> user;
    ToOne<BudgetType> budgetType;

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

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> userToOne) {
        this.user = userToOne;
    }

    public ToOne<BudgetType> getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(ToOne<BudgetType> budgetTypeToOne) {
        this.budgetType = budgetTypeToOne;
    }

    public ToMany<Record> getRecords() {
        return records;
    }

    public void setRecords(ToMany<Record> records) {
        this.records = records;
    }
}

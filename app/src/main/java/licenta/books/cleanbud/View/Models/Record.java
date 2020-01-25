package licenta.books.cleanbud.View.Models;


import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Record {

    @Id
    long id;
    private String titleRecord;

    ToOne<Budget> budgetToOne;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleRecord() {
        return titleRecord;
    }

    public void setTitleRecord(String titleRecord) {
        this.titleRecord = titleRecord;
    }

    public ToOne<Budget> getBudgetToOne() {
        return budgetToOne;
    }

    public void setBudgetToOne(ToOne<Budget> budgetToOne) {
        this.budgetToOne = budgetToOne;
    }
}

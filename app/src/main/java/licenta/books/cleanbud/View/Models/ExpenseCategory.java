package licenta.books.cleanbud.View.Models;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class ExpenseCategory {
    @Id
    long id;
    private String titleExpCategory;

    @Backlink
    ToMany<Expense> expenses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitleExpCategory() {
        return titleExpCategory;
    }

    public void setTitleExpCategory(String titleExpCategory) {
        this.titleExpCategory = titleExpCategory;
    }

    public ToMany<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ToMany<Expense> expenses) {
        this.expenses = expenses;
    }
}

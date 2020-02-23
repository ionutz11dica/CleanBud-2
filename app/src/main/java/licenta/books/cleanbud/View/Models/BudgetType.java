package licenta.books.cleanbud.View.Models;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class BudgetType {

    @Id
    long id;

    String title;

    int idImage;

    @Backlink
    ToMany<Budget> budgets;


    public BudgetType(String title, int idImage) {
        this.title = title;
        this.idImage = idImage;
    }

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

    public ToMany<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(ToMany<Budget> budgets) {
        this.budgets = budgets;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }
}

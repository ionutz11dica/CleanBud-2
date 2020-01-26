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

    ToOne<Budget> budget;
    ToOne<Income> income;
    ToOne<Expense> expense;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToOne<Income> getIncome() {
        return income;
    }

    public void setIncome(ToOne<Income> incomeToOne) {
        this.income = incomeToOne;
    }

    public ToOne<Expense> getExpense() {
        return expense;
    }

    public void setExpense(ToOne<Expense> expenseToOne) {
        this.expense = expenseToOne;
    }

    public String getTitleRecord() {
        return titleRecord;
    }

    public void setTitleRecord(String titleRecord) {
        this.titleRecord = titleRecord;
    }

    public ToOne<Budget> getBudgetT() {
        return budget;
    }

    public void setBudget(ToOne<Budget> budgetToOne) {
        this.budget = budgetToOne;
    }
}

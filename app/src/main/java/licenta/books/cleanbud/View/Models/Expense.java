package licenta.books.cleanbud.View.Models;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Expense {
    @Id
    long id;
    private float amountExpense;

    ToOne<ExpenseCategory> expenseCategory;
    ToOne<Record> recordToOne;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmountExpense() {
        return amountExpense;
    }

    public void setAmountExpense(float amountExpense) {
        this.amountExpense = amountExpense;
    }

    public ToOne<ExpenseCategory> getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ToOne<ExpenseCategory> expenseCategoryToOne) {
        this.expenseCategory = expenseCategoryToOne;
    }
}

package licenta.books.cleanbud.View.Models;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Expense {
    @Id
    long id;
    private float amountExpense;

    ToOne<ExpenseCategory> expenseCategoryToOne;


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

    public ToOne<ExpenseCategory> getExpenseCategoryToOne() {
        return expenseCategoryToOne;
    }

    public void setExpenseCategoryToOne(ToOne<ExpenseCategory> expenseCategoryToOne) {
        this.expenseCategoryToOne = expenseCategoryToOne;
    }
}

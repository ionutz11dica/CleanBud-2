package ro.disertatie.cleanbud.View.Database.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.Expense;

@Dao
public interface ExpenseDAO {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM expense INNER JOIN budget on expense.budgetId = budget.budgetId where expense.budgetId = :budgetId")
    Single<List<Expense>> getAllBudgetExpenses(Integer budgetId);



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertExpense(Expense... expenses);
}

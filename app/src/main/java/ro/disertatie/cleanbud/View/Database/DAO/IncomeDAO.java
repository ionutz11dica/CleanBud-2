package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.Income;

@Dao
public interface IncomeDAO {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM income INNER JOIN budget on income.budgetId = budget.budgetId where income.budgetId = :budgetId")
    Single<List<Income>> getAllIncomes(Integer budgetId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIncome(Income... incomes);

}

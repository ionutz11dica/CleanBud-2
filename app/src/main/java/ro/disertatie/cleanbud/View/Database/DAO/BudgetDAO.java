package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.Budget;

@Dao
public interface BudgetDAO {
    @Query("SELECT * FROM budget where userId = :userId")
    Single<List<Budget>> getAllBudgets(Integer userId);

    @Query("Select initialAmount,currentAmount, initialAmount-currentAmount as Difference from budget")
    float getCurrentBudgetAmount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBudget(Budget... budgets);

    @Update
    void updateBudget(Budget... budgets);

    @Query("DELETE from budget WHERE budgetId=:budgetId")
    void deleteBudget(Integer budgetId);
}

package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.Budget;

@Dao
public interface BudgetDAO {
    @Query("SELECT * FROM budget where userId = :userId")
    Single<List<Budget>> getAllBudgets(Integer userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBudget(Budget... budgets);
}

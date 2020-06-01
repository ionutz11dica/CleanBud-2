package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.EconomyBudget;
import ro.disertatie.cleanbud.View.Models.User;


@Dao
public interface EconomyBudgetDAO {


    @Query("SELECT COUNT(*) from economy_budget WHERE userId=:userId")
    Single<Integer> checkIfEconomyBudgetExists(int userId);

    @Query("SELECT amount from economy_budget where userId=:userId")
    Single<Float> getSavingsBudget(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEconomyBudget(EconomyBudget... economyBudgets);

}

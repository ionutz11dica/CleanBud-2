package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.Income;

@Dao
public interface IncomeDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertIncome(Income... incomes);

}

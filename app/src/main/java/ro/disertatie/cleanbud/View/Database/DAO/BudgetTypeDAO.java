package ro.disertatie.cleanbud.View.Database.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.BudgetType;

@Dao
public interface BudgetTypeDAO {


    @Insert
    void insertBudgetTypes(BudgetType... budgets);
}

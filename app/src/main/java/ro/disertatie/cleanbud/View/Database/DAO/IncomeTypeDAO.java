package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import ro.disertatie.cleanbud.View.Models.ExpenseCategory;
import ro.disertatie.cleanbud.View.Models.IncomeCategory;

@Dao
public interface IncomeTypeDAO {

    @Insert
    void insertIncomesTypes(IncomeCategory... incomeCategories);
}

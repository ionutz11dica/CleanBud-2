package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import ro.disertatie.cleanbud.View.Models.ExpenseCategory;

@Dao
public interface ExpensesTypeDAO {

    @Insert
    void insertExpensesTypes(ExpenseCategory... expenseCategories);
}

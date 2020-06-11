package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.BudgetPOJO;
import ro.disertatie.cleanbud.View.Models.ExpensePOJO;
import ro.disertatie.cleanbud.View.Models.IncomeExpensePOJO;

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

    @Query("SELECT currencyId,SUM(currentAmount) as amountSum from budget where userId=:userId group by currencyId order by currencyId")
    Single<List<BudgetPOJO>> getAllBudgetsAmount(Integer userId);


    @Query("SELECT b.*, e.expAmount,i.incAmount FROM budget b " +
            "LEFT JOIN ( SELECT budgetId, SUM(amountExpense) AS expAmount FROM expense GROUP BY budgetId) e ON e.budgetId = b.budgetId " +
            "LEFT JOIN ( SELECT budgetId,SUM(amountIncome) AS incAmount FROM income GROUP BY budgetId) i ON i.budgetId = b.budgetId " +
            "WHERE b.currencyId =:currencyId AND b.userId =:userId ")
    Single<List<IncomeExpensePOJO>> getAllExpenseIncome2(Integer currencyId, Integer userId);

    @Query("SELECT b.*, e.expAmount, e.timestampExp FROM budget b " +
            "LEFT JOIN ( SELECT budgetId, expenseDate AS timestampExp, SUM(amountExpense) AS expAmount FROM expense  GROUP BY timestampExp  ) e ON e.budgetId = b.budgetId " +
            "WHERE  b.userId =:userId and currencyId =:currencyId")
    Single<List<ExpensePOJO>> getExpenseSumByDays(Integer currencyId, Integer userId);
//

    @Query("SELECT b.*, i.expAmount, i.timestampExp FROM budget b " +
            "LEFT JOIN ( SELECT budgetId, dateIncome AS timestampExp, SUM(amountIncome) AS expAmount FROM income  GROUP BY timestampExp  ) i ON i.budgetId = b.budgetId " +
            "WHERE  b.userId =:userId and currencyId =:currencyId")
    Single<List<ExpensePOJO>> getIncomeSumByDays(Integer currencyId, Integer userId);




//    @Query("select sum(e.amountExpense) as expAmount, sum(i.amountIncome) as incAmount " +
//            "from budget b" +
//            " join expense e on b.budgetId=e.budgetId " +
//            " join income i on b.budgetId = i.budgetId  and userId=:userId where currencyId =:currencyId")
//    Single<List<TestPOJO>> getAllExpenseIncomeDate2(Integer currencyId,Integer userId);

}

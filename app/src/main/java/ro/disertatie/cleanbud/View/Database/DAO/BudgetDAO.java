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
import ro.disertatie.cleanbud.View.Models.IncomePOJO;

@Dao
public interface BudgetDAO {
    @Query("SELECT * FROM budget where userId = :userId")
    Single<List<Budget>> getAllBudgets(Integer userId);

    @Query("Select initialAmount,currentAmount, initialAmount-currentAmount as Difference from budget")
    float getCurrentBudgetAmount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBudget(Budget... budgets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBudgetUpdated(List<Budget> budgets);

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
            "LEFT JOIN ( SELECT budgetId, expenseDate AS timestampExp, SUM(amountExpense) AS expAmount FROM expense where expenseDate between datetime('now', '-30 days') AND datetime('now', 'localtime')  " +
            "GROUP BY timestampExp  ) e ON e.budgetId = b.budgetId " +
            "WHERE b.userId =:userId and currencyId =:currencyId")
    Single<List<ExpensePOJO>> getExpenseMonthly(Integer currencyId, Integer userId);

    @Query("SELECT b.*, e.expAmount, e.timestampExp FROM budget b " +
            "LEFT JOIN ( SELECT budgetId, expenseDate AS timestampExp, SUM(amountExpense) AS expAmount FROM expense where expenseDate between datetime('now', '-7 days') AND datetime('now', 'localtime')  " +
            "GROUP BY timestampExp  ) e ON e.budgetId = b.budgetId " +
            "WHERE b.userId =:userId and currencyId =:currencyId")
    Single<List<ExpensePOJO>> getExpenseWeekly(Integer currencyId, Integer userId);
//
//datetime('now', 'start of month') AND datetime('now', 'localtime')
//    ('now','localtime','-30 days') AND date('now', 'localtime')";

    @Query("SELECT b.*, i.incAmount, i.timestampExp FROM budget b " +
            "LEFT JOIN ( SELECT budgetId, dateIncome AS timestampExp, SUM(amountIncome) AS incAmount FROM income where dateIncome between datetime('now', '-30 days') AND datetime('now', 'localtime')  " +
            "GROUP BY timestampExp  ) i ON i.budgetId = b.budgetId " +
            "WHERE b.userId =:userId and currencyId =:currencyId")
    Single<List<IncomePOJO>> getIncomeMonthly(Integer currencyId, Integer userId);


    @Query("SELECT b.*, i.incAmount, i.timestampExp FROM budget b " +
            "LEFT JOIN ( SELECT budgetId, dateIncome AS timestampExp, SUM(amountIncome) AS incAmount FROM income where dateIncome between datetime('now', '-7 days') AND datetime('now', 'localtime')  " +
            "GROUP BY timestampExp  ) i ON i.budgetId = b.budgetId " +
            "WHERE b.userId =:userId and currencyId =:currencyId")
    Single<List<IncomePOJO>> getIncomeWeekly(Integer currencyId, Integer userId);



}

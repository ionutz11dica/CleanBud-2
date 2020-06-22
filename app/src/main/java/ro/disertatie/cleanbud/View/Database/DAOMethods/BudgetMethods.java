package ro.disertatie.cleanbud.View.Database.DAOMethods;


import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.BudgetPOJO;
import ro.disertatie.cleanbud.View.Models.ExpensePOJO;
import ro.disertatie.cleanbud.View.Models.IncomeExpensePOJO;
import ro.disertatie.cleanbud.View.Models.IncomePOJO;
import timber.log.Timber;

public class BudgetMethods implements BudgetDAO {

    private BudgetDAO budgetDAO;
    private static BudgetMethods budgetMethods;

    private BudgetMethods(BudgetDAO budgetDAO) {
        this.budgetDAO = budgetDAO;
    }


    public static BudgetMethods getInstance(BudgetDAO budgetDAO){
        if(budgetMethods == null){
            synchronized (BudgetMethods.class){
                if(budgetMethods==null){
                    budgetMethods = new BudgetMethods(budgetDAO);
                }
            }
        }
        return budgetMethods;
    }




    @Override
    public Single<List<Budget>> getAllBudgets(Integer userId) {
        return budgetDAO.getAllBudgets(userId);
    }

    @Override
    public float getCurrentBudgetAmount() {
        return budgetDAO.getCurrentBudgetAmount();
    }

    @Override
    public void insertBudget(Budget... budgets) {
        Completable.fromAction(() -> budgetDAO.insertBudget(budgets))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Budget","Success");
                        Timber.d("Successful");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Budget",e.getMessage());
                        Timber.d(e);
                    }
                });
    }

    @Override
    public void insertBudgetUpdated(List<Budget> budgets) {
        Completable.fromAction(() -> budgetDAO.insertBudgetUpdated(budgets))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Budget","Success");
                        Timber.d("Successful");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Budget",e.getMessage());
                        Timber.d(e);
                    }
                });
    }

    @Override
    public void updateBudget(Budget... budgets) {
        Completable.fromAction(() -> budgetDAO.updateBudget(budgets))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Budget","Success");
                        Timber.d("Successful");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Budget",e.getMessage());
                        Timber.d(e);
                    }
                });
    }

    @Override
    public void deleteBudget(Integer budgetId) {
        Completable.fromAction(() -> budgetDAO.deleteBudget(budgetId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Budget","Success");
                        Timber.d("Successful");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Budget",e.getMessage());
                        Timber.d(e);
                    }
                });
    }

    @Override
    public Single<List<BudgetPOJO>> getAllBudgetsAmount(Integer userId) {
        return budgetDAO.getAllBudgetsAmount(userId);
    }



    @Override
    public Single<List<IncomeExpensePOJO>> getAllExpenseIncome2(Integer currencyId, Integer userId) {
        return budgetDAO.getAllExpenseIncome2(currencyId,userId);
    }

    @Override
    public Single<List<ExpensePOJO>> getExpenseMonthly(Integer currencyId, Integer userId) {
        return budgetDAO.getExpenseMonthly(currencyId,userId);
    }

    @Override
    public Single<List<ExpensePOJO>> getExpenseWeekly(Integer currencyId, Integer userId) {
        return budgetDAO.getExpenseWeekly(currencyId,userId);
    }

    @Override
    public Single<List<IncomePOJO>> getIncomeMonthly(Integer currencyId, Integer userId) {
        return budgetDAO.getIncomeMonthly(currencyId,userId);
    }

    @Override
    public Single<List<IncomePOJO>> getIncomeWeekly(Integer currencyId, Integer userId) {
        return budgetDAO.getIncomeWeekly(currencyId, userId);
    }

//    @Override
//    public Single<List<TestPOJO>> getAllExpenseIncomeDate2(Integer currencyId, Integer userId) {
//        return budgetDAO.getAllExpenseIncomeDate2(currencyId,userId);
//    }
}

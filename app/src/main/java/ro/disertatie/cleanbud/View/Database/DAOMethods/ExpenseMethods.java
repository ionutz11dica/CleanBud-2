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
import ro.disertatie.cleanbud.View.Database.DAO.ExpenseDAO;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.Expense;
import timber.log.Timber;

public class ExpenseMethods implements ExpenseDAO {

    private ExpenseDAO expenseDAO;
    private static ExpenseMethods expenseMethods;

    private ExpenseMethods(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }


    public static ExpenseMethods getInstance(ExpenseDAO expenseDAO){
        if(expenseMethods == null){
            synchronized (UserMethods.class){
                if(expenseMethods==null){
                    expenseMethods = new ExpenseMethods(expenseDAO);
                }
            }
        }
        return expenseMethods;
    }


    @Override
    public Single<List<Expense>> getAllBudgetExpenses(Integer budgetId) {
        return expenseDAO.getAllBudgetExpenses(budgetId);
    }

    @Override
    public void updateExpense(Expense... expenses) {
        Completable.fromAction(() -> expenseDAO.updateExpense(expenses))
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
    public void insertExpense(Expense... expenses) {
        Completable.fromAction(() -> expenseDAO.insertExpense(expenses))
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
}
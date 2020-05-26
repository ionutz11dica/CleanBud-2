package ro.disertatie.cleanbud.View.Database.DAOMethods;

import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.View.Database.DAO.ExpenseDAO;
import ro.disertatie.cleanbud.View.Database.DAO.IncomeDAO;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.Income;
import timber.log.Timber;

public class IncomeMethods implements IncomeDAO {

    private IncomeDAO incomeDAO;
    private static IncomeMethods incomeMethods;


    private IncomeMethods(IncomeDAO incomeDAO) {
        this.incomeDAO = incomeDAO;
    }


    public static IncomeMethods getInstance(IncomeDAO incomeDAO){
        if(incomeMethods == null){
            synchronized (UserMethods.class){
                if(incomeMethods==null){
                    incomeMethods = new IncomeMethods(incomeDAO);
                }
            }
        }
        return incomeMethods;
    }


    @Override
    public Single<List<Income>> getAllIncomes(Integer budgetId) {
        return incomeDAO.getAllIncomes(budgetId);
    }

    @Override
    public void insertIncome(Income... incomes) {
        Completable.fromAction(() -> incomeDAO.insertIncome(incomes))
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

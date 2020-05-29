package ro.disertatie.cleanbud.View.Database.DAOMethods;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.View.Database.DAO.EconomyBudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAO.ExpenseDAO;
import ro.disertatie.cleanbud.View.Models.EconomyBudget;
import timber.log.Timber;

public class EconomyBudgetMethods implements EconomyBudgetDAO {
    private EconomyBudgetDAO economyBudgetDAO;
    private static EconomyBudgetMethods economyBudgetMethods;

    private EconomyBudgetMethods(EconomyBudgetDAO economyBudgetDAO) {
        this.economyBudgetDAO = economyBudgetDAO;
    }


    public static EconomyBudgetMethods getInstance(EconomyBudgetDAO economyBudgetDAO){
        if(economyBudgetMethods == null){
            synchronized (EconomyBudgetMethods.class){
                if(economyBudgetMethods==null){
                    economyBudgetMethods = new EconomyBudgetMethods(economyBudgetDAO);
                }
            }
        }
        return economyBudgetMethods;
    }

    @Override
    public Single<Integer> checkIfEconomyBudgetExists(int userId) {
        return economyBudgetDAO.checkIfEconomyBudgetExists(userId);
    }

    @Override
    public void insertEconomyBudget(EconomyBudget... economyBudgets) {
        Completable.fromAction(() -> economyBudgetDAO.insertEconomyBudget(economyBudgets))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Successful");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);
                    }
                });
    }
}

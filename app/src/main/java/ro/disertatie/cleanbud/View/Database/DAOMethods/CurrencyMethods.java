package ro.disertatie.cleanbud.View.Database.DAOMethods;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.View.Database.DAO.CurrencyDAO;
import ro.disertatie.cleanbud.View.Models.Currency;
import timber.log.Timber;

public class CurrencyMethods implements CurrencyDAO {
    private CurrencyDAO currencyDAO;
    private static CurrencyMethods currencyMethods;

    private CurrencyMethods(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }


    public static CurrencyMethods getInstance(CurrencyDAO currencyDAO){
        if(currencyMethods == null){
            synchronized (CurrencyMethods.class){
                if(currencyMethods==null){
                    currencyMethods = new CurrencyMethods(currencyDAO);
                }
            }
        }
        return currencyMethods;
    }

    @Override
    public void insertCurrency(List<Currency> currencies) {
        Completable.fromAction(() -> currencyDAO.insertCurrency(currencies))
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

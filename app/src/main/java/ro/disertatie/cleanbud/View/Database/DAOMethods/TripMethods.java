package ro.disertatie.cleanbud.View.Database.DAOMethods;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.View.Database.DAO.TripDAO;
import ro.disertatie.cleanbud.View.Database.DAO.UserDAO;
import ro.disertatie.cleanbud.View.Models.Trip;
import timber.log.Timber;

public class TripMethods implements TripDAO {


    private TripDAO tripDAO;
    private static TripMethods tripMethods;

    private TripMethods(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }


    public static TripMethods getInstance(TripDAO tripDAO){
        if(tripMethods == null){
            synchronized (TripMethods.class){
                if(tripMethods==null){
                    tripMethods = new TripMethods(tripDAO);
                }
            }
        }
        return tripMethods;
    }

    @Override
    public void insertTrip(Trip... trips) {
        Completable.fromAction(() -> tripDAO.insertTrip(trips))
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

package ro.disertatie.cleanbud.View.Database.DAOMethods;

import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.View.Database.DAO.TripDAO;
import ro.disertatie.cleanbud.View.Database.DAO.UserTripDAO;
import ro.disertatie.cleanbud.View.Models.Trip;
import ro.disertatie.cleanbud.View.Models.UserTrip;
import timber.log.Timber;

public class UserTripMethods implements UserTripDAO {

    private UserTripDAO userTripDAO;
    private static UserTripMethods userTripMethods;

    private UserTripMethods(UserTripDAO userTripDAO) {
        this.userTripDAO = userTripDAO;
    }


    public static UserTripMethods getInstance(UserTripDAO userTripDAO){
        if(userTripMethods == null){
            synchronized (UserTripMethods.class){
                if(userTripMethods==null){
                    userTripMethods = new UserTripMethods(userTripDAO);
                }
            }
        }
        return userTripMethods;
    }

    @Override
    public void insertUserTrip(UserTrip... userTrips) {
        Completable.fromAction(() -> userTripDAO.insertUserTrip(userTrips))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("USERTRIP", "merge");

                    }

                    @Override
                    public void onError(Throwable e) {
                       Log.d("USERTRIP",e.getMessage());
                    }
                });
    }

    @Override
    public Single<Integer> checkIfIsFavorite(int userId, int tripId) {
        return userTripDAO.checkIfIsFavorite(userId,tripId);
    }

    @Override
    public void deleteBudget(int userId, int tripId) {
        Completable.fromAction(() -> userTripDAO.deleteBudget(userId,tripId))
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

    @Override
    public Single<List<Trip>> getUserFavoriteHotels(Integer userId) {
        return userTripDAO.getUserFavoriteHotels(userId);
    }


}

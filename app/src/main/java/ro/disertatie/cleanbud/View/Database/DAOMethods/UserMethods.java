package ro.disertatie.cleanbud.View.Database.DAOMethods;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.View.Database.DAO.UserDAO;
import ro.disertatie.cleanbud.View.Models.User;
import ro.disertatie.cleanbud.View.Utils.StaticVar;
import timber.log.Timber;

public class UserMethods implements UserDAO {

    private  UserDAO userDao;
    private static UserMethods userMethods;

    private UserMethods(UserDAO userDao) {
        this.userDao = userDao;
    }


    public static UserMethods getInstance(UserDAO userDao){
        if(userMethods == null){
            synchronized (UserMethods.class){
                if(userMethods==null){
                    userMethods = new UserMethods(userDao);
                }
            }
        }
        return userMethods;
    }





    @Override
    public void insertUser(User... user) {
        Completable.fromAction(() -> userDao.insertUser(user))
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
    public Single<User> verifyAvailableAccount(String usernameReq, String passwordReq) {
        return userDao.verifyAvailableAccount(usernameReq,passwordReq);
    }

    @Override
    public Single<User>  verifyExistenceGoogleAccount(String emailReq) {
        return userDao.verifyExistenceGoogleAccount(emailReq);
    }
}

package ro.disertatie.cleanbud.View.Services;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAO.CurrencyDAO;
import ro.disertatie.cleanbud.View.Database.DAO.EconomyBudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.BudgetMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.CurrencyMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.EconomyBudgetMethods;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.BudgetPOJO;
import ro.disertatie.cleanbud.View.Models.Currency;
import ro.disertatie.cleanbud.View.Models.EconomyBudget;
import ro.disertatie.cleanbud.View.Utils.Constants;

public class Alarm extends BroadcastReceiver {
    BudgetMethods budgetMethods;
    BudgetDAO budgetDAO;
    private EconomyBudgetMethods economyBudgetMethods;
    private EconomyBudgetDAO economyBudgetDAO;
    private CurrencyMethods currencyMethods;
    private CurrencyDAO currencyDAO;
    private SharedPreferences sharedPreferences;
    private boolean isNotifyOn = true;
    private Context context;
    private int userId;
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire(60*1000L*10 /*10 minutes*/);
        this.context = context;

        budgetDAO = AppRoomDatabase.getInstance(context).getBudgetDao();
        budgetMethods = BudgetMethods.getInstance(budgetDAO);
        economyBudgetDAO = AppRoomDatabase.getInstance(context).economyBudgetDAO();
        economyBudgetMethods = EconomyBudgetMethods.getInstance(economyBudgetDAO);
        currencyDAO = AppRoomDatabase.getInstance(context).currencyDAO();
        currencyMethods = CurrencyMethods.getInstance(currencyDAO);

        sharedPreferences = context.getSharedPreferences(Constants.NOTIFICATION_PREF,Context.MODE_PRIVATE);
        isNotifyOn = sharedPreferences.getBoolean(Constants.NOTIFICATION_KEY,true);
        userId = sharedPreferences.getInt(Constants.USER_ID_KEY, -1);

        if(userId != - 1) {
            Single<List<Budget>> single = budgetMethods.getAllBudgets(userId);
            single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<Budget>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<Budget> budgets) {
                            Toast.makeText(context,"Id user "+userId,Toast.LENGTH_LONG).show();
                            getCurrencies(budgets);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }


        wl.release();
    }


    public void getEconomyBudget(List<Budget> list,float dollar, float leu){
        Single<EconomyBudget> percentage = economyBudgetMethods.getSavingsBudgetPercentage(userId);
        percentage.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<EconomyBudget>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(EconomyBudget economyBudget) {
                        float sum = 0;

                        for (Budget budget : list) {
                            if(budget.getCurrentAmount() >= budget.getInitialAmount()/2){
                                if(budget.getCurrencyId() == 0){
                                    sum += budget.getCurrentAmount()/dollar;

                                }else if(budget.getCurrencyId() == 1){
                                    sum += budget.getCurrentAmount()/leu;
                                }else{
                                    sum += budget.getCurrentAmount();
                                }
                                budget.setCurrentAmount(budget.getCurrentAmount() - budget.getCurrentAmount()*(economyBudget.getPercentage()/100));
                                budgetMethods.updateBudget(budget);
                            }
                        }
                        sum = (sum*dollar) * (economyBudget.getPercentage()/100);
                        economyBudgetMethods.updateEconomyBudget(economyBudget.getAmount()+sum,userId);
                        if(isNotifyOn && sum != 0){
                            int notifyID = 1;
                            String CHANNEL_ID = "my_channel_01";// The id of the channel.
                            CharSequence name = "Test";// The user-visible name of the channel.
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                            // Create a notification and set the notification channel.
                            Notification notification = new Notification.Builder(context)
                                    .setContentTitle("Savings Budget")
                                    .setContentText("Your savings budget is updated with "+String.format("%.2f",sum)+"$")
                                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                    .setChannelId(CHANNEL_ID)
                                    .build();

                            NotificationManager mNotificationManager =
                                    (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.createNotificationChannel(mChannel);

                            // Issue the notification.
                            mNotificationManager.notify(notifyID , notification);

                            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(2500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(2500);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    private void getCurrencies(List<Budget> budgets){
        Single<List<Currency>> single = currencyMethods.getCurrencies();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Currency>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Currency> currencies) {
                        getEconomyBudget(budgets,currencies.get(0).getValue(),currencies.get(1).getValue());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void setAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


}

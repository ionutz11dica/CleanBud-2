package ro.disertatie.cleanbud.View.Database;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import io.reactivex.annotations.NonNull;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetTypeDAO;
import ro.disertatie.cleanbud.View.Database.DAO.UserDAO;
import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.BudgetType;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.ExpenseCategory;
import ro.disertatie.cleanbud.View.Models.Income;
import ro.disertatie.cleanbud.View.Models.IncomeCategory;
import ro.disertatie.cleanbud.View.Models.Record;
import ro.disertatie.cleanbud.View.Models.Trip;
import ro.disertatie.cleanbud.View.Models.User;
import ro.disertatie.cleanbud.View.Models.UserTrip;

import static ro.disertatie.cleanbud.View.Utils.Constants.DATABASE_NAME;


@Database(entities = {User.class, Budget.class, BudgetType.class, Expense.class, ExpenseCategory.class, Income.class, IncomeCategory.class,
        Record.class, Trip.class, UserTrip.class},version = 6,exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {
    //database object
    public abstract UserDAO getUserDao();
    public abstract BudgetDAO getBudgetDao();
    public abstract BudgetTypeDAO budgetTypeDao();

    private static volatile AppRoomDatabase appRoomDatabase=null;

    //Singleton
    public static AppRoomDatabase getInstance(final Context context){
        if(appRoomDatabase == null){
            synchronized (AppRoomDatabase.class){
                if(appRoomDatabase == null){
                    appRoomDatabase = buildDatabase(context);
                }
            }
        }
        return appRoomDatabase;
    }

    private static AppRoomDatabase buildDatabase(final Context context){
        return Room.databaseBuilder(context,
                AppRoomDatabase.class,
                DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).budgetTypeDao().insertBudgetTypes(BudgetType.populateBudgetTypes());
                            }
                        });
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }

    public static void destroyInstance() {
        appRoomDatabase = null;
    }
}

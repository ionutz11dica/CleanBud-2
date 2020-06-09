package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.Currency;

@Dao
public interface CurrencyDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrency(List<Currency> currencies);

    @Query("SELECT * FROM currency")
    Single<List<Currency>> getCurrencies();
}

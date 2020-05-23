package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import ro.disertatie.cleanbud.View.Models.Budget;
import ro.disertatie.cleanbud.View.Models.Trip;

@Dao
public interface TripDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrip(Trip... trips);
}

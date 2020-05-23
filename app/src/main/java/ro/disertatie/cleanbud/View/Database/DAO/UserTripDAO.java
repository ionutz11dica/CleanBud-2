package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import ro.disertatie.cleanbud.View.Models.UserTrip;

@Dao
public interface UserTripDAO {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertUserTrip(UserTrip... userTrips);
}

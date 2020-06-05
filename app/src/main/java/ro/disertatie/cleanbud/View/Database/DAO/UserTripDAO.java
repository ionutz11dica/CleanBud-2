package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.DELETE;
import ro.disertatie.cleanbud.View.Models.Trip;
import ro.disertatie.cleanbud.View.Models.UserTrip;

@Dao
public interface UserTripDAO {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertUserTrip(UserTrip... userTrips);

    @Query("SELECT COUNT(*) from userTrip WHERE userId=:userId AND tripId=:tripId")
    Single<Integer> checkIfIsFavorite(int userId,int tripId);

    @Query("DELETE from userTrip WHERE userId=:userId and tripId=:tripId")
    void deleteBudget(int userId,int tripId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM trip INNER JOIN userTrip ON trip.tripId = userTrip.tripId WHERE userTrip.userId = :userId")
    Single<List<Trip>> getUserFavoriteHotels(Integer userId);

}

package ro.disertatie.cleanbud.View.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Single;
import ro.disertatie.cleanbud.View.Models.User;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User... user);

    @Query("SELECT * from user where email = :emailReq and password = :passwordReq")
    Single<User> verifyAvailableAccount(String emailReq, String passwordReq);

    @Query("SELECT * from user where email = :emailReq")
    Single<User> verifyExistenceGoogleAccount(String emailReq);

}

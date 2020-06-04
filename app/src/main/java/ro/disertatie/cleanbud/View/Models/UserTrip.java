package ro.disertatie.cleanbud.View.Models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "userTrip",
        primaryKeys = {"userId","tripId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Trip.class,
                        parentColumns = "tripId",
                        childColumns = "tripId",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId")
        },
        indices = {
                @Index(value = "userId"),
                @Index(value = "tripId")
        })
public class UserTrip {

    @NonNull public final Integer tripId;
    @NonNull public final Integer userId;

    public UserTrip(@NonNull Integer tripId, @NonNull Integer userId) {
        this.tripId = tripId;
        this.userId = userId;
    }
}

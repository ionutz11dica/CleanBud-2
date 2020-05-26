package ro.disertatie.cleanbud.View.Models.Converters;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriConverters {
    @TypeConverter
    public static Uri fromString(String value) {
         if (value == null){
             return null;
         }else{
             return Uri.parse(value);
         }
    }

    public static String toString(Uri uri){
        return uri.toString();
    }
}

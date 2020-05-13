package ro.disertatie.cleanbud.View.Models.Converters;

import android.annotation.SuppressLint;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ro.disertatie.cleanbud.View.Utils.Constants;

public class TimestampConverter {

        @SuppressLint("SimpleDateFormat")
        static DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);

        @TypeConverter
        public static Date fromTimestamp(String value) {
            if (value != null) {
                try {
                    return df.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                return null;
            }
        }

        @TypeConverter
        public static String fromDateToString(Date date){

            return df.format(date);
        }
    }


package ro.disertatie.cleanbud.View.Uitility;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static boolean isEmptyOrNull(String value) {
        return (value == null || value.isEmpty());
    }

    public static void showMessage(Context activity, String message) {
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        toast.show();
    }

    public static String dateConverter() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat converter = new SimpleDateFormat("EEE, d MMM yyyy");

        return converter.format(date);
    }

    public static String timeConverter(Long time) {
        SimpleDateFormat converter = new SimpleDateFormat("hh:mm a");

        return converter.format(new Date(time*1000));
    }

//    @BindingAdapter("layoutBackgroundImage")
//    public static void setLayoutBackgroundImage(ConstraintLayout constraintLayout, String url){
//        String[] day = new String[]{"01d","02d","03d","04d","09d","10d","11d","13d","50d"};
//        String[] night = new String[]{ "01n","02n","03n","04n","09n","10n","11n","13n","50n"};
//
//        for(int i = 0; i <day.length;i++){
//            if(url.equals(day[i])){
//                constraintLayout.setBackgroundResource(constraintLayout.getResources().getIdentifier("ic_background_daylight","drawable", constraintLayout.getContext().getPackageName()));
//
//            }else if(url.equals(night[i])){
//                constraintLayout.setBackgroundResource(constraintLayout.getResources().getIdentifier("ic_background_night","drawable", constraintLayout.getContext().getPackageName()));
//
//            }
//        }
//
//    }
}

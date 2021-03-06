package ro.disertatie.cleanbud.View.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverters;

import com.github.mikephil.charting.data.Entry;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;

public class ExpensePOJO implements Serializable {
    private Integer budgetId;
    private float expAmount;


    @TypeConverters({TimestampConverter.class})
    private Date timestampExp;


    public Date getTimestampExp() {
        return timestampExp;
    }

    public void setTimestampExp(Date timestampExp) {
        this.timestampExp = timestampExp;
    }

    public ExpensePOJO() {
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public float getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(float expAmount) {
        this.expAmount = expAmount;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Entry> algorithmMonthlyWeekly(List<ExpensePOJO> list,int noDays){
        ArrayList<Entry> newList = new ArrayList<>();

        LocalDate dateBefore30Days = LocalDate.now().minusDays(noDays);
        LocalDate now = LocalDate.now();
        int i = 0;
        for(LocalDate date = dateBefore30Days; date.isBefore(now); date = date.plusDays(1)){
            boolean flag = false;
            for (int j = 0; j < list.size();j++ ){
                if(isEqual(date,list.get(j).getTimestampExp().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
                    newList.add(new Entry(i,list.get(j).getExpAmount()));
                    flag = true;
                }
            }
            if(!flag){
                newList.add(new Entry(i,0));
            }
            i++;
        }
        return newList;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isWithinRange(LocalDate testDate, LocalDate startDate, LocalDate endDate) {
        return !(testDate.isBefore(startDate) || testDate.isAfter(endDate));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isEqual(LocalDate date, LocalDate date2) {
        return date.isEqual(date2);
    }

}

package ro.disertatie.cleanbud.View.ViewModel;

import android.graphics.Color;

import com.broooapps.lineargraphview2.DataModel;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;
import java.util.List;

import ro.disertatie.cleanbud.View.Fragments.AnalyticsFragment;
import ro.disertatie.cleanbud.View.Utils.IndexAxisValueFormatter;
import ro.disertatie.cleanbud.databinding.AnalyticsFragmentBinding;

public class AnalyticsViewModel {
    private AnalyticsFragment analyticsFragment;
    private AnalyticsFragmentBinding analyticsFragmentBinding;

    public AnalyticsViewModel(AnalyticsFragment analyticsFragment, AnalyticsFragmentBinding analyticsFragmentBinding) {
        this.analyticsFragment = analyticsFragment;
        this.analyticsFragmentBinding = analyticsFragmentBinding;
        initTabLayout();
        initTabLayoutOverall();
        initCubicLineChart();
    }

    private void initTabLayout(){
        analyticsFragmentBinding.tabLayout.addTab(analyticsFragmentBinding.tabLayout.newTab().setText("Dollar"));
        analyticsFragmentBinding.tabLayout.addTab(analyticsFragmentBinding.tabLayout.newTab().setText("Euro"));
        analyticsFragmentBinding.tabLayout.addTab(analyticsFragmentBinding.tabLayout.newTab().setText("Leu"));

    }

    private void initTabLayoutOverall(){
        analyticsFragmentBinding.tabLayoutOverall.addTab(analyticsFragmentBinding.tabLayoutOverall.newTab().setText("Weekly"));
        analyticsFragmentBinding.tabLayoutOverall.addTab(analyticsFragmentBinding.tabLayoutOverall.newTab().setText("Monthly"));

    }


    public void initLinearGraph(){
        List<DataModel> dataList = new ArrayList<>();

        dataList.add(new DataModel("Income", "#d291bc", 100));
        dataList.add(new DataModel("Two2", "#ffffff", 5));
        dataList.add(new DataModel("Expense", "#1b6ca8", 250));
        analyticsFragmentBinding.incomeVsExpense.setData(dataList,355);
    }

    public void initCubicLineChart(){
        analyticsFragmentBinding.overallAnalytics.setBackgroundColor(Color.WHITE);
        analyticsFragmentBinding.overallAnalytics.setGridBackgroundColor(Color.TRANSPARENT);
        analyticsFragmentBinding.overallAnalytics.setDrawGridBackground(true);

        analyticsFragmentBinding.overallAnalytics.setDrawBorders(true);
        analyticsFragmentBinding.overallAnalytics.getDescription().setEnabled(false);
        analyticsFragmentBinding.overallAnalytics.setPinchZoom(false);

        Legend l = analyticsFragmentBinding.overallAnalytics.getLegend();
        l.setEnabled(false);

        YAxis leftAxis = analyticsFragmentBinding.overallAnalytics.getAxisLeft();
        leftAxis.setAxisMaximum(200);
        leftAxis.setAxisMinimum(50);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawGridLines(false);

        analyticsFragmentBinding.overallAnalytics.getAxisRight().setEnabled(false);
        analyticsFragmentBinding.overallAnalytics.getXAxis().setEnabled(false);

        setData(20,10);

    }

    private void setData(int count, float range){
        ArrayList<Entry> yVals = new ArrayList<>();

        for(int i = 0 ;i<count;i++){
            if(i==5){
                float val = (float) (0);
                yVals.add(new Entry(i,val));
            }else{
                float val = (float) (Math.random()*range + 100);
                yVals.add(new Entry(i,val));
            }

        }


        ArrayList<Entry> yVals1 = new ArrayList<>();

        for(int i = 0 ;i<count;i++){
            float val = (float) (Math.random()*range + 180);
            yVals1.add(new Entry(i,val));
        }

        LineDataSet set1,set2;

        set1 = new LineDataSet(yVals,"Data Set1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setDrawCircles(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setLineWidth(3f);
        set1.setFillAlpha(255);
        set1.setDrawFilled(true);
        set1.setDrawValues(false);
        set1.setFillColor(Color.parseColor("#1b6ca8"));
        set1.setColor(Color.parseColor("#1b6ca8"));



        set2 = new LineDataSet(yVals1,"Data Set2");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        set2.setDrawCircles(false);
        set2.setLineWidth(3f);
        set2.setFillAlpha(255);
        set2.setDrawFilled(true);
        set2.setDrawValues(false);

        set2.setFillColor(Color.parseColor("#d291bc"));
        set2.setColor(Color.parseColor("#d291bc"));


        LineData data = new LineData(set2,set1);

        data.setDrawValues(false);

        analyticsFragmentBinding.overallAnalytics.setData(data);
        analyticsFragmentBinding.overallAnalytics.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        analyticsFragmentBinding.overallAnalytics.invalidate();
    }
}

package ro.disertatie.cleanbud.View.ViewModel;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.broooapps.lineargraphview2.DataModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.BudgetMethods;
import ro.disertatie.cleanbud.View.Fragments.AnalyticsFragment;
import ro.disertatie.cleanbud.View.Models.BudgetPOJO;
import ro.disertatie.cleanbud.View.Models.ExpensePOJO;
import ro.disertatie.cleanbud.View.Models.IncomeExpensePOJO;
import ro.disertatie.cleanbud.View.Utils.StaticVar;
import ro.disertatie.cleanbud.databinding.AnalyticsFragmentBinding;

public class AnalyticsViewModel {
    private AnalyticsFragment analyticsFragment;
    private AnalyticsFragmentBinding analyticsFragmentBinding;
    private AnalyticsFragment.AnalyticsListener listener;
    private BudgetMethods budgetMethods;

    public AnalyticsViewModel(AnalyticsFragment analyticsFragment, AnalyticsFragmentBinding analyticsFragmentBinding) {
        this.analyticsFragment = analyticsFragment;
        this.analyticsFragmentBinding = analyticsFragmentBinding;
        listener = (AnalyticsFragment.AnalyticsListener) analyticsFragment.getContext();
        initTabLayout();
        initTabLayoutOverall();

        initCubicLineChart();
        backListener();
        openDB();
    }

    private void initTabLayout(){
        analyticsFragmentBinding.tabLayout.addTab(analyticsFragmentBinding.tabLayout.newTab().setText("Dollar"));
        analyticsFragmentBinding.tabLayout.addTab(analyticsFragmentBinding.tabLayout.newTab().setText("Euro"));
        analyticsFragmentBinding.tabLayout.addTab(analyticsFragmentBinding.tabLayout.newTab().setText("Leu"));

        analyticsFragmentBinding.analyticsToolbar.setTitleTextAppearance(analyticsFragment.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        analyticsFragmentBinding.analyticsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

    }

    public void tabClick(){
        analyticsFragmentBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: {
                        getDataForLinearGraph(0);
                        break;
                    }
                    case 1: {
                        getDataForLinearGraph(2);
                        break;
                    }
                    case 2: {
                        getDataForLinearGraph(1);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabLayoutOverall(){
        analyticsFragmentBinding.tabLayoutOverall.addTab(analyticsFragmentBinding.tabLayoutOverall.newTab().setText("Weekly"));
        analyticsFragmentBinding.tabLayoutOverall.addTab(analyticsFragmentBinding.tabLayoutOverall.newTab().setText("Monthly"));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        analyticsFragmentBinding.analyticsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backPressedAnalytics("homeF");
            }
        });
    }

    public void initLinearGraph(List<IncomeExpensePOJO> list){
        float sumExp = 0;
        float sumInc = 0;

        for (IncomeExpensePOJO budgetPOJO : list){
            sumExp += budgetPOJO.getExpAmount();
            sumInc += budgetPOJO.getIncAmount();
        }

        List<DataModel> dataList = new ArrayList<>();

        dataList.add(new DataModel("Income", "#d291bc", (int)sumInc));
        dataList.add(new DataModel("Two2", "#ffffff", 5));
        dataList.add(new DataModel("Expense", "#1b6ca8", (int)sumExp));
        analyticsFragmentBinding.tvAnalyticsIncome.setText(String.valueOf(sumInc));
        analyticsFragmentBinding.tvAnalyticsExpense.setText(String.valueOf(sumExp));
//        analyticsFragmentBinding.incomeVsExpense.
        analyticsFragmentBinding.incomeVsExpense.setData(dataList,sumExp+sumInc+5);


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

    private void initPieChart(List<BudgetPOJO> list){
        float sum = 0;
        for (BudgetPOJO budgetPOJO : list) {
            sum += budgetPOJO.getAmountSum();
        }



        analyticsFragmentBinding.pieChart.setUsePercentValues(true);
        analyticsFragmentBinding.pieChart.getDescription().setEnabled(false);
        analyticsFragmentBinding.pieChart.setExtraOffsets(5,10,5,5);

        analyticsFragmentBinding.pieChart.setDragDecelerationFrictionCoef(0.99f);

        analyticsFragmentBinding.pieChart.setDrawHoleEnabled(true);
        analyticsFragmentBinding.pieChart.setHoleColor(Color.WHITE);
        analyticsFragmentBinding.pieChart.setTransparentCircleRadius(60f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry((list.get(2).getAmountSum()/sum)*100,"Euro"));
        yValues.add(new PieEntry((list.get(1).getAmountSum()/sum)*100,"Leu"));
        yValues.add(new PieEntry((list.get(0).getAmountSum()/sum)*100,"Dollar"));
        analyticsFragmentBinding.pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues,"Currency budgets");
        dataSet.setSliceSpace(1.5f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(9f);
        data.setValueTextColor(Color.WHITE);

        analyticsFragmentBinding.pieChart.setData(data);

    }

    public void getDataForPieChart(){
        Single<List<BudgetPOJO>> single = budgetMethods.getAllBudgetsAmount(StaticVar.USER_ID);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BudgetPOJO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BudgetPOJO> budgetPOJOS) {
                        initPieChart(budgetPOJOS);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void getDataForLinearGraph(int currencyId){
        Single<List<IncomeExpensePOJO>> single = budgetMethods.getAllExpenseIncome2(currencyId,StaticVar.USER_ID);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<IncomeExpensePOJO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<IncomeExpensePOJO> testPOJOS) {
                        initLinearGraph(testPOJOS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test",e.getMessage());
                    }
                });
    }


    public void getDataForCubicLineGraph(){
        Single<List<ExpensePOJO>> single = budgetMethods.getExpenseSumByDays(0, StaticVar.USER_ID);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ExpensePOJO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ExpensePOJO> list) {
                        if(list.isEmpty()){

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                            Log.d("Test",e.getMessage());
                    }
                });
    }

    private void openDB(){
        BudgetDAO budgetDAO = AppRoomDatabase.getInstance(analyticsFragment.getContext().getApplicationContext()).getBudgetDao();
        budgetMethods = BudgetMethods.getInstance(budgetDAO);

    }
}

package ro.disertatie.cleanbud.View.ViewModel;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.QueryMap;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.API.APIClient;
import ro.disertatie.cleanbud.View.API.APIService;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.EconomyBudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.EconomyBudgetMethods;
import ro.disertatie.cleanbud.View.Fragments.HotelsFragment;
import ro.disertatie.cleanbud.View.Fragments.TripFilterFragment;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.DataHotel;
import ro.disertatie.cleanbud.View.Models.ApiModels.Location.DataLocation;
import ro.disertatie.cleanbud.View.Uitility.Utility;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.View.ProgressDialogClass;
import ro.disertatie.cleanbud.databinding.TripFilterFragmentBinding;

public class TripsFilterViewModel {
    private TripFilterFragment tripFilterFragment;
    private TripFilterFragmentBinding tripFilterFragmentBinding;
    private EconomyBudgetMethods economyBudgetMethods;
    private EconomyBudgetDAO economyBudgetDAO;
    private TripFilterFragment.TripFilterListener listener;
    private final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private String myFormat = "dd,MMM,yyyy"; //In which you need put here
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private boolean isArrive = false;
    private float savingAmount = 0;
    private int noGuest = 1;
    private int noRooms = 1;
    private int price = 0;
    private String checkin;
    private ProgressDialogClass dialogClass;
    private APIService apiService;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TripsFilterViewModel(TripFilterFragment tripFilterFragment, TripFilterFragmentBinding tripFilterFragmentBinding) {
        this.tripFilterFragment = tripFilterFragment;
        this.tripFilterFragmentBinding = tripFilterFragmentBinding;

        listener = (TripFilterFragment.TripFilterListener) tripFilterFragment.getContext();
        dialogClass = new ProgressDialogClass(tripFilterFragment.getActivity());
        initToolbar();
        setPickerDate();
    }

    private void initProgressPrice(int min, int max){

        final int total = max - min;
        tripFilterFragmentBinding.fluidSlider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                return  Unit.INSTANCE;
            }
        });


        tripFilterFragmentBinding.fluidSlider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                return  Unit.INSTANCE;
            }
        });

        tripFilterFragmentBinding.fluidSlider.setPositionListener(pos -> {
            final String value = String.valueOf( (int)(min + total * pos) );
            tripFilterFragmentBinding.fluidSlider.setBubbleText(value);
            price = Integer.parseInt(value);
            return Unit.INSTANCE;
        });

        tripFilterFragmentBinding.fluidSlider.setPosition(0.3f);
        tripFilterFragmentBinding.fluidSlider.setStartText(String.valueOf(min));
        tripFilterFragmentBinding.fluidSlider.setEndText(String.valueOf(max));
    }

    public void openDb(){
        economyBudgetDAO = AppRoomDatabase.getInstance(tripFilterFragment.getContext()).economyBudgetDAO();
        economyBudgetMethods = EconomyBudgetMethods.getInstance(economyBudgetDAO);
    }

    public void getSavings(int userId){

        Single<Float> single = economyBudgetMethods.getSavingsBudget(userId);
        single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<Float>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(Float aFloat) {
                    retrieveSavings(aFloat);
                    dialogClass.dismissDialog();
                }

                @Override
                public void onError(Throwable e) {

                }
            });
    }

    private void retrieveSavings(Float aFloat) {
        savingAmount = aFloat;
        initProgressPrice(Math.round(aFloat*0.2f),Math.round(aFloat));
        tripFilterFragmentBinding.tvSavingTotal.setText(Math.round(aFloat)+"$");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        tripFilterFragmentBinding.tripToolbar.setTitleTextAppearance(tripFilterFragment.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        tripFilterFragmentBinding.tripToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        backListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        tripFilterFragmentBinding.tripToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressedTripFilter("homeF");
            }
        });
    }

    public TripFilterFragment.TripFilterListener getListener() {
        return listener;
    }

    public void resetFilters(){
        noGuest = 1;
        noRooms = 1;
        tripFilterFragmentBinding.tvArrive.setText("Arrive");
        tripFilterFragmentBinding.tvDepart.setText("Depart");
        tripFilterFragmentBinding.etCity.setText("");
        tripFilterFragmentBinding.tvNoGuest.setText("1 guests");
        tripFilterFragmentBinding.tvNoRooms.setText("1 rooms");

    }

    private void updateDateLabel(){
        sdf = new SimpleDateFormat("dd,MMM,yyyy");
        if(isArrive){
            tripFilterFragmentBinding.tvArrive.setText(sdf.format(myCalendar.getTime()));
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            checkin = sdf.format(myCalendar.getTime());

        }else{
            tripFilterFragmentBinding.tvDepart.setText(sdf.format(myCalendar.getTime()));

        }
    }

    public void openDatePickerDialog(){
        tripFilterFragmentBinding.llArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(tripFilterFragment.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                isArrive = true;

            }
        });
    }

    public void openDatePickerDialogDepart(){
        tripFilterFragmentBinding.tvDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(tripFilterFragment.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                isArrive = false;
            }
        });
    }

    private void setPickerDate(){
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };
    }



    public void applyFilterClick(){

        tripFilterFragmentBinding.btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(v)) {
                    dialogClass.showDialog("Searching..", "Please wait");
                    apiService = APIClient.getRetrofit3().create(APIService.class);
                    String city = tripFilterFragmentBinding.etCity.getText().toString();
                    Call<DataLocation> call = apiService.getLocations("30", "relevance", city.substring(0,1).toUpperCase() + city.substring(1));
                    call.enqueue(new Callback<DataLocation>() {
                        @Override
                        public void onResponse(Call<DataLocation> call, Response<DataLocation> response) {
                            if (response.code() == 200) {
                                Call<DataHotel> call2 = apiService.getHotels(createQueryMap(response.body().getResultObject()[0].getResultObject().getLocation_id()));
                                call2.enqueue(new Callback<DataHotel>() {
                                    @Override
                                    public void onResponse(Call<DataHotel> call, Response<DataHotel> response) {
                                        if (response.code() == 200) {
                                            listener.passDataToHotels(response.body().getList(),"hotelsF");

                                            dialogClass.dismissDialog();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<DataHotel> call, Throwable t) {
                                        dialogClass.dismissDialog();
                                    }
                                });

                            }
                        }

                        @Override
                        public void onFailure(Call<DataLocation> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private boolean isValid(View view){
        if(Utility.isEmptyOrNull(tripFilterFragmentBinding.etCity.getText().toString())){
            tripFilterFragmentBinding.etCity.setError("Required Parameter");
            return false;
        }
        else if(tripFilterFragmentBinding.tvArrive.getText().toString().equals("Arrive")){
            Snackbar.make(view, "Invalid inputs", Snackbar.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }

    private Map<String,String> createQueryMap(String id){
        Map<String,String> map = new HashMap<>();
        map.put("location_id",id);
        map.put("adults", String.valueOf(noGuest));
        map.put("checkin",checkin);
        map.put("rooms",String.valueOf(noRooms));
        map.put("pricesmax","$"+price+"-> "+Math.round(price));

        return map;
    }

    public void addNoGuest(){
        tripFilterFragmentBinding.llPlusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noGuest < 6) {


                    noGuest++;
                    tripFilterFragmentBinding.tvNoGuest.setText(noGuest + " guests");
                }
            }
        });
    }

    public void minusNoGuest(){
        tripFilterFragmentBinding.llMinusGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noGuest > 1) {


                    noGuest--;
                    tripFilterFragmentBinding.tvNoGuest.setText(noGuest + " guests");
                }
            }
        });
    }


    public void addNoRooms(){
        tripFilterFragmentBinding.llPlusRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noRooms < 6) {


                    noRooms++;
                    tripFilterFragmentBinding.tvNoRooms.setText(noRooms + " rooms");
                }
            }
        });
    }

    public void minusNoRooms(){
        tripFilterFragmentBinding.llMinusRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noRooms > 1) {


                    noRooms--;
                    tripFilterFragmentBinding.tvNoRooms.setText(noRooms + " rooms");
                }
            }
        });
    }

}

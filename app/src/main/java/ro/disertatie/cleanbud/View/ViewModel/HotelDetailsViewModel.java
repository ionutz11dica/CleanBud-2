package ro.disertatie.cleanbud.View.ViewModel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.API.APIClient;
import ro.disertatie.cleanbud.View.API.APIService;
import ro.disertatie.cleanbud.View.Activities.StartActivity;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.BudgetDAO;
import ro.disertatie.cleanbud.View.Database.DAO.ExpenseDAO;
import ro.disertatie.cleanbud.View.Database.DAO.IncomeDAO;
import ro.disertatie.cleanbud.View.Database.DAO.TripDAO;
import ro.disertatie.cleanbud.View.Database.DAO.UserTripDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.BudgetMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.ExpenseMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.IncomeMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.TripMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.UserTripMethods;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.GoogleMapDialog;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.WeatherDialog;
import ro.disertatie.cleanbud.View.Fragments.HotelDetailsFragment;
import ro.disertatie.cleanbud.View.Fragments.HotelsFragment;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.View.Models.Trip;
import ro.disertatie.cleanbud.View.Models.UserTrip;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.Utils.StaticVar;
import ro.disertatie.cleanbud.databinding.HotelDetailsFragmentBinding;

public class HotelDetailsViewModel {
    private HotelDetailsFragment hotelDetailsFragment;
    private HotelDetailsFragmentBinding hotelDetailsFragmentBinding;
    private APIService apiService;
    private Trip trip;
    private String phoneNo = "";
    private String title = "";
    private HotelDetailsFragment.HotelsDetailsListener listener;
    private boolean isFavorite = false;
    private boolean isFromFavorite = false;
    private UserTripMethods userTripMethods;
    private TripMethods tripMethods;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HotelDetailsViewModel(HotelDetailsFragment hotelDetailsFragment, HotelDetailsFragmentBinding hotelDetailsFragmentBinding) {
        this.hotelDetailsFragment = hotelDetailsFragment;
        this.hotelDetailsFragmentBinding = hotelDetailsFragmentBinding;
        listener = (HotelDetailsFragment.HotelsDetailsListener) hotelDetailsFragment.getContext();
        apiService = APIClient.getRetrofit4().create(APIService.class);
        initToolbar();
        openDB();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        hotelDetailsFragmentBinding.hotelDetailsToolbar.setTitleTextAppearance(hotelDetailsFragment.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        hotelDetailsFragmentBinding.hotelDetailsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        backListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        hotelDetailsFragmentBinding.hotelDetailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFromFavorite){
                    listener.onBackButtonPressedHotelsDetailsListener("tripFilterF");
                }else{
                    listener.onBackButtonPressedHotelsDetailsListener("hotelsF");

                }
            }
        });
    }

    private void setTripObject(ResultObjectHotel resultObjectHotel) {
        trip = new Trip();
        trip.setDescription(resultObjectHotel.getDescription());
        trip.setTripId(Integer.parseInt(resultObjectHotel.getLocation_id()));
        trip.setLat(resultObjectHotel.getLat());
        trip.setLon(resultObjectHotel.getLon());
        trip.setName(resultObjectHotel.getName());
        trip.setPhone(resultObjectHotel.getPhone());
        trip.setPhoto(resultObjectHotel.getPhoto().getImages().getLarge().getUrl());
        trip.setPrice(resultObjectHotel.getPrice());
        trip.setLocationString(resultObjectHotel.getLocationString());

    }

    public void setupViews(ResultObjectHotel resultObjectHotel) {
        isFromFavorite = false;
        setTripObject(resultObjectHotel);
        checkIfIsFavorite();
        Glide
                .with(hotelDetailsFragment.getContext())
                .load(resultObjectHotel.getPhoto().getImages().getLarge().getUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_businessman)
                .into(hotelDetailsFragmentBinding.imvHotelDetails);
        hotelDetailsFragmentBinding.tvHotelNameDetails.setText(resultObjectHotel.getName());
        hotelDetailsFragmentBinding.tvDescriptionHotel.setText(resultObjectHotel.getDescription());
        hotelDetailsFragmentBinding.tvNameHotelDesc.setText(resultObjectHotel.getName());
        hotelDetailsFragmentBinding.tvHotelNameDetails.setText(resultObjectHotel.getName());
        phoneNo = resultObjectHotel.getPhone();
        title = resultObjectHotel.getName();
    }

    public void setupViewsFav(Trip resultObjectHotel) {
        isFromFavorite = true;
        trip = resultObjectHotel;
        checkIfIsFavorite();
        Glide
                .with(hotelDetailsFragment.getContext())
                .load(resultObjectHotel.getPhoto())
                .centerCrop()
                .placeholder(R.drawable.ic_businessman)
                .into(hotelDetailsFragmentBinding.imvHotelDetails);
        hotelDetailsFragmentBinding.tvHotelNameDetails.setText(resultObjectHotel.getName());
        hotelDetailsFragmentBinding.tvDescriptionHotel.setText(resultObjectHotel.getDescription());
        hotelDetailsFragmentBinding.tvNameHotelDesc.setText(resultObjectHotel.getName());
        hotelDetailsFragmentBinding.tvHotelNameDetails.setText(resultObjectHotel.getName());
        phoneNo = resultObjectHotel.getPhone();
        title = resultObjectHotel.getName();
    }

    public void setUpQuickCallClick() {
        hotelDetailsFragmentBinding.ibtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNo));

                hotelDetailsFragment.getContext().startActivity(intent);
            }
        });
    }

    public void setupGoogleMap(){
        hotelDetailsFragmentBinding.ibtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleMapDialog googleMapDialog = new GoogleMapDialog();
                Bundle bundle = new Bundle();
                bundle.putDouble(Constants.LAT_KEY,Double.parseDouble(trip.getLat()));
                bundle.putDouble(Constants.LON_KEY,Double.parseDouble(trip.getLon()));
                bundle.putString(Constants.HOTEL_NAME_KEY, title);

                googleMapDialog.setArguments(bundle);
                googleMapDialog.show(hotelDetailsFragment.getActivity().getSupportFragmentManager(),null);

            }
        });
    }

    public void setupBackClick(){
        hotelDetailsFragmentBinding.btnBackHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressedHotelsDetailsListener("hotelsF");
            }
        });
    }

    public void setupFavoriteClick(){
        hotelDetailsFragmentBinding.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFavorite){
                    tripMethods.insertTrip(trip);
                    userTripMethods.insertUserTrip(new UserTrip(trip.getTripId(),StaticVar.USER_ID));
                    DrawableCompat.setTint(
                            DrawableCompat.wrap(hotelDetailsFragmentBinding.btnFavorite.getDrawable()),
                            ContextCompat.getColor(hotelDetailsFragment.getContext(), R.color.another_nice_color)
                    );
                    isFavorite = !isFavorite;
                }else {
                    userTripMethods.deleteBudget(StaticVar.USER_ID,trip.getTripId());
                    DrawableCompat.setTint(
                            DrawableCompat.wrap(hotelDetailsFragmentBinding.btnFavorite.getDrawable()),
                            ContextCompat.getColor(hotelDetailsFragment.getContext(), R.color.white)
                    );
                    isFavorite = !isFavorite;

                }
            }
        });
    }

    public void setupWeatherClick(){
        hotelDetailsFragmentBinding.ibtnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherDialog weatherDialog = new WeatherDialog();
                Bundle bundle = new Bundle();
                bundle.putDouble(Constants.LAT_KEY,Double.parseDouble(trip.getLat()));
                bundle.putDouble(Constants.LON_KEY,Double.parseDouble(trip.getLon()));
                weatherDialog.setArguments(bundle);
                weatherDialog.show(hotelDetailsFragment.getActivity().getSupportFragmentManager(),null);
            }
        });
    }

    private void checkIfIsFavorite(){
        Single<Integer> single = userTripMethods.checkIfIsFavorite(StaticVar.USER_ID,trip.getTripId());
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        if(integer==0){
                            isFavorite = false;
                            DrawableCompat.setTint(
                                    DrawableCompat.wrap(hotelDetailsFragmentBinding.btnFavorite.getDrawable()),
                                    ContextCompat.getColor(hotelDetailsFragment.getContext(), R.color.white)
                            );
                        }else{
                            isFavorite = true;
                            DrawableCompat.setTint(
                                    DrawableCompat.wrap(hotelDetailsFragmentBinding.btnFavorite.getDrawable()),
                                    ContextCompat.getColor(hotelDetailsFragment.getContext(), R.color.another_nice_color)
                            );
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void openDB(){
        UserTripDAO userTrip = AppRoomDatabase.getInstance(hotelDetailsFragment.getContext()).userTripDAO();
        userTripMethods = UserTripMethods.getInstance(userTrip);
        TripDAO tripDAO = AppRoomDatabase.getInstance(hotelDetailsFragment.getContext()).tripDAO();
        tripMethods = TripMethods.getInstance(tripDAO);


    }
}

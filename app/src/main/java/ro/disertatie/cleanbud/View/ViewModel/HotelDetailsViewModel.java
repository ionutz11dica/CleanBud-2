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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.API.APIClient;
import ro.disertatie.cleanbud.View.API.APIService;
import ro.disertatie.cleanbud.View.Activities.StartActivity;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.GoogleMapDialog;
import ro.disertatie.cleanbud.View.Fragments.Dialogs.WeatherDialog;
import ro.disertatie.cleanbud.View.Fragments.HotelDetailsFragment;
import ro.disertatie.cleanbud.View.Fragments.HotelsFragment;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.databinding.HotelDetailsFragmentBinding;

public class HotelDetailsViewModel {
    private HotelDetailsFragment hotelDetailsFragment;
    private HotelDetailsFragmentBinding hotelDetailsFragmentBinding;
    private APIService apiService;
    private double lat = 0.0;
    private double lon = 0.0;
    private String phoneNo = "";
    private String title = "";
    private HotelDetailsFragment.HotelsDetailsListener listener;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HotelDetailsViewModel(HotelDetailsFragment hotelDetailsFragment, HotelDetailsFragmentBinding hotelDetailsFragmentBinding) {
        this.hotelDetailsFragment = hotelDetailsFragment;
        this.hotelDetailsFragmentBinding = hotelDetailsFragmentBinding;
        listener = (HotelDetailsFragment.HotelsDetailsListener) hotelDetailsFragment.getContext();
        apiService = APIClient.getRetrofit4().create(APIService.class);
        initToolbar();
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
                listener.onBackButtonPressedHotelsDetailsListener("hotelsF");
            }
        });
    }

    public void setupViews(ResultObjectHotel resultObjectHotel) {


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
        lat = Double.parseDouble(resultObjectHotel.getLat());
        lon = Double.parseDouble(resultObjectHotel.getLon());
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
                bundle.putDouble(Constants.LAT_KEY,lat);
                bundle.putDouble(Constants.LON_KEY,lon);
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

    public void setupWeatherClick(){
        hotelDetailsFragmentBinding.ibtnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherDialog weatherDialog = new WeatherDialog();
                Bundle bundle = new Bundle();
                bundle.putDouble(Constants.LAT_KEY,lat);
                bundle.putDouble(Constants.LON_KEY,lon);
                weatherDialog.setArguments(bundle);
                weatherDialog.show(hotelDetailsFragment.getActivity().getSupportFragmentManager(),null);
            }
        });
    }
}

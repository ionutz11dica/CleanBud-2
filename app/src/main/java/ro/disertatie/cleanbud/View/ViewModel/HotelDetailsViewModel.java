package ro.disertatie.cleanbud.View.ViewModel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.API.APIClient;
import ro.disertatie.cleanbud.View.API.APIService;
import ro.disertatie.cleanbud.View.Activities.StartActivity;
import ro.disertatie.cleanbud.View.Fragments.HotelDetailsFragment;
import ro.disertatie.cleanbud.View.Fragments.HotelsFragment;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.databinding.HotelDetailsFragmentBinding;

public class HotelDetailsViewModel {
    private HotelDetailsFragment hotelDetailsFragment;
    private HotelDetailsFragmentBinding hotelDetailsFragmentBinding;
    private APIService apiService;
    private double lat = 0.0;
    private double lon = 0.0;
    private String phoneNo = "";
    private HotelDetailsFragment.HotelsDetailsListener listener;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HotelDetailsViewModel(HotelDetailsFragment hotelDetailsFragment, HotelDetailsFragmentBinding hotelDetailsFragmentBinding) {
        this.hotelDetailsFragment = hotelDetailsFragment;
        this.hotelDetailsFragmentBinding = hotelDetailsFragmentBinding;
        listener = (HotelDetailsFragment.HotelsDetailsListener) hotelDetailsFragment.getContext();
//        initToolbar();
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

    public void setupBackClick(){
        hotelDetailsFragmentBinding.btnBackHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressedHotelsDetailsListener("hotelsF");
            }
        });
    }
}

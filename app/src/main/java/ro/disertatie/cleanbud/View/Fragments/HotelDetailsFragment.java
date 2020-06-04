package ro.disertatie.cleanbud.View.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.ViewModel.HotelDetailsViewModel;
import ro.disertatie.cleanbud.databinding.HotelDetailsFragmentBinding;

public class HotelDetailsFragment extends Fragment {
    private HotelDetailsViewModel hotelDetailsViewModel;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HotelDetailsFragmentBinding hotelDetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.hotel_details_fragment,container,false);
        hotelDetailsViewModel = new HotelDetailsViewModel(this,hotelDetailsFragmentBinding);
        hotelDetailsViewModel.setUpQuickCallClick();
        hotelDetailsViewModel.setupBackClick();
        hotelDetailsViewModel.setupFavoriteClick();
        hotelDetailsViewModel.setupGoogleMap();
        hotelDetailsViewModel.setupWeatherClick();
        return hotelDetailsFragmentBinding.getRoot();
    }



    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnHomeFragmentInteractionListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    public interface HotelsDetailsListener{
        void onBackButtonPressedHotelsDetailsListener(String string);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            Bundle bundle = getArguments();
            if(bundle!=null)
            {
                ResultObjectHotel id = (ResultObjectHotel) bundle.getSerializable(Constants.HOTELS_KEY);
                if(id != null){
                    hotelDetailsViewModel.setupViews(id);
                }
            }
        }
    }
}

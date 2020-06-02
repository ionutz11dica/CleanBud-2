package ro.disertatie.cleanbud.View.ViewModel;

import android.os.Build;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Adapter.HotelsAdapter;
import ro.disertatie.cleanbud.View.Fragments.HotelsFragment;
import ro.disertatie.cleanbud.View.Fragments.TripFilterFragment;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.databinding.HotelsFragmentBinding;

public class HotelsViewModel {
    private HotelsFragment hotelsFragment;
    private HotelsFragmentBinding hotelsFragmentBinding;
    private HotelsFragment.HotelsListener listener;
    private ArrayList<ResultObjectHotel> hotelArrayList;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HotelsViewModel(HotelsFragment hotelsFragment, HotelsFragmentBinding hotelsFragmentBinding) {
        this.hotelsFragment = hotelsFragment;
        this.hotelsFragmentBinding = hotelsFragmentBinding;
        listener = (HotelsFragment.HotelsListener) hotelsFragment.getContext();
        hotelArrayList = new ArrayList<>();
        initToolbar();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        hotelsFragmentBinding.hotelsToolbar.setTitleTextAppearance(hotelsFragment.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        hotelsFragmentBinding.hotelsToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        backListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        hotelsFragmentBinding.hotelsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressedHotelsListener("tripFilterF");
            }
        });
    }

    public void setAdapterHotels(ArrayList<ResultObjectHotel> resultObjectHotels){
        HotelsAdapter hotelsAdapter = new HotelsAdapter(hotelsFragment.getActivity(),resultObjectHotels,hotelsFragment);
        hotelArrayList = resultObjectHotels;
        hotelsFragmentBinding.hotelsLv.setAdapter(hotelsAdapter);
    }



    public void onHotelClick(int pos){
        listener.passHotelDetailsToFragment(hotelArrayList.get(pos),"hotelsDetailsF");
    }
}

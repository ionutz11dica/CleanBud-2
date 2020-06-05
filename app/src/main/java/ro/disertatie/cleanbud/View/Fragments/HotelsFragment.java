package ro.disertatie.cleanbud.View.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Adapter.HotelsAdapter;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.ViewModel.HotelsViewModel;
import ro.disertatie.cleanbud.databinding.HotelsFragmentBinding;

public class HotelsFragment extends Fragment implements HotelsAdapter.HotelAdapterListener {
    private HotelsViewModel hotelsViewModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HotelsFragmentBinding hotelsFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.hotels_fragment,container,false);
         hotelsViewModel = new HotelsViewModel(this,hotelsFragmentBinding);


        return hotelsFragmentBinding.getRoot();
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

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClickAdapter(int id) {
        hotelsViewModel.onHotelClick(id);
    }

    public interface HotelsListener{
        void onBackButtonPressedHotelsListener(String string);
        void passHotelDetailsToFragment(ResultObjectHotel resultObjectHotel,String fragment);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            Bundle bundle = getArguments();
            if(bundle!=null)
            {
                ArrayList<ResultObjectHotel> list = (ArrayList<ResultObjectHotel>) bundle.getSerializable(Constants.HOTELS_LIST_KEY);
                if(list!=null && !list.isEmpty()){
                    hotelsViewModel.setAdapterHotels(list);
                }
            }
        }
    }






}

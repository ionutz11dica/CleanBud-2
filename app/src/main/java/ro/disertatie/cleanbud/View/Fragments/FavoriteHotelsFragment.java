package ro.disertatie.cleanbud.View.Fragments;

import android.content.Context;
import android.content.Intent;
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

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Models.Trip;
import ro.disertatie.cleanbud.View.Utils.Constants;
import ro.disertatie.cleanbud.View.Utils.StaticVar;
import ro.disertatie.cleanbud.View.ViewModel.FavoriteHotelsViewModel;
import ro.disertatie.cleanbud.databinding.FavoriteHotelsFragmentBinding;
import ro.disertatie.cleanbud.databinding.ReportsFragmentBinding;

import static android.app.Activity.RESULT_OK;

public class FavoriteHotelsFragment extends Fragment {



    private FavoriteHotelsListener listener;
    private FavoriteHotelsViewModel favoriteHotelsViewModel;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FavoriteHotelsFragmentBinding favoriteHotelsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.favorite_hotels_fragment,container,false);
        favoriteHotelsViewModel = new FavoriteHotelsViewModel(this,favoriteHotelsFragmentBinding);
        favoriteHotelsViewModel.openDb();
        favoriteHotelsViewModel.onFavoriteClick();
        favoriteHotelsViewModel.searchFavHotels();
        return favoriteHotelsFragmentBinding.getRoot();
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



    public interface FavoriteHotelsListener{
        void onBackButtonPressedFavorite(String string);
        void passDataToHotelDetailsFav(Trip trip, String string);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            favoriteHotelsViewModel.getMyTrips(StaticVar.USER_ID);
        }
    }


}
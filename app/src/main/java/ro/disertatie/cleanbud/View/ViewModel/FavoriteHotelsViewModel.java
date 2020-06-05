package ro.disertatie.cleanbud.View.ViewModel;

import android.os.Build;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Adapter.FavoriteHotelsAdapter;
import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.UserTripDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.EconomyBudgetMethods;
import ro.disertatie.cleanbud.View.Database.DAOMethods.UserTripMethods;
import ro.disertatie.cleanbud.View.Fragments.FavoriteHotelsFragment;
import ro.disertatie.cleanbud.View.Models.Trip;
import ro.disertatie.cleanbud.databinding.FavoriteHotelsFragmentBinding;

public class FavoriteHotelsViewModel {
    private FavoriteHotelsFragment favoriteHotelsFragment;
    private FavoriteHotelsFragmentBinding favoriteHotelsFragmentBinding;
    private FavoriteHotelsFragment.FavoriteHotelsListener listener;
    private FavoriteHotelsAdapter adapter;
    private UserTripMethods userTripMethods;
    private List<Trip> trips;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FavoriteHotelsViewModel(FavoriteHotelsFragment favoriteHotelsFragment, FavoriteHotelsFragmentBinding favoriteHotelsFragmentBinding) {
        this.favoriteHotelsFragment = favoriteHotelsFragment;
        this.favoriteHotelsFragmentBinding = favoriteHotelsFragmentBinding;
        listener = (FavoriteHotelsFragment.FavoriteHotelsListener) favoriteHotelsFragment.getContext();
        initToolbar();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        favoriteHotelsFragmentBinding.favoriteToolbar.setTitleTextAppearance(favoriteHotelsFragment.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        favoriteHotelsFragmentBinding.favoriteToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        backListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        favoriteHotelsFragmentBinding.favoriteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressedFavorite("tripFilterF");
            }
        });
    }



    private void setAdapter(List<Trip> trips){
        if(this.trips!=null){
            this.trips.clear();
            this.trips.addAll(trips);
        }else{
            this.trips = trips;

        }

        if(adapter == null){
            adapter = new FavoriteHotelsAdapter(favoriteHotelsFragment.getActivity(),this.trips);
            if(Looper.myLooper() == Looper.getMainLooper()){
                favoriteHotelsFragmentBinding.lvFavorite.setAdapter(adapter);

            }else{
                favoriteHotelsFragmentBinding.lvFavorite.post(() -> {
                    favoriteHotelsFragmentBinding.lvFavorite.setAdapter(adapter);
                });
            }
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    public void getMyTrips(int userId){
        Single<List<Trip>> single = userTripMethods.getUserFavoriteHotels(userId);
        single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Trip>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Trip> trips) {
                        setAdapter(trips);
                    }

                    @Override
                    public void onError(Throwable e) {
                        trips = new ArrayList<>();
                    }
                });
    }

    public void onFavoriteClick(){
        favoriteHotelsFragmentBinding.lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.passDataToHotelDetailsFav(trips.get(position),"hotelsDetailsF");
            }
        });
    }

    public void searchFavHotels(){
        favoriteHotelsFragmentBinding.favoriteSearchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FavoriteHotelsViewModel.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void openDb(){
        UserTripDAO userTripDAO = AppRoomDatabase.getInstance(favoriteHotelsFragment.getContext()).userTripDAO();
        userTripMethods = UserTripMethods.getInstance(userTripDAO);
    }
}

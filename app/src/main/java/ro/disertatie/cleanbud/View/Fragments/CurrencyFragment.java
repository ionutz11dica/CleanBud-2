package ro.disertatie.cleanbud.View.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.API.APIClient;
import ro.disertatie.cleanbud.View.API.APIService;
import ro.disertatie.cleanbud.View.Models.Currency;
import ro.disertatie.cleanbud.View.Utils.CurrencyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.disertatie.cleanbud.View.ViewModel.CurrencyViewModel;
import ro.disertatie.cleanbud.databinding.CurrencyFragmentBinding;


public class CurrencyFragment  extends Fragment  {
    private CurrencyViewModel currencyViewModel;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CurrencyFragmentBinding currencyFragmentBinding =  DataBindingUtil.inflate(inflater,R.layout.currency_fragment,container,false);
        currencyViewModel = new CurrencyViewModel(this,currencyFragmentBinding);
        currencyViewModel.onChangeClick();
        currencyViewModel.onChangeAmountText();



        return currencyFragmentBinding.getRoot();
    }



//    private void getCurrencyRates() {
//        Call<CurrencyApi> call =  apiService.getCurrencyRates();
//        call.enqueue(new Callback<CurrencyApi>() {
//            @SuppressLint("DefaultLocale")
//            @Override
//            public void onResponse(Call<CurrencyApi> call, Response<CurrencyApi> response) {
//                Iterator hmIterator = response.body().getRates().entrySet().iterator();
//                DecimalFormat df = new DecimalFormat("#.##");
//               while (hmIterator.hasNext() ){
//                   Map.Entry mapElement = (Map.Entry)hmIterator.next();
//
//                   Currency currency = new Currency();
//                   currency.setCurrencyName(mapElement.getKey().toString());
//                   currency.setValue((Float) mapElement.getValue());
//
//               }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<CurrencyApi> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnHomeFragmentInteractionListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CurrencyFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    public interface CurrencyFragmentInteractionListener{
            void onBackButtonPressed(String fragment);
    }
}

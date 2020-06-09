package ro.disertatie.cleanbud.View.ViewModel;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;

import ro.disertatie.cleanbud.View.Database.AppRoomDatabase;
import ro.disertatie.cleanbud.View.Database.DAO.CurrencyDAO;
import ro.disertatie.cleanbud.View.Database.DAOMethods.CurrencyMethods;
import ro.disertatie.cleanbud.View.Models.Currency;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.API.APIClient;
import ro.disertatie.cleanbud.View.API.APIService;
import ro.disertatie.cleanbud.View.Fragments.CurrencyFragment;
import ro.disertatie.cleanbud.View.Utils.CurrencyApi;
import ro.disertatie.cleanbud.View.View.ProgressDialogClass;
import ro.disertatie.cleanbud.databinding.CurrencyFragmentBinding;

public class CurrencyViewModel {
    private CurrencyFragment currencyFragment;
    private CurrencyFragmentBinding currencyFragmentBinding;
    private CurrencyFragment.CurrencyFragmentInteractionListener listener;
    private APIService apiService;
    private CurrencyMethods currencyMethods;
    private List<Currency> currencies = new ArrayList<>();
    private boolean isUSD = false;
    private float total = 1.0f;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CurrencyViewModel(CurrencyFragment currencyFragment, CurrencyFragmentBinding currencyFragmentBinding) {
        this.currencyFragment = currencyFragment;
        this.currencyFragmentBinding = currencyFragmentBinding;
        listener = (CurrencyFragment.CurrencyFragmentInteractionListener) currencyFragment.getContext();
        apiService = APIClient.getRetrofit().create(APIService.class);
        initToolbar();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        currencyFragmentBinding.currencyToolbar.setTitleTextAppearance(currencyFragment.getContext(), R.style.Widget_AppCompat_ActionBar_Solid);
        currencyFragmentBinding.currencyToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        openDb();
        getCurrencyRates();
        backListener();
    }

    private void getCurrencyRates() {
        Call<CurrencyApi> call =  apiService.getCurrencyRates();
        ProgressDialogClass progressDialogClass = new ProgressDialogClass(currencyFragment.getActivity());
        progressDialogClass.showDialog("Fetching data..","Please wait..");
        call.enqueue(new Callback<CurrencyApi>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<CurrencyApi> call, Response<CurrencyApi> response) {
                Iterator hmIterator = response.body().getRates().entrySet().iterator();
                DecimalFormat df = new DecimalFormat("#.##");
                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry) hmIterator.next();

                    if (mapElement.getKey().toString().equals("USD")) {
                        Currency currency = new Currency();
                        currency.setCurrencyId(0);
                        currency.setCurrencyName("American Dollar");
                        currency.setDrawable(R.drawable.ic_flag);
                        currency.setCurrencyShortCut(mapElement.getKey().toString());
                        currency.setValue((Float) mapElement.getValue());
                        currencies.add(currency);
                    } else if (mapElement.getKey().toString().equals("RON")) {
                        Currency currency = new Currency();
                        currency.setCurrencyId(1);
                        currency.setCurrencyShortCut(mapElement.getKey().toString());
                        currency.setDrawable(R.drawable.ic_romania);

                        currency.setCurrencyName("Romanian Leu");
                        currency.setValue((Float) mapElement.getValue());
                        currencies.add(currency);
                    }
                }

                Currency currency = new Currency();
                currency.setCurrencyId(2);
                currency.setCurrencyName("Euro");
                currency.setDrawable(R.drawable.ic_europe);

                currency.setCurrencyShortCut("EUR");
                currency.setValue(1.0f);
                currencies.add(currency);

                currencyMethods.insertCurrency(currencies);
                progressDialogClass.dismissDialog();
                setInitialView(response.body().getDate());
            }

            @Override
            public void onFailure(Call<CurrencyApi> call, Throwable t) {

            }
        });

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void setInitialView(String date){
        currencyFragmentBinding.tvDateRates.setText("Based on data from: "+date);
        currencyFragmentBinding.tvEquivalent.setText("1 EUR \u2248 " + String.format("%.3f",currencies.get(1).getValue()));
        currencyFragmentBinding.tvCurrencyAmountExchanged.setText(String.format("%.3f",currencies.get(1).getValue()));
        currencyFragmentBinding.tvCurrencyAmount.setText("1");
        currencyFragmentBinding.tvCurrencyNameEchanged.setText(currencies.get(1).getCurrencyName());
        currencyFragmentBinding.tvCurrencyName.setText("Euro");


    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void reInitViews(Currency currency){
        float value = (currencies.get(1).getValue()/currency.getValue())*total;

        currencyFragmentBinding.tvEquivalent.setText("1 "+ currency.getCurrencyShortCut() +" \u2248 " + String.format("%.3f",currencies.get(1).getValue()/currency.getValue()));
        currencyFragmentBinding.tvCurrencyAmountExchanged.setText(String.format("%.3f",value));
        currencyFragmentBinding.tvCurrencyAmount.setText(String.format("%.0f",total));
        currencyFragmentBinding.tvCurrencyNameEchanged.setText(currencies.get(1).getCurrencyName());
        currencyFragmentBinding.tvCurrencyName.setText(currency.getCurrencyName());


    }

    public void onChangeClick(){
        currencyFragmentBinding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isUSD){
                    isUSD = true;

                    reInitViews(currencies.get(0));
                }else{
                    isUSD = false;

                    reInitViews(currencies.get(2));

                }
            }
        });
    }

    public void onChangeAmountText(){
        currencyFragmentBinding.tvCurrencyAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    currencyFragmentBinding.tvCurrencyAmount.setText("1");
                    total = 1.0f;
                }else{
                     total = Float.parseFloat(s.toString());
                    if(isUSD){
                        float value = (currencies.get(1).getValue()/currencies.get(0).getValue())*total;
                        currencyFragmentBinding.tvCurrencyAmountExchanged.setText(String.format("%.3f",value));

                    }else{
                        float value = currencies.get(1).getValue()/currencies.get(2).getValue()*total;
                        currencyFragmentBinding.tvCurrencyAmountExchanged.setText(String.format("%.3f",value));


                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void backListener(){
        currencyFragmentBinding.currencyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressed("homeF");
            }
        });
    }

    public void openDb(){
        CurrencyDAO currencyDAO = AppRoomDatabase.getInstance(currencyFragment.getContext()).currencyDAO();
        currencyMethods = CurrencyMethods.getInstance(currencyDAO);
    }
}

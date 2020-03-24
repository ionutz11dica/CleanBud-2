package ro.disertatie.cleanbud.View.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import ro.disertatie.cleanbud.View.Adapter.CurrencyRecylerAdapter;
import ro.disertatie.cleanbud.View.Models.Currency;
import ro.disertatie.cleanbud.View.Utils.CurrencyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CurrencyFragment  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //Delegate -> Listener
    private CurrencyFragmentInteractionListener listener;

    @BindView(R.id.currency_toolbar)
    Toolbar toolbar;

    //Retrofit -> Networking
    private APIService apiService;

    //List

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private CurrencyRecylerAdapter adapter;
    private int currentPage = PAGE_START;

    private List<Currency> items=new ArrayList<>();
    LinearLayoutManager layoutManager;

    //Components
    @BindView(R.id.tv_euro_currency)
    TextView tvEuro;
    @BindView(R.id.tv_my_budget_euro)
    TextView tvMyBudEuro;

    @BindView(R.id.tv_dolar_currency)
    TextView tvDollar;
    @BindView(R.id.tv_my_budget_dolar)
    TextView tvMyBudDollar;

    @BindView(R.id.tv_ron_currency)
    TextView tvRon;
    @BindView(R.id.tv_my_budget_ron)
    TextView tvMyBudRon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.currency_fragment,container,false);
        ButterKnife.bind(this,view);
        initComponents();

        getCurrencyRates();
        backListener();




        return view;
    }
    private static final int PAGE_START = 0;
    private void initComponents(){
        apiService = APIClient.getRetrofit().create(APIService.class);
        toolbar.setTitle("Currency");
        toolbar.setTitleTextAppearance(getContext(),R.style.Widget_AppCompat_ActionBar_Solid);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        swipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CurrencyRecylerAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(adapter);



    }

    private void backListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressed("homeF");
            }
        });
    }

    private void getCurrencyRates() {
        Call<CurrencyApi> call =  apiService.getCurrencyRates();
        call.enqueue(new Callback<CurrencyApi>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<CurrencyApi> call, Response<CurrencyApi> response) {
                Iterator hmIterator = response.body().getRates().entrySet().iterator();
                DecimalFormat df = new DecimalFormat("#.##");
               while (hmIterator.hasNext() ){
                   Map.Entry mapElement = (Map.Entry)hmIterator.next();

                   Currency currency = new Currency();
                   currency.setCurrencyName(mapElement.getKey().toString());
                   currency.setValue((Float) mapElement.getValue());
                   switch (mapElement.getKey().toString()) {
                       case "RON" : {
                           tvRon.setText("Value: "+String.format("%.2f",mapElement.getValue())+"€");
                           break;
                       }
                       case "USD" : {
                           tvDollar.setText("Value: "+String.format("%.2f",mapElement.getValue())+"$");
                           break;
                       }
                       case "EUR" : {
                           tvEuro.setText("Value: "+String.format("%.2f",mapElement.getValue())+"LEI");
                           break;
                       }
                   }

                   items.add(currency);
               }
                /**
                 * manage progress view
                 */
                if (currentPage != PAGE_START) adapter.removeLoading();

                adapter.addItems(items);


                //Toast.makeText(getContext(),String.valueOf(itemCount),Toast.LENGTH_LONG).show();

                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<CurrencyApi> call, Throwable t) {

            }
        });

    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnHomeFragmentInteractionListener) {
            listener = (CurrencyFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CurrencyFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onRefresh() {

        adapter.clear();
        getCurrencyRates();
    }

    public interface CurrencyFragmentInteractionListener{
            void onBackButtonPressed(String fragment);
    }
}

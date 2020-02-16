package licenta.books.cleanbud.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.API.APIClient;
import licenta.books.cleanbud.View.API.APIService;
import licenta.books.cleanbud.View.Utils.CurrencyApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyFragment  extends Fragment {

    //Delegate -> Listener
    private CurrencyFragmentInteractionListener listener;

    @BindView(R.id.currency_toolbar)
    Toolbar toolbar;

    //Retrofit -> Networking
    private APIService apiService;

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

    private void initComponents(){
        apiService = APIClient.getRetrofit().create(APIService.class);
        toolbar.setTitle("Currency");
        toolbar.setTitleTextAppearance(getContext(),R.style.Widget_AppCompat_ActionBar_Solid);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
    }

    private void backListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackButtonPressed(getTargetFragment());
            }
        });
    }

    private void getCurrencyRates() {
        Call<CurrencyApi> call =  apiService.getCurrencyRates();
        call.enqueue(new Callback<CurrencyApi>() {
            @Override
            public void onResponse(Call<CurrencyApi> call, Response<CurrencyApi> response) {
                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();

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

    public interface CurrencyFragmentInteractionListener{
            void onBackButtonPressed(Fragment fragment);
    }
}

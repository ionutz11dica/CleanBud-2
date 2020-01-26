package licenta.books.cleanbud.View.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import licenta.books.cleanbud.R;

public class HomeFragment extends Fragment {

    //Delegate -> Listener
    private OnHomeFragmentInteractionListener listener;

    //Attributes
    @BindView(R.id.cv_myreports)
    CardView cvReports;

    @BindView(R.id.cv_myBudget)
    CardView cvBudgets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        ButterKnife.bind(this,view);
        listener.onHomeButtonsPressed(55555);

        return view;
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            listener = (OnHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnHomeFragmentInteractionListener{
        void onHomeButtonsPressed(int idBtn);
    }
}

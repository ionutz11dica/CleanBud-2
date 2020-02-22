package licenta.books.cleanbud.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import licenta.books.cleanbud.R;

public class BudgetFragments extends Fragment {
    private BudgetInteractionListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.budget_fragment, container, false);

        return view;
    }
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnHomeFragmentInteractionListener) {
            listener = (BudgetInteractionListener) context;
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



    public interface BudgetInteractionListener{
        void onBudgetListener();
    }
}

package licenta.books.cleanbud.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.Models.Currency;

public class CurrencyRecylerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private List<Currency> currencies;
    public CurrencyRecylerAdapter(List<Currency> postItems) {
        this.currencies = postItems;
    }
    @NonNull @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_row, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_loading, parent, false));
            default:
                return null;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }
    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == currencies.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
    @Override
    public int getItemCount() {
        return currencies == null ? 0 : currencies.size();
    }
    public void addItems(List<Currency> postItems) {
        currencies = new ArrayList<>();
        currencies.addAll(postItems);
        notifyDataSetChanged();
    }
    public void addLoading() {
        isLoaderVisible = true;
        currencies.add(new Currency());
        notifyItemInserted(currencies.size() - 1);
    }
    public void removeLoading() {
        isLoaderVisible = false;
        int position = currencies.size() - 1;
        Currency item = getItem(position);
        if (item != null) {
            currencies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void clear() {
         currencies.clear();
        notifyDataSetChanged();
    }
    Currency getItem(int position) {
        return currencies.get(position);
    }
    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewDescription)
        TextView textViewDescription;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        protected void clear() {
        }
        public void onBind(int position) {
            super.onBind(position);
            Currency item = currencies.get(position);
            textViewTitle.setText(item.getCurrencyName());
            textViewDescription.setText(String.valueOf(item.getValue()));
        }
    }
    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @Override
        protected void clear() {
        }
    }
}

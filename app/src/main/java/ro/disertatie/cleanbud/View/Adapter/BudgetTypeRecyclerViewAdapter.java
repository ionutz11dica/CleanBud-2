package ro.disertatie.cleanbud.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Models.BudgetType;

public class BudgetTypeRecyclerViewAdapter extends RecyclerView.Adapter<BudgetTypeRecyclerViewAdapter.ViewHolder> {

    private List<Integer> mViewColors;
    private List<BudgetType> mBudgetTypes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context ;
    int selected_position = 0;
    // data is passed into the constructor
    public BudgetTypeRecyclerViewAdapter(Context context, List<Integer> colors, List<BudgetType> budgets) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mViewColors = colors;
        this.mBudgetTypes = budgets;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_budget_type, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int color = mViewColors.get(position);
        BudgetType budget = mBudgetTypes.get(position);
        holder.myBudgetLinear.setBackgroundColor(color);
        holder.myBudgetLinearTitle.setBackgroundColor(color);
        holder.myView.setImageDrawable(ContextCompat.getDrawable(context,budget.getIdImage()));
        holder.myTextView.setText(budget.getTitle());

        if (selected_position == position) {
            holder.itemView.setAlpha( 0.5f  );

        } else {
            holder.itemView.setAlpha( 1  );

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected_position==position){
                    selected_position=-1;
                    mClickListener.onItemClick(v,selected_position);
                    notifyDataSetChanged();
                    return;
                }
                selected_position = position;
                mClickListener.onItemClick(v,selected_position);
                notifyDataSetChanged();

            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mBudgetTypes.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        LinearLayout myBudgetLinear;
        LinearLayout myBudgetLinearTitle;
        ImageView myView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myBudgetLinear = itemView.findViewById(R.id.ll_budget_type);
            myBudgetLinearTitle = itemView.findViewById(R.id.ll_budget_title);
            myView = itemView.findViewById(R.id.type_budget_imv);
            myTextView = itemView.findViewById(R.id.type_budget_tv);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
////            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//
//        }
    }

    // convenience method for getting data agt click position
    public BudgetType getItem(int id) {
        return mBudgetTypes.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
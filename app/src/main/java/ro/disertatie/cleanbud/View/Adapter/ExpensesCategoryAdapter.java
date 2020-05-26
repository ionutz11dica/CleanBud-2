package ro.disertatie.cleanbud.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.ExpenseCategory;

public class ExpensesCategoryAdapter extends BaseAdapter {
    private Context context;
    private ExpenseCategory[] expenseCategories;

    public ExpensesCategoryAdapter(Context context, ExpenseCategory[] expenseCategories) {
        this.context = context;
        this.expenseCategories = expenseCategories;
    }


    @Override
    public int getCount() {
        return expenseCategories.length;
    }

    @Override
    public Object getItem(int position) {
        return expenseCategories[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        gridView = inflater.inflate(R.layout.row_expense_category, null, false);

        ImageView btnColor = gridView.findViewById(R.id.icon_grid);
        TextView lable = gridView.findViewById(R.id.lable_grid);

        btnColor.setBackground(ContextCompat.getDrawable(context, expenseCategories[position].getImageId()));
        lable.setText(expenseCategories[position].getTitleExpCategory());

        return gridView;
    }
}
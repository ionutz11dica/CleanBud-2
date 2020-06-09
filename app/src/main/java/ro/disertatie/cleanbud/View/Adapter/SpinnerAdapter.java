package ro.disertatie.cleanbud.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Activities.BudgetCreatorActivity;
import ro.disertatie.cleanbud.View.Models.Currency;
import ro.disertatie.cleanbud.View.Utils.SpinnerClass;

public class SpinnerAdapter extends ArrayAdapter<Currency> {

    LayoutInflater flater;



    public SpinnerAdapter(Activity context, int textviewId, List<Currency> list){

        super(context,textviewId, list);
        flater = context.getLayoutInflater();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Currency rowItem = getItem(position);

        @SuppressLint("ViewHolder") View rowview = flater.inflate(R.layout.row_spinner,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.state_tv);
        txtTitle.setText(rowItem.getCurrencyName());

        ImageView imageView = (ImageView) rowview.findViewById(R.id.icon_imv);
        imageView.setImageResource(rowItem.getDrawable());

        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(R.layout.row_spinner ,parent, false);
        }
        Currency rowItem = getItem(position);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.state_tv);
        txtTitle.setText(rowItem.getCurrencyName());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon_imv);
        imageView.setImageResource(rowItem.getDrawable());
        return convertView;
    }
}
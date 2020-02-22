package licenta.books.cleanbud.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import licenta.books.cleanbud.R;
import licenta.books.cleanbud.View.Utils.SpinnerClass;

public class SpinnerAdapter extends ArrayAdapter<SpinnerClass> {

    LayoutInflater flater;



    public SpinnerAdapter(Activity context,int resouceId, int textviewId, List<SpinnerClass> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpinnerClass rowItem = getItem(position);

        @SuppressLint("ViewHolder") View rowview = flater.inflate(R.layout.row_spinner,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.state_tv);
        txtTitle.setText(rowItem.getText());

        ImageView imageView = (ImageView) rowview.findViewById(R.id.icon_imv);
        imageView.setImageResource(rowItem.getIdImg());

        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(R.layout.row_spinner ,parent, false);
        }
        SpinnerClass rowItem = getItem(position);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.state_tv);
        txtTitle.setText(rowItem.getText());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon_imv);
        imageView.setImageResource(rowItem.getIdImg());
        return convertView;
    }
}
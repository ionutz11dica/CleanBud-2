package ro.disertatie.cleanbud.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.Income;
import ro.disertatie.cleanbud.View.Utils.RecordProtocol;

public class HotelsAdapter extends BaseAdapter {
    private final Activity context;

    private ArrayList<ResultObjectHotel> hotels;
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd, MMM, yyyy");


    public HotelsAdapter(Activity context, ArrayList<ResultObjectHotel> hotels) {
        this.context = context;
        this.hotels = hotels;
    }


    @Override
    public int getCount() {
        return hotels.size();
    }

    @Override
    public Object getItem(int position) {
        return hotels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row_hotel, null, true);

        ImageView imvHotel = rowView.findViewById(R.id.imv_hotel);
        TextView nameHotel = rowView.findViewById(R.id.tv_hotel_name);
        TextView price = rowView.findViewById(R.id.tv_price);
        Button btnDetails = rowView.findViewById(R.id.btn_view_details);

        Glide
                .with(context)
                .load(hotels.get(position).getPhoto().getImages().getLarge().getUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imvHotel);
        nameHotel.setText(hotels.get(position).getName());
        price.setText(hotels.get(position).getPrice());





        return rowView;
    }


}

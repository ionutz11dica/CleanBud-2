package ro.disertatie.cleanbud.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Models.ApiModels.Hotels.ResultObjectHotel;
import ro.disertatie.cleanbud.View.Models.Trip;

public class FavoriteHotelsAdapter extends ArrayAdapter<Trip> {
    private final Activity context;
    private List<Trip> trips;
    private List<Trip> filteredList = new ArrayList<>();

    public FavoriteHotelsAdapter(Activity context, List<Trip> hotels) {
        super(context,R.layout.row_favorite,hotels);
        this.context = context;
        this.trips = hotels;
        this.filteredList.addAll(hotels);
    }





    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row_favorite, null, true);

        ImageView imvHotel = rowView.findViewById(R.id.imv_favorite);
        TextView nameHotel = rowView.findViewById(R.id.tv_name_hotel_favorite);
        TextView price = rowView.findViewById(R.id.tv_price_favorite);

        Glide
                .with(context)
                .load(trips.get(position).getPhoto())
                .centerCrop()
                .placeholder(R.drawable.ic_businessman)
                .into(imvHotel);
        nameHotel.setText(trips.get(position).getName());
        price.setText(trips.get(position).getPrice());




        return rowView;
    }
    @SuppressWarnings("unchecked")
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                constraint = constraint.toString().toLowerCase();


                if (constraint.toString().length() > 0) {
                    List<Trip> founded = new ArrayList<>();
                    for (Trip trip : trips) {
                        if(trip.getName().toLowerCase().startsWith(constraint.toString())){
                            founded.add(trip);
                        }
                    }
                    results.values = founded;
                    results.count = founded.size();
                } else {
                    results.values = filteredList;
                    results.count = filteredList.size();
                }



                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                clear();
                for (Trip trip : (List<Trip>) results.values) {
                    add(trip);
                }
                if(results.count == 0){
                    notifyDataSetInvalidated();
                }else{
                    notifyDataSetChanged();

                }
            }
        };
    }
}
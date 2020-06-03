package ro.disertatie.cleanbud.View.Fragments.Dialogs;

import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.Callable;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Utils.Constants;

public class GoogleMapDialog extends DialogFragment implements OnMapReadyCallback {

    private GoogleMap map;
    private static View view;
    public GoogleMapDialog(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.google_map_dialog, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mapFragment, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Bundle bundle = getArguments();
        if(bundle!=null){
            double lat = bundle.getDouble(Constants.LAT_KEY);
            double lon = bundle.getDouble(Constants.LON_KEY);
            String nameH = bundle.getString(Constants.HOTEL_NAME_KEY);
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            LatLng latLng = new LatLng(lat,lon);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(nameH);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            map.addMarker(markerOptions);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment f =  getFragmentManager().findFragmentById(R.id.mapFragment);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }
}

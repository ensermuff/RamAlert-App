package com.vcu.RamAlerts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class DisplayAlertFragment extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    public static DisplayAlertFragment Instance;
    public static GoogleMap mMap;
    private String vcuAlert = "";
    private String[] coordinates = new String[2];
    public static HashMap<LatLng, Marker> markerList = new HashMap<>();
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            DisplayAlertFragment.this.onMapReady(googleMap);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setInstance();
        View rootView = inflater.inflate(R.layout.fragment_display_alert, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        mMapView.getMapAsync(this);
        return rootView;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        setmMap(googleMap);
        if(!vcuAlert.equals("")){
            Geocoder geocoder = new Geocoder();
            AsyncTask task = geocoder.execute(vcuAlert);
            try {
                coordinates = (String[]) task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            double lat = Double.parseDouble(coordinates[0]);
            double lon = Double.parseDouble(coordinates[1]);
            //place a marker on our map
            LatLng alert = new LatLng(lat, lon);
            if(markerList.containsKey(alert)){
                markerList.get(alert).remove();
                markerList.clear();
            }
            else{
                MarkerOptions markerOptions = new MarkerOptions().position(alert).title("Vcu alert");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_1alert));
                Marker amarker = mMap.addMarker(markerOptions);
                markerList.put(alert, amarker);
                moveToCurrentLocation(alert);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(alert));
            }
        }
    }
    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }
    public Bitmap bitmapSizeByScale(Bitmap bitmapIn, float scall_zero_to_one_f) {

        Bitmap bitmapOut = Bitmap.createScaledBitmap(bitmapIn,
                Math.round(bitmapIn.getWidth() * scall_zero_to_one_f),
                Math.round(bitmapIn.getHeight() * scall_zero_to_one_f), false);
        return bitmapOut;
    }
    public void setVcuAlert(String alert){
        vcuAlert = alert;
    }
    public void setmMap(GoogleMap map){
        if(map != null){
            mMap = map;
        }
    }
    public void placeAlert(){
        if (mMap != null) onMapReady(mMap);
    }
    public void setInstance(){
        Instance = this;
    }

}
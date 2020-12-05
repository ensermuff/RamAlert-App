package com.vcu.RamAlerts;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
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
    private static Marker userMarker;
    private static double latitude;
    private static double longitude;

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
    public void displayUserLocation(){
        if (userMarker != null){
            userMarker.remove();
        }
        LocationSettings locationSettingsInstance = new LocationSettings();
        if (locationSettingsInstance.getLatitude() != 0 && locationSettingsInstance.getLongitude() != 0) {
            double lat = locationSettingsInstance.getLatitude();
            double lon = locationSettingsInstance.getLongitude();
            LatLng myUser = new LatLng(lat, lon);
            //user's location marker
            userMarker = mMap.addMarker(setMarkerOptions(myUser, "User location", R.drawable.user_location));
            markerList.put(myUser, userMarker);
        }
    }
    public void setAlertLatitude(double lat){
        this.latitude = lat;
    }
    public static double getAlertLatitude(){
        return latitude;
    }
    public void setAlertLongitude(double lon){
        this.longitude = lon;
    }
    public static double getAlertLongitude(){
        return longitude;
    }
    public MarkerOptions setMarkerOptions(LatLng position, String title, int icon){
        MarkerOptions markerOptions = new MarkerOptions().position(position).title(title);
        if(icon != -1){
            markerOptions.icon(BitmapDescriptorFactory.fromResource(icon));
        }
        return markerOptions;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setmMap(mMap);
        displayUserLocation();

        if (!vcuAlert.equals("")) {
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
            setAlertLatitude(lat);
            setAlertLongitude(lon);
            //place a marker on our map
            LatLng alert = new LatLng(lat, lon);
            if (markerList.containsKey(alert)) {
                markerList.get(alert).remove();
                markerList.clear();
            } else{
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(alert)
                        .title("Vcu alert");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.alert));
                CircleOptions circleOptions = new CircleOptions()
                        .center(alert)
                        .radius(250)
                        .strokeColor(Color.RED)
                        .fillColor(0x22FF6666)
                        .strokeWidth(5);
                // Get back the mutable Circle
                mMap.addCircle(circleOptions);
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
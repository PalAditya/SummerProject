package com.app.lenovo.summerproject;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(20.63936, 76.14712), new LatLng(28.20453, 91.34466));
        mMap.setLatLngBoundsForCameraTarget(BOUNDS_INDIA);
        mMap.setMinZoomPreference(5);
        LatLng Roorkee = new LatLng(29.8453, 77.8880);
        mMap.addMarker(new MarkerOptions().position(Roorkee).title("Marker in Roorkee"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Roorkee));
        mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(29.8453, 77.8880), new LatLng(22.5726, 88.3639))
                .width(5)
                .color(Color.RED));
        mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(20.9517, 85.0985), new LatLng(22.5726, 88.3639))
                .width(5)
                .color(Color.RED));
        LatLng Chennai=getLocationFromAddress(this,"Chennai");
        LatLng Mumbai=getLocationFromAddress(this,"Mumbai");
        if(Chennai!=null&&Mumbai!=null)
            mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(Chennai.latitude,Chennai.longitude), new LatLng(Mumbai.latitude,Mumbai.longitude))
                    .width(5)
                    .color(Color.RED));
        else
            Log.e("Nooooo :(",Mumbai.toString()+","+Chennai.toString());
    }
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {
            Log.e("Uff", ex.getMessage());
        }
        return p1;
    }
}

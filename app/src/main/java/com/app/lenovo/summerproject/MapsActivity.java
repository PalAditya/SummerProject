package com.app.lenovo.summerproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    DrawerLayout mDrawerLayout;
    private DatabaseReference mDatabase;
    String name[]={"Kolkata","Mumbai","Wyoming","South Dakota","California","Nevada","Utah","Colorado","Arizona","New Mexico",
            "Oklahama","Arkansas","Texas","Louisiana","Mississippi","Albama"};
    int names[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        String s1 = HelperClass.getSharedPreferencesString(getApplicationContext(), "Bp", "");
                        String s2 = HelperClass.getSharedPreferencesString(getApplicationContext(), "Temp", "");
                        String s3 = HelperClass.getSharedPreferencesString(getApplicationContext(), "Other", "");
                        String s4 = HelperClass.getSharedPreferencesString(getApplicationContext(), "Date", "");
                        boolean c1=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c1",false);
                        boolean c2=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c2",false);
                        boolean c3=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c3",false);
                        boolean c4=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c4",false);
                        boolean c5=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c5",false);
                        boolean c6=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c6",false);
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:
                                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                                try {
                                    Intent intent=new Intent(MapsActivity.this,ProfileHandling.class);
                                    startActivity(intent);
                                }catch (Exception e)
                                {
                                    Log.e("Activity",e.getMessage());
                                }
                                break;
                            case R.id.suggest:
                                Toast.makeText(getApplicationContext(),"Suggesting...",Toast.LENGTH_SHORT).show();
                                dummy2(c1,c2,c3,c4,c5,c6);
                                break;
                            case R.id.show_me:
                                Toast.makeText(getApplicationContext(),"Show Path",Toast.LENGTH_SHORT).show();
                                showInputDialog();
                                dummy("Kolkata","Mumbai");
                                break;
                            case R.id.Temp:
                                Toast.makeText(getApplicationContext(),"Temperatures...",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.add_mine:
                                Toast.makeText(getApplicationContext(),"My suggestion",Toast.LENGTH_LONG).show();
                                dummy3();
                                break;
                            case R.id.alert:
                                Toast.makeText(getApplicationContext(),"Alert",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(),"Uh-oh",Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
    }

    private void showInputDialog()
    {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        final EditText input2 = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input2.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setView(input2);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();*/
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
    private void dummy2(boolean c1,boolean c2,boolean c3,boolean c4,boolean c5,boolean c6)
    {
        boolean summer=true;//Will parse from the date later
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Suggestions...");
        TextView tv=new TextView(this);
        StringBuilder sb=new StringBuilder();
        if(summer)
            sb.append("You should wear cotton clothes\n");
        else
            sb.append("You shouuld wear cotton clothes\n");
        if(c1)
            sb=sb.append("Don't forget to take water bottles with you\n");
        if(c3)
            sb.append("Have you taken your migraine medicines?\n");
        if(c2)
            sb.append("Please wear a cap or hat if you haven't, also remember to avoid congested places. We don't want another" +
                    " fainting incident, do we?\n");
        if(c4)
            sb.append("Don't forget to take lotions. Also wear loose fitting clothes\n");
        if(c5)
            sb.append("Try to stay in shades\n");
        if(c6)
            sb.append("Remember to sit down at once if you're feeling tired. Also there's no shame in asking for help\n");
        tv.setText(sb.toString());
        builder.setView(tv);
        builder.show();
    }
    int index(String s)
    {
        for (int i=0;i<16;i++)
            if (name[i].equals(s))
                return i;
        return 100;
    }
    final void dummy(String s1,String s2)
    {
        //setContentView(R.layout.activity_maps);
        Algorithms obj=new Algorithms(16);
        double dist[]=new double[16];
        int parent[]=new int[16];
        /*int mapping[]={R.id.textView11,R.id.textView4,R.id.textView5,R.id.textView6,
                R.id.textView7,R.id.textView12,R.id.textView17,R.id.textView18
                ,R.id.textView3,R.id.textView16,R.id.textView13,R.id.textView14
                ,R.id.textView15,R.id.textView8,R.id.textView9,R.id.textview20};*/
        obj=obj.go();
        /*lhs.clear();
        lhs.add(0);
        lhs.add(7);*/
        int x=index(s1);
        int y=index(s2);
            parent[x]=x;
            try {
                obj.shortestPath(x, y, dist, parent);
                String s=dist[y] + "," + parent[y];
                Log.e("Umm",s+","+ Arrays.toString(parent));
                LinkIt(parent,y);
            }catch (Exception e)
            {
                Log.e("Testing...", e.getMessage());
            }
        }
    private void LinkIt(int par[],int x) {
        if(par[x]!=x) {
            LatLng a1=null,a2=null;
            a1=getLocationFromAddress(this,name[x]);
            a2=getLocationFromAddress(this,name[par[x]]);
            try {
                if (a1 != null && a2 != null) {
                    mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(a1.latitude, a1.longitude), new LatLng(a2.latitude, a2.longitude))
                            .width(5)
                            .color(Color.RED));
                    mMap.addMarker(new MarkerOptions().position(a1)).setTitle(name[x]);
                    mMap.addMarker(new MarkerOptions().position(a2)).setTitle(name[par[x]]);
                }
                else
                    Log.e("Nooooo :(", a1.toString() + "," + a2.toString());
            }catch(Exception e)
            {
                Log.e("Of course",e.getMessage());
            }
            LinkIt(par, par[x]);
        }
    }
    private void dummy3()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        SuggestionModel sm = new SuggestionModel("012", 1);
        mDatabase.child(name[0]).setValue(sm);
        //final String xr=name[4];
        final String str="45789";
        BackgroundTask backgroundTask=new BackgroundTask(this);
        backgroundTask.execute("1",str);
    }
}


package com.app.lenovo.summerproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    DrawerLayout mDrawerLayout;
    private DatabaseReference mDatabase;
    String name[] = {"Srinagar","Delhi","Jaipur","Lucknow","Patna","Dispur","Gandhinagar","Bhopal","Kolkata","Mumbai",
    "Bhubaneswar","Roorkee","Hyderabad","Bengaluru","Chennai","Thiruvananthapuram"};
    int names[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, name);
        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(2);
        Button button = findViewById(R.id.nextbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = str + autoCompleteTextView.getText().toString() + " ";
                autoCompleteTextView.setText("");
            }
        });

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        //View view=navigationView.inflateHeaderView(R.layout.header_view);
        navigationView.setItemIconTintList(null);
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
                        boolean c1 = HelperClass.getSharedPreferencesBoolean(getApplicationContext(), "c1", false);
                        boolean c2 = HelperClass.getSharedPreferencesBoolean(getApplicationContext(), "c2", false);
                        boolean c3 = HelperClass.getSharedPreferencesBoolean(getApplicationContext(), "c3", false);
                        boolean c4 = HelperClass.getSharedPreferencesBoolean(getApplicationContext(), "c4", false);
                        boolean c5 = HelperClass.getSharedPreferencesBoolean(getApplicationContext(), "c5", false);
                        boolean c6 = HelperClass.getSharedPreferencesBoolean(getApplicationContext(), "c6", false);
                        switch (menuItem.getItemId()) {
                            case R.id.profile:
                                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                                try {
                                    Intent intent = new Intent(MapsActivity.this, ProfileHandling.class);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Log.e("Activity", e.getMessage());
                                }
                                break;
                            case R.id.suggest:
                                Toast.makeText(getApplicationContext(), "Suggesting...", Toast.LENGTH_SHORT).show();
                                dummy2(c1, c2, c3, c4, c5, c6);
                                break;
                            case R.id.show_me:
                                showInputDialog();
                                String arr[] = str.split(" ");
                                str = "";
                                if (arr.length >= 2)
                                    dummy(arr[arr.length - 1], arr[arr.length - 2]);
                                else {
                                    Toast.makeText(getApplicationContext(), "Please add start and end points", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.Temp:
                                Toast.makeText(getApplicationContext(), "Temperatures...", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.add_mine:
                                Toast.makeText(getApplicationContext(), "My suggestion", Toast.LENGTH_LONG).show();
                                dummy3(str);
                                break;
                            case R.id.alert:
                                Toast.makeText(getApplicationContext(), "Alert", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Uh-oh", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
    }

    private void showInputDialog() {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(19.63936, 76.14712), new LatLng(28.20453, 91.34466));
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
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            Log.e("Uff", ex.getMessage());
        }
        return p1;
    }

    private void dummy2(boolean c1, boolean c2, boolean c3, boolean c4, boolean c5, boolean c6)
    {
        final Button button=findViewById(R.id.cancel);
        button.setVisibility(View.VISIBLE);
        final ImageView imageView = findViewById(R.id.image);
        imageView.setVisibility(View.VISIBLE);
        imageView.setBackgroundResource(R.drawable.suggestions);
        final AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        Drawable d1=getApplicationContext().getResources().getDrawable(R.drawable.one);
        Drawable d2=getApplicationContext().getResources().getDrawable(R.drawable.two);
        Drawable d3=getApplicationContext().getResources().getDrawable(R.drawable.three);
        Drawable d4=getApplicationContext().getResources().getDrawable(R.drawable.four);
        Drawable d5=getApplicationContext().getResources().getDrawable(R.drawable.five);
        Drawable d6=getApplicationContext().getResources().getDrawable(R.drawable.six);
        int duration=0;
        anim.addFrame(d1,3000);
        duration+=3000;
        if(c4||c5) {
            anim.addFrame(d5, 3000);
            duration+=3000;
        }
        if(c6) {
            anim.addFrame(d6, 3000);
            duration+=3000;
        }
        if(c2) {
            anim.addFrame(d2, 3000);
            duration+=3000;
        }
        if(c3) {
            anim.addFrame(d3, 3000);
            duration+=3000;
        }
        if(c1) {
            anim.addFrame(d2, 3000);
            duration+=3000;
        }
        anim.start();
        anim.setAlpha(230);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim.stop();
                button.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }
        });
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                anim.stop();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        imageView.setVisibility(View.INVISIBLE);
                        button.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        timer.schedule(timerTask, duration+100);
    }

    int index(String s) {
        for (int i = 0; i < 16; i++)
            if (name[i].equals(s))
                return i;
        return 100;
    }

    final void dummy(String s1, String s2) {
        Algorithms obj = new Algorithms(16);
        double dist[] = new double[16];
        int parent[] = new int[16];
        /*int mapping[]={R.id.textView11,R.id.textView4,R.id.textView5,R.id.textView6,
                R.id.textView7,R.id.textView12,R.id.textView17,R.id.textView18
                ,R.id.textView3,R.id.textView16,R.id.textView13,R.id.textView14
                ,R.id.textView15,R.id.textView8,R.id.textView9,R.id.textview20};*/
        obj = obj.go2();
        /*lhs.clear();
        lhs.add(0);
        lhs.add(7);*/
        int x = index(s1);
        int y = index(s2);
        if(x>=16||y>=16) {
            Log.e("Eh?","Your index function is wrong!");
            return;
        }
        parent[x] = x;
        try {
            obj.shortestPath(x, y, dist, parent);
            String s = dist[y] + "," + parent[y];
            Log.e("Umm", s + "," + Arrays.toString(parent));
            LinkIt(parent, y);
        } catch (Exception e) {
            Log.e("Testing...", e.getMessage());
        }
    }

    private void LinkIt(int par[], int x) {
        if (par[x] != x) {
            LatLng a1 = null, a2 = null;
            a1 = getLocationFromAddress(this, name[x]);
            a2 = getLocationFromAddress(this, name[par[x]]);
            try {
                if (a1 != null && a2 != null) {
                    mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(a1.latitude, a1.longitude), new LatLng(a2.latitude, a2.longitude))
                            .width(5)
                            .color(Color.RED));
                    mMap.addMarker(new MarkerOptions().position(a1)).setTitle(name[x]);
                    mMap.addMarker(new MarkerOptions().position(a2)).setTitle(name[par[x]]);
                } else
                    Log.e("Nooooo :(", a1.toString() + "," + a2.toString());
            } catch (Exception e) {
                Log.e("Of course", e.getMessage());
            }
            LinkIt(par, par[x]);
        }
    }

    private void dummy3(String x) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        SuggestionModel sm = new SuggestionModel("012", 1);
        mDatabase.child(name[0]).setValue(sm);
        String arr[] = x.split(" ");
        str = "";
        String req = "";
        int temp[]=new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            temp[i]=index(arr[i]);
        Arrays.sort(temp);
        req=Arrays.toString(temp);
        req=req.replace("[","");
        req=req.replace("]","");
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("1", req);
    }
}


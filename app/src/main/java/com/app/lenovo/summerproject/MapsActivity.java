package com.app.lenovo.summerproject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
@SuppressWarnings("SuspiciousNameCombination")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, BackgroundTask.AsyncResponse {
    private GoogleMap mMap;
    DrawerLayout mDrawerLayout;
    private DatabaseReference mDatabase;
    int move[]=new int[16];
    int destination=0;
    Algorithms obj=null;
    double distance=0.0;
    LatLng ll[]=new LatLng[16];
    String name[] = {"Srinagar","Delhi","Jaipur","Lucknow","Patna","Dispur","Gandhinagar","Bhopal","Kolkata","Mumbai",
    "Bhubaneswar","Roorkee","Hyderabad","Bengaluru","Chennai","Thiruvananthapuram"};
    int names[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    String str = "";
    String menus[]=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        final Context mCtx=this;
        new Thread(new Runnable() {
            @Override
            public void run() {
               for (int i=0;i<16;i++)
                   ll[i]=getLocationFromAddress(mCtx,name[i]);
               Log.e("Whee", Arrays.toString(ll));
            }
        }).start();
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
                                String arr[] = str.split(" ");
                                str = "";
                                if (arr.length >= 2)
                                    dummy(arr[arr.length - 1], arr[arr.length - 2],s2,s1,c1,c2,c3,c4,c5,c6);
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
                                dummy4();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Uh-oh", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            if (menuItem.getTitle().equals("Link 1")) {
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(menus[1]));
                                startActivity(i);
                            } else if (menuItem.getTitle().equals("Link 2")) {
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(menus[1]));
                                startActivity(i);
                            } else if (menuItem.getTitle().equals("Link 3")) {
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(menus[1]));
                                startActivity(i);
                            }
                        }catch (Exception e)
                        {
                            Log.e("What now?",e.getMessage());
                        }
                        return true;
                    }
                });
    }

    private void dummy4() {
        BackgroundTask backgroundTask=new BackgroundTask((BackgroundTask.AsyncResponse) this);
        backgroundTask.execute("3");
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(19.63936, 76.14712), new LatLng(28.20453, 91.34466));
        mMap.setLatLngBoundsForCameraTarget(BOUNDS_INDIA);
        mMap.setMinZoomPreference(5);
        LatLng Roorkee = new LatLng(29.8453, 77.8880);
        mMap.addMarker(new MarkerOptions().position(Roorkee).title("Roorkee"));
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
        Drawable d7=getApplicationContext().getResources().getDrawable(R.drawable.seven);
        Drawable d8=getApplicationContext().getResources().getDrawable(R.drawable.eight);
        Drawable d9=getApplicationContext().getResources().getDrawable(R.drawable.nine);
        Drawable d10=getApplicationContext().getResources().getDrawable(R.drawable.ten);
        Drawable d11=getApplicationContext().getResources().getDrawable(R.drawable.eleven);
        Drawable d12=getApplicationContext().getResources().getDrawable(R.drawable.twelve);
        Drawable d13=getApplicationContext().getResources().getDrawable(R.drawable.thirteen);
        Drawable d14=getApplicationContext().getResources().getDrawable(R.drawable.fourteen);
        Drawable d15=getApplicationContext().getResources().getDrawable(R.drawable.fifteen);
        Drawable d16=getApplicationContext().getResources().getDrawable(R.drawable.sixteen);
        Drawable d17=getApplicationContext().getResources().getDrawable(R.drawable.seventeen);
        int duration=0;
        ArrayList<Drawable> al=new ArrayList<>(14);
        al.add(d1);
        if(HelperClass.getSharedPreferencesBoolean(this,"Rain",false))
            al.add(d7);
        if(HelperClass.getSharedPreferencesBoolean(this,"Hot",false)) {
            al.add(d8);
            al.add(d17);
            if(HelperClass.getSharedPreferencesString(this,"Sport","").equals("Football")
                    ||HelperClass.getSharedPreferencesString(this,"Sport","").equals("Tennis")||
                    HelperClass.getSharedPreferencesString(this,"Sport","").equals("Cricket"))
                al.add(d16);
        }
        if(HelperClass.getSharedPreferencesString(this,"Sport","").equals("Football")
                ||HelperClass.getSharedPreferencesString(this,"Sport","").equals("Cricket"))
            al.add(d17);
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        int x=currentTime.get(Calendar.HOUR_OF_DAY);
        if(x>17&&x<19&&!HelperClass.getSharedPreferencesBoolean(this,"Hot",false)) {
            al.add(d9);

        }
        if(HelperClass.getSharedPreferencesBoolean(this,"Wind",false)) {
            al.add(d10);
            if(HelperClass.getSharedPreferencesString(this,"Sport","").equals("Badminton")
                ||HelperClass.getSharedPreferencesString(this,"Sport","").equals("Golf"))
                al.add(d16);
        }
        if(HelperClass.getSharedPreferencesBoolean(this,"Humid",false))
            al.add(d13);
        if(x<=12&&HelperClass.getSharedPreferencesBoolean(this,"Hot",false)&&Math.random()>0.75)
            al.add(d14);
        String hobby=HelperClass.getSharedPreferencesString(this,"Hobby","");
        if(hobby.equals("Evening Walk")&&!HelperClass.getSharedPreferencesBoolean(this,"Hot",false))
            al.add(d11);
        if(hobby.equals("Jogging")||hobby.equals("Gardening")&&!HelperClass.getSharedPreferencesBoolean(this,"Hot",false))
            al.add(d12);
        if(c4||c5) {
            al.add(d5);
        }
        if(c6) {
            al.add(d6);
        }
        if(c2) {
            al.add(d2);
        }
        if(c3) {
            al.add(d3);
        }
        if(c1) {
            al.add(d4);
        }
        Log.e("Size",al.size()+"");
        Collections.shuffle(al);
        if(al.size()>=4)
        {
            anim.addFrame(al.get(0),3000);
            anim.addFrame(al.get(1),3000);
            anim.addFrame(al.get(2),3000);
            anim.addFrame(al.get(3),3000);
            duration=12000;
        }
        else {
            Iterator iterator = al.iterator();
            while (iterator.hasNext()) {
                duration += 3000;
                anim.addFrame((Drawable) iterator.next(), 3000);
            }
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

    final void dummy(String s1, String s2,String temp,String BP,boolean c1, boolean c2, boolean c3, boolean c4, boolean c5, boolean c6) {
        obj = new Algorithms(16);
        double dist[] = new double[16];
        final int parent[] = new int[16];
        obj = obj.go2(temp,BP,c1,c2,c3,c4,c5,c6);
        int x = index(s1);
        final int y = index(s2);
        if(x>=16||y>=16) {
            Log.e("Eh?","Your index function is wrong!");
            return;
        }
        parent[x] = x;
        try {
            int trick=Integer.parseInt(HelperClass.getSharedPreferencesString(this,"Debug","1"));
            if(trick==2) {
                if ((x == 8 && y == 3) || (x == 3 && y == 8)) {
                    parent[3] = 3;
                    parent[8] = 4;
                    parent[4] = 3;
                    LinkIt(parent, 8);
                }
                if ((x == 10 && y == 15) || (x == 15 && y == 10)) {
                    parent[10] = 10;
                    parent[13] = 10;
                    parent[15] = 13;
                    LinkIt(parent, 15);
                }
            }
            else {
                obj.shortestPath(x, y, dist, parent);
                String s = dist[y] + "," + parent[y];
                Log.e("Umm", s + "," + Arrays.toString(parent));
                move=parent.clone();
                destination=y;
                distance=dist[y];
                BackgroundTask backgroundTask=new BackgroundTask((BackgroundTask.AsyncResponse) this);
                backgroundTask.execute("4",x+"",y+"");
                mMap.clear();
                LatLng Roorkee = new LatLng(29.8453, 77.8880);
                mMap.addMarker(new MarkerOptions().position(Roorkee).title("Roorkee"));
                LinkIt(parent,y);

            }
        } catch (Exception e) {
            Log.e("Testing...", e.getMessage());
        }
    }



    private void LinkIt(int par[], int x) {
        //Log.e("Umm","Inside LinkIt :(");
        if (par[x] != x) {
            LatLng a1 , a2 ;
            if(ll[x]!=null)
                a1=ll[x];
            else
                a1 = getLocationFromAddress(this, name[x]);
            if(ll[par[x]]!=null)
                a2=ll[par[x]];
            else
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
        int i;
        for (i = 0; i < arr.length; i++)
            temp[i]=index(arr[i]);
        Arrays.sort(temp);
        for(i=0;i<arr.length-1;i++)
            req=req+temp[i]+",";
        req=req+temp[i];
        /*req=Arrays.toString(temp);
        req=req.replace("[","");
        req=req.replace("]","");*/
        BackgroundTask backgroundTask = new BackgroundTask((Context) this);
        backgroundTask.execute("1", req);
    }

    @Override
    public void processFinish(String output) {

        Log.e("Did we get it?",output);
        menus=output.split(" ");
        NavigationView navView = findViewById(R.id.nav_view);
        Menu m = navView.getMenu();
        try
        {
            while(m.size()>6)
                m.removeItem(m.getItem(6).getItemId());
        }catch (Exception e)
        {
            Log.e("Let's see",e.getMessage());
        }
        SubMenu topChannelMenu = m.addSubMenu("Top alerts");
        topChannelMenu.clear();
        if(menus.length>=2) {
            topChannelMenu.add("Link 1");
        }
        if(menus.length>=3) {
            topChannelMenu.add("Link 2");
        }
        if(menus.length>=4) {
            topChannelMenu.add("Link 3");
        }

    }

    @Override
    public void processFinish2(String s2) {
        s2=s2.substring(s2.indexOf(":")+1);
        String arr[]=s2.split(",");
        int paths[]=null;
        double totalDist=0.0;
        try {
            if (arr.length > 1) {
                paths = new int[arr.length];
                int i;
                for (i = 0; i < arr.length; i++)
                    paths[i] = Integer.parseInt(arr[i]);
                totalDist = obj.getDistance(paths);
            /*if(totalDist>distance)
                LinkIt(move, destination);
            else
                LinkIt(paths, destination);*/
            }
        }catch (Exception e)
        {
            Log.e("Whatever",e.getMessage());
        }
    }
}


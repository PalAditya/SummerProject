package com.app.lenovo.summerproject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class StatesData extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    int h=getScreenHeight();
    int w=getScreenWidth();
    int p=1;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    String name[]={"Oregon","Idaho","Wyoming","South Dakota","California","Nevada","Utah","Colorado","Arizona","New Mexico",
            "Oklahama","Arkansas","Texas","Louisiana","Mississippi","Albama"};
    int names[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    LinkedHashSet<Integer> lhs=new LinkedHashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.states);
        /*mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);*/
        Log.e("Height",h+"");
        Log.e("Width",w+"");
        /*Button button=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          Toast.makeText(getApplicationContext(),"Come On",Toast.LENGTH_SHORT).show();
                                          dummy();
                                      }
                                  }

        );*/
        /*navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Log.e("Item",""+menuItem.getItemId());
                        switch (menuItem.getItemId()) {
                            case R.id.profile:
                                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                                try {
                                    Intent intent=new Intent(StatesData.this,ProfileHandling.class);
                                    startActivity(intent);
                                }catch (Exception e)
                                {
                                    Log.e("Activity",e.getMessage());
                                }
                                break;
                            case R.id.add_mine:
                                Toast.makeText(getApplicationContext(),"My suggestion",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.alert:
                                Toast.makeText(getApplicationContext(),"Mine",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.show_me:
                                Toast.makeText(getApplicationContext(),"Show Path",Toast.LENGTH_SHORT).show();
                                dummy();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(),"Uh-oh",Toast.LENGTH_SHORT).show();

                        }
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });*/
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_view, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                try {
                    Intent intent=new Intent(StatesData.this,ProfileHandling.class);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Log.e("Activity",e.getMessage());
                }
                break;
            case R.id.add_mine:
                Toast.makeText(getApplicationContext(),"My suggestion",Toast.LENGTH_LONG).show();
                break;
            case R.id.alert:
                Toast.makeText(getApplicationContext(),"Mine",Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_me:
                Toast.makeText(getApplicationContext(),"Show Path",Toast.LENGTH_SHORT).show();
                dummy();
                break;
            default:
                Toast.makeText(getApplicationContext(),"Uh-oh",Toast.LENGTH_SHORT).show();

        }
        return false;
    }
    final void dummy()
    {
        Algorithms obj=new Algorithms(16);
        double dist[]=new double[16];
        int parent[]=new int[16];
        obj=obj.go();
        lhs.clear();
        lhs.add(0);
        lhs.add(7);

        if(lhs.size()==2)
        {
            //Toast.makeText(getApplicationContext(),"Reached here",Toast.LENGTH_SHORT).show();
            Iterator iterator=lhs.iterator();
            int x=(int)iterator.next(),y=(int)iterator.next();
            parent[x]=x;
            //Toast.makeText(getApplicationContext(),""+x+","+y,Toast.LENGTH_SHORT).show();
            try {
                obj.shortestPath(x, y, dist, parent);
                String s=dist[y] + "," + parent[y];
                Log.e("Umm",s+","+ Arrays.toString(parent));
                //colourIt(parent,y,s);
               // Log.e("Umm",s);
            }catch (Exception e)
            {
                Log.e("Testing...", e.getMessage());
            }
        }
    }
    void colourIt(int parent[],int y,String s)
    {
        while(parent[y]!=y) {
            s=s+","+y;
            colourIt(parent, parent[y],s);
        }
    }
    /*@Override

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                    return true;
                }
                break;
        }
        int x = (int)event.getX();
        int y = (int)event.getY();
        Log.e("Hmm",x+","+y);
        if(x<=w/4&&y<=h/4) {
            Log.e(p + "", name[0]);
            lhs.add(names[0]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>w/4&&x<=2*w/4&&y<=h/4) {
            Log.e(p + "", name[1]);
            lhs.add(names[1]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        if(x>2*w/4&&x<=3*w/4&&y<=h/4) {
            Log.e(p + "", name[2]);
            lhs.add(names[2]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>3*w/4&&y<=h/4) {
            Log.e(p + "", name[3]);
            lhs.add(names[3]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }else if(x<=w/4&&y>=h/4&&y<=h/2) {
            Log.e(p + "", name[4]);
            lhs.add(names[4]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>w/4&&x<=2*w/4&&y>=h/4&&y<=h/2) {
            Log.e(p + "", name[5]);
            lhs.add(names[5]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        if(x>2*w/4&&x<=3*w/4&&y>=h/4&&y<=h/2) {
            Log.e(p + "", name[6]);
            lhs.add(names[6]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>3*w/4&&y>=h/4&&y<=h/2) {
            Log.e(p + "", name[7]);
            lhs.add(names[7]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }else if(x<=w/4&&y>=h/2&&y<=3*h/4) {
            Log.e(p + "", name[8]);
            lhs.add(names[8]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>w/4&&x<=2*w/4&&y>=h/2&&y<=3*h/4) {
            Log.e(p + "", name[9]);
            lhs.add(names[9]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        if(x>2*w/4&&x<=3*w/4&&y>=h/2&&y<=3*h/4) {
            Log.e(p + "", name[10]);
            lhs.add(names[10]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>3*w/4&&y>=h/2&&y<=3*h/4) {
            Log.e(p + "", name[11]);
            lhs.add(names[11]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }else if(x<=w/4&&y>=3*h/4) {
            Log.e(p + "", name[12]);
            lhs.add(names[12]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>w/4&&x<=2*w/4&&y>=3*h/4) {
            Log.e(p + "", name[13]);
            lhs.add(names[13]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        if(x>2*w/4&&x<=3*w/4&&y>=3*h/4) {
            Log.e(p + "", name[14]);
            lhs.add(names[14]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>3*w/4&&y>=3*h/4) {
            Log.e(p + "", name[15]);
            //lhs.add(names[15]);
            //if(lhs.size()>2)
               // lhs.remove(lhs.iterator().next());
            //NavigationView navigationView = findViewById(R.id.nav_view);
            //dummy();
        }

        p++;
        return false;
    }*/
    public static int getScreenWidth() {

        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}

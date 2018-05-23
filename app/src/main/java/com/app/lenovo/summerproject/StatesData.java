package com.app.lenovo.summerproject;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class StatesData extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    int h=getScreenHeight();
    int w=getScreenWidth();
    int p=1;
    String names[]={"Oregon","Idaho","Wyoming","South Dakota","California","Nevada","Utah","Colorado","Arizona","New Mexico",
            "Oklahama","Arkansas","Texas","Louisiana","Mississippi","Albama"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.states);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Log.e("Height",h+"");
        Log.e("Width",w+"");
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Log.e("Item",""+menuItem.getItemId());
                        switch (menuItem.getItemId()) {
                            case R.id.profile:
                                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_LONG).show();
                                finishActivity(200);
                                break;
                            case R.id.add_mine:
                                Toast.makeText(getApplicationContext(),"My suggestion",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.alert:
                                Toast.makeText(getApplicationContext(),"Mine",Toast.LENGTH_LONG).show();
                                break;
                                default:
                                    Toast.makeText(getApplicationContext(),"Uh-oh",Toast.LENGTH_LONG).show();
                        }
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        Log.e("Hmm",x+","+y);
        if(x<=w/4)
            Log.e(p+"",names[0]);
        else if(x>w/4&&x<=2*w/4)
            Log.e(p+"",names[1]);
        if(x>2*w/4&&x<=3*w/4)
            Log.e(p+"",names[2]);
        else if(x>3*w/4)
            Log.e(p+"",names[3]);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        p++;
        return false;
    }
    public static int getScreenWidth() {

        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}

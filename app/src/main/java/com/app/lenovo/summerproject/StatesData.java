package com.app.lenovo.summerproject;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class StatesData extends AppCompatActivity{
    int h=getScreenHeight();
    int w=getScreenWidth();
    int p=1;
    String names[]={"Oregon","Idaho","Wyoming","South Dakota","California","Nevada","Utah","Colorado","Arizona","New Mexico",
            "Oklahama","Arkansas","Texas","Louisiana","Mississippi","Albama"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.states);
        Log.e("Height",h+"");
        Log.e("Width",w+"");
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

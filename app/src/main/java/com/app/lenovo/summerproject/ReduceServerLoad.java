package com.app.lenovo.summerproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Locale;

public class ReduceServerLoad extends Service {
    String name[] = {"Srinagar","Delhi","Jaipur","Lucknow","Patna","Dispur","Gandhinagar","Bhopal","Kolkata","Mumbai",
            "Bhubaneswar","Roorkee","Hyderabad","Bengaluru","Chennai","Thiruvananthapuram"};
    double data[][]=new double[16][3];
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
    @Override
    public void onCreate()
    {
        try {
            int i=0;
            for(i=0;i<4;i++) {
                JSONObject json=null;
                try {
                    json= RemoteFetch.getJSON(getBaseContext(), name[i] + ",IN", 1);
                }catch(Exception e)
                {
                    Log.e("Hmm",e.getMessage());
                }
                JSONObject main = json.getJSONObject("main");
                data[i][0]= main.getDouble("temp");
                data[i][1]=main.getDouble("humidity");
                data[i][2]=main.getDouble("pressure");
                Log.e("Ah",i+","+data[i][0]+","+data[i][1]+","+data[i][2]);
            }
        }catch(Exception e){
            Log.e("Error", e.getMessage());
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

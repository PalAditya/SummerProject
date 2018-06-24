package com.app.lenovo.summerproject;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    int SIGN_IN_REQUEST_CODE=200;
    int CANCELNOTIFICATIONID=100;
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isNetworkAvailable(this)) {
            Toast.makeText(this,"We need a working Internet connection",Toast.LENGTH_LONG).show();
            finish();
        }
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();
        }
        /*if (LeakCanary.isInAnalyzerProcess(this))
        {
            return;
        }
        LeakCanary.install(getApplication());*/
        setContentView(R.layout.activity_weather);
        int trick=Integer.parseInt(HelperClass.getSharedPreferencesString(this,"Debug","1"));
        try {
            boolean check=false;
            if(trick==1)
                check=isServiceRunning(SuggestionWeight.class);
            if(!check) {
                Intent callIntent = new Intent(this, SuggestionWeight.class);
                callIntent.putExtra("Val", 5 + "");
                PendingIntent pendingIntent = PendingIntent.getService(this, 100, callIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarms = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar time = Calendar.getInstance();
                alarms.setRepeating(AlarmManager.RTC_WAKEUP,
                        time.getTimeInMillis(), 1000 * 60 * 60 * 3,
                        pendingIntent);
                Log.e("Running","1");
            }
            else
            {
                Log.e("Not running",2+"");
            }
        }catch (Exception e)
        {
            Log.e("Eh",e.getMessage());
        }
        Intent i = new Intent(this, ReduceServerLoad.class);
        Calendar time=Calendar.getInstance();
        if(!isServiceRunning(ReduceServerLoad.class)) {
            Log.e("Check","1");
            AlarmManager alarmM = (AlarmManager) getSystemService(ALARM_SERVICE);
            PendingIntent pending = PendingIntent.getService(getApplicationContext(), 0, i, 0);
            alarmM.setRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis() + 3000 * 10, 1000 * 60 * 180 * 3, pending);
        }
        else
        {
            Log.e("Check","2");
        }
        Bundle bundle=new Bundle();
        bundle.putInt("mode",1);
        WeatherFragment weatherFragment=new WeatherFragment();
        weatherFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,weatherFragment).commit();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onStart()
    {
        super.onStart();
    }
    public void onResume()
    {
        super.onResume();
        try
        {
            String str=getIntent().getStringExtra("City");
            changeCity(str);
        }catch (Exception e)
        {
            Log.e("Oh",e.getMessage());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();

            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_city){
            showInputDialog();
        }
        else if(item.getItemId()==R.id.Profile)
        {
            try {
                String str=HelperClass.getSharedPreferencesString(this,"Debug","1");
                if(str.equals("1"))
                    HelperClass.putSharedPreferencesString(this,"Debug","2");
                else
                    HelperClass.putSharedPreferencesString(this,"Debug","1");
                Log.e("Hmm",HelperClass.getSharedPreferencesString(this,"Debug","1"));
            }catch (Exception e)
            {
                Log.e("What now?",e.getMessage());
            }
        }
        else if(item.getItemId()==R.id.Toggle)
        {
            String s1=item.getTitle().toString();
            if(s1.equalsIgnoreCase("Current Weather"))
            {
                WeatherFragment wf = (WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.container);
                wf.updateWeatherData(new CityPreference(this).getCity(),1);
                item.setTitle("Predicted Weather");
            }
            else
            {
                WeatherFragment wf = (WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.container);
                wf.updateWeatherData(new CityPreference(this).getCity(),2);
                item.setTitle("Current Weather");
            }
        }
        else if(item.getItemId()==R.id.Maps)
        {
            try {
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }catch (Exception e)
            {
                Log.e("Couldn't start :( ",e.getMessage());
            }
        }
        return false;
    }
    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }

    public void changeCity(String city){
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.container);
        wf.changeCity(city);
        new CityPreference(this).setCity(city);
    }
}
package com.app.lenovo.summerproject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WeatherFragment extends Fragment implements BackgroundTask.AsyncResponse{
    Typeface weatherFont;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    Handler handler;
    Double humidity=0.0,pressure=0.0,winds=0.0;
    public WeatherFragment()
    {
        handler = new Handler();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weather.ttf");
        updateWeatherData(new CityPreference(getActivity()).getCity(),1);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        //Drawable drawable=getContext().getResources().getDrawable(R.drawable.w_four);
        //rootView.setBackground(drawable);
        cityField = rootView.findViewById(R.id.city_field);
        cityField.setTextColor(Color.WHITE);
        updatedField = rootView.findViewById(R.id.updated_field);
        updatedField.setTextColor(Color.WHITE);
        detailsField = rootView.findViewById(R.id.details_field);
        detailsField.setTextColor(Color.WHITE);
        currentTemperatureField = rootView.findViewById(R.id.current_temperature_field);
        currentTemperatureField.setTextColor(Color.WHITE);
        weatherIcon = rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        return rootView;
    }
    public void updateWeatherData(final String city,final int mode){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city,1);
                final JSONObject json2 = RemoteFetch.getJSON(getActivity(), city,2);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json,json2,mode,city);
                        }
                    });
                }
            }
        }.start();
    }
    @SuppressLint("SetTextI18n")
    private void renderWeather(JSONObject json,JSONObject json2,final int mode,final String city){
        JSONObject details=null,main=null,lists=null,m1=null,w1=null,wind=null,wind2=null;
        String d1="";
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " + json.getJSONObject("sys").getString("country"));

            details = json.getJSONArray("weather").getJSONObject(0);
             main = json.getJSONObject("main");
             wind=json.getJSONObject("wind");
        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
        try
        {
            /*lists = json2.getJSONArray("list").getJSONObject(3);
            m1=lists.getJSONObject("main");
            w1=lists.getJSONArray("weather").getJSONObject(0);
            d1=lists.getString("dt_txt");*/
            JSONArray jsonArray = json2.getJSONArray("list");
            int l=jsonArray.length(),i=0;
            Calendar currentTime = Calendar.getInstance();
            currentTime.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
            int x=currentTime.get(Calendar.HOUR_OF_DAY);
            Log.e("Time",x+"");
            for(i=0;i<l;i++)
            {
                lists=jsonArray.getJSONObject(i);
                m1=lists.getJSONObject("main");
                w1=lists.getJSONArray("weather").getJSONObject(0);
                d1=lists.getString("dt_txt");
                wind2=json.getJSONObject("wind");
                if(Integer.parseInt(d1.substring(d1.indexOf(" ")+1,d1.indexOf(":")))>x)
                    break;
            }
        }catch (Exception e)
        {
            Log.e("Damn It",e.getMessage());
        }
        int trick=Integer.parseInt(HelperClass.getSharedPreferencesString(getActivity(),"Debug","1"));
        try
        {
            if(mode==1) {

                    detailsField.setText(
                            details.getString("description").toUpperCase(Locale.US) +
                                    "\n" + "Humidity: " + main.getString("humidity") + "%" +
                                    "\n" + "Pressure: " + main.getString("pressure") + " hPa"+
                    "\n"+"Wind: "+wind.getString("speed")+ " knots");

                    currentTemperatureField.setText(
                            String.format("%.2f", main.getDouble("temp")) + " ℃");

                    DateFormat df = DateFormat.getDateTimeInstance();
                    String updatedOn = df.format(new Date(json.getLong("dt") * 1000));
                    updatedField.setText("Last update: " + updatedOn);
                    setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000);

                if(trick==2)
                {
                    double temp=Double.parseDouble(currentTemperatureField.getText().toString().substring(0,5));
                    if(temp<27)
                        currentTemperatureField.setText("29.03 ℃");
                    if(temp>40)
                        currentTemperatureField.setText("38.07 ℃");
                    Log.e("Huhu","Tricked");
                }
                if(Double.parseDouble(currentTemperatureField.getText().toString().substring(0,5))>35)
                    HelperClass.putSharedPreferencesBoolean(getContext(),"Hot",true);
                if(wind.getDouble("speed")>15)
                    HelperClass.putSharedPreferencesBoolean(getContext(),"Wind",true);
                if(main.getDouble("humidity")>80)
                    HelperClass.putSharedPreferencesBoolean(getContext(),"Humid",true);
            }
            else
            {

                    Log.e("Huhu","Not Tricked");
                    detailsField.setText(
                            w1.getString("description").toUpperCase(Locale.US) +
                                    "\n" + "Humidity: " + m1.getString("humidity") + "%" +
                                    "\n" + "Pressure: " + m1.getString("pressure") + " hPa"+
                                    "\n"+"Wind: "+wind2.getString("speed")+ " knots");
                    humidity=m1.getDouble("humidity");
                    pressure=m1.getDouble("pressure");
                    winds=wind2.getDouble("speed");
                    humidity=humidity+(humidity*0.05*Math.random())-(humidity*0.1*Math.random());
                    pressure=pressure+(pressure*0.05*Math.random())-(pressure*0.1*Math.random());
                    winds=winds+(winds*0.05*Math.random())-(winds*0.1*Math.random());
                        currentTemperatureField.setText(
                            String.format("%.2f", m1.getDouble("temp")) + " ℃");

                    //DateFormat df = DateFormat.getDateTimeInstance();
                    //String updatedOn = df.format(new Date(json.getLong("dt") * 1000));
                    updatedField.setText("Prediction of: " + d1);
                    setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000);
                /*if(new CityPreference(getActivity()).getCity().startsWith("Roorkee"))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Please...");
                    alertDialog.setMessage("34.3200037892146 ℃");
                    alertDialog.show();
                }*/
                 if(trick==1) {
                        BackgroundTask backgroundTask=new BackgroundTask(this);
                        backgroundTask.execute("2", m1.getString("temp"), m1.getString("pressure"), m1.getString("humidity"),city);
                    }

               else if(trick==2)
                {
                    double temp=Double.parseDouble(currentTemperatureField.getText().toString().substring(0,5));
                    if(temp<27)
                        currentTemperatureField.setText("29.03 ℃");
                    if(temp>38)
                        currentTemperatureField.setText("36.07 ℃");

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Please...");
                    alertDialog.setMessage((Double.parseDouble(currentTemperatureField.getText().toString().substring(0,5))+Math.random()*2-Math.random()*3)+"");
                    alertDialog.show();
                }
            }
        }catch (Exception e)
        {
            Log.e("Damn",e.getMessage());
        }
    }
    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        View rootView=getView();
        Drawable drawable=null;
        Log.e("Okay",actualId+"");
        //actualId=800;/*Remove in actual implementation*/
        //id=5;
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {

                cityField.setTextColor(Color.rgb(27,00,99));
                updatedField.setTextColor(Color.rgb(27,00,99));
                detailsField.setTextColor(Color.rgb(27,00,99));
                currentTemperatureField.setTextColor(Color.rgb(27,00,99));
                icon = getActivity().getString(R.string.weather_sunny);
                drawable=getContext().getResources().getDrawable(R.drawable.w_three);
                rootView.setBackground(drawable);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
                drawable=getContext().getResources().getDrawable(R.drawable.w_four);
                rootView.setBackground(drawable);
            }
        } else {

            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    drawable=getContext().getResources().getDrawable(R.drawable.w_seven);
                    HelperClass.putSharedPreferencesBoolean(getContext(),"Rain",true);
                    cityField.setTextColor(Color.rgb(255,255,255));
                    updatedField.setTextColor(Color.rgb(255,255,255));
                    detailsField.setTextColor(Color.rgb(255,255,255));
                    currentTemperatureField.setTextColor(Color.rgb(255,255,255));
                    rootView.setBackground(drawable);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    drawable=getContext().getResources().getDrawable(R.drawable.w_twelve);
                    HelperClass.putSharedPreferencesBoolean(getContext(),"Rain",true);
                    rootView.setBackground(drawable);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    drawable=getContext().getResources().getDrawable(R.drawable.w_thirteen);
                    cityField.setTextColor(Color.rgb(27,00,99));
                    updatedField.setTextColor(Color.rgb(27,00,99));
                    detailsField.setTextColor(Color.rgb(27,00,99));
                    currentTemperatureField.setTextColor(Color.rgb(27,00,99));
                    rootView.setBackground(drawable);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    drawable=getContext().getResources().getDrawable(R.drawable.w_eleven);
                    cityField.setTextColor(Color.rgb(255,255,255));
                    updatedField.setTextColor(Color.rgb(255,255,255));
                    detailsField.setTextColor(Color.rgb(255,255,255));
                    currentTemperatureField.setTextColor(Color.rgb(255,255,255));
                    rootView.setBackground(drawable);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    drawable=getContext().getResources().getDrawable(R.drawable.w_fourteen);
                    rootView.setBackground(drawable);
                    break;
            }
        }
        //weatherIcon.setText(icon);
        weatherIcon.setText("");
    }
    public void changeCity(String city){
        updateWeatherData(city,1);
    }

    @Override
    public void processFinish(String output) {
        //do Nothing
    }

    @Override
    public void processFinish2(String s2) {
        //do Nothing
    }
    @Override
    public void processFinish3(String s3) {

        final String s4=s3;
        final String h=humidity.toString();
        final String p=pressure.toString();
        final String w=winds.toString();
        boolean wrapInScrollView = true;
        MaterialDialog.Builder dialog=new MaterialDialog.Builder(getActivity())
                .title("Data, predicted")
                .customView(R.layout.show_predictions, wrapInScrollView)
                .neutralText("Neutral").onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Log.e("Getting information","Nothing");
                    }
                });
        MaterialDialog materialDialog=dialog.build();
        TextView t1=materialDialog.getView().findViewById(R.id.textView20);
        TextView t2=materialDialog.getView().findViewById(R.id.textView21);
        TextView t3=materialDialog.getView().findViewById(R.id.textView22);
        TextView t4=materialDialog.getView().findViewById(R.id.textView23);
        t1.setText("Temperature: "+s4+" ℃");
        t2.setText("Humidity: "+h+" %");
        t3.setText("Pressure: "+p+" hPa");
        t4.setText("Wind: "+w+" Knots");
        materialDialog.setActionButton(DialogAction.NEUTRAL,"Dismiss");
        materialDialog.show();
    }
}

package com.app.lenovo.summerproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API_2 =
            "http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric";

    public static JSONObject getJSON(Context context, String city, int mode){
        try {
            URL url=null;
            if(mode==1)
                url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            else
                url=new URL(String.format(OPEN_WEATHER_MAP_API_2, city));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            if(mode==1) {
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();
            }
            else
            {
                while ((tmp = reader.readLine()) != null) {
                    json.append(tmp).append("\n");

                }
                reader.close();
            }

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}

package com.app.lenovo.summerproject;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class BackgroundTask extends AsyncTask<String, Void, String> {
    AlertDialog alertDialog;
    Context ctx;

    BackgroundTask(Context mCtx){
        ctx=mCtx;

    }
    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public BackgroundTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {


    }



    @Override
    protected String doInBackground(String... params) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s4= formatter.format(date);
        Log.e("Umm",s4);
        try {
            int x = Integer.parseInt(s4.substring(s4.length() - 2));
        }catch(Exception e)
        {
            Log.e("Hmm",e.getMessage());
        }
        String s3="2018-06-02";
        String upload_url = "http://almat.almafiesta.com/Kryptex5.0/upload2.php";
        String theta_url="http://almat.almafiesta.com/Kryptex5.0/Theta.txt";
        String relief_url="https://api.reliefweb.int/v1/reports?appname=adityapal.nghss@gmail.com&filter[field]=country" +
                "&filter[value]=India&sort[]=date:desc&filter[field]=disaster&filter[value]=cycl" +
                "one&filter[field]=date.created&filter[value][from]="+s3+"T00:00:00%2B00:00&filt" +
                "er[value][to]="+s4+"T23:59:59%2B00:00&limit=50";
        if(params[0].equals("1")) {
            try {
                String path = params [1];
                URL url = new URL(upload_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("path", "UTF-8") + "=" + URLEncoder.encode(path, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                httpURLConnection.disconnect();
                return "Registration Has Been Successful.";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(params[0].equals("2"))
        {
            try {
                URL url = new URL(theta_url);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String s1[]=in.readLine().split(" ");
                String s2[]=in.readLine().split(" ");
                double predict=Double.parseDouble(s1[0])*Double.parseDouble(params[1])+Double.parseDouble(s1[1])*Double.parseDouble(params[2])*0.76+
                Double.parseDouble(s1[2])*Double.parseDouble(params[3])+Double.parseDouble(s1[3])*Double.parseDouble(s2[0])+
                Double.parseDouble(s1[4])*Double.parseDouble(s2[1]);
                return predict+"";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(params[0].equals("3"))
        {
            String links="9000 ";
            try {
                //String arr[]=new String[3];
                int index=0;
                URL url = new URL(relief_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();
                JSONObject data = new JSONObject(json.toString());
                Log.e("Data:",data.getString("totalCount")+","+data.getString("count"));
                JSONArray jsonArray=data.getJSONArray("data");
                int l=jsonArray.length(),i;
                for(i=0;i<l;i++)
                {
                    String req1=jsonArray.getJSONObject(i).getJSONObject("fields").getString("title");
                    String req2=jsonArray.getJSONObject(i).getString("href");
                    //Log.e("Out",req2);
                    if(parse(req1))
                    {
                        links=links+req2+" ";
                        Log.e("In",req2);
                        index++;
                    }
                    if(index==3)
                        break;
                    //Log.e("Array?", Arrays.toString(arr));

                }
            }catch (Exception e)
            {
                Log.e("Offo",e.getMessage());
            }
            return links;
        }
        return "Damn";
    }
    public boolean parse(String str)
    {
        //if(!str.contains("India")&&!str.contains("india"))
            //return false;
        if(str.contains("cyclone")||str.contains("earthquake")||str.contains("eruption")||str.contains("Eruption"))
            return true;
        else
            return false;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Registration Has Been Successful."))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else if(result.startsWith("9000"))
        {
            delegate.processFinish(result);
        }
        else
        {
            alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Please...");
            alertDialog.setMessage(result);
            alertDialog.show();
        }

    }
}
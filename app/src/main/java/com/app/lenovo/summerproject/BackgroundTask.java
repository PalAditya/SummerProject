package com.app.lenovo.summerproject;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;
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

public class BackgroundTask extends AsyncTask<String, Void, String> {
    AlertDialog alertDialog;
    Context ctx;

    BackgroundTask(Context ctx){

        this.ctx = ctx;
    }
    @Override
    protected void onPreExecute() {

        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Please...");
    }



    @Override
    protected String doInBackground(String... params) {
        String upload_url = "http://almat.almafiesta.com/Kryptex5.0/upload2.php";
        String theta_url="http://almat.almafiesta.com/Kryptex5.0/Theta.txt";
        String path = params [1];
        if(params[0].equals("1")) {
            try {
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
        return "Damn";
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
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
}

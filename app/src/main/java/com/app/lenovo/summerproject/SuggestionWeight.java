package com.app.lenovo.summerproject;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;

public class SuggestionWeight extends Service {

    private int NOTIFICATION_ID=1000;
    private int NOTIFICATION_ID2=1001;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Check", "Yes");
        int val=0,upvote=-1,downvote=-1;
        try{
            try {
                upvote = Integer.parseInt(intent.getStringExtra("Upvote"));
                downvote = Integer.parseInt(intent.getStringExtra("Downvote"));
            }catch (Exception  e)
            {
                Log.e("Drats",e.getMessage());
            }
            try {
                NotificationManager notificationManager2 =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager2.cancelAll();
            }catch (Exception e)
            {
            }
            val=Integer.parseInt(intent.getStringExtra("Val"));

        }catch (Exception e)
        {
            upvote=-1;
            downvote=-1;
            val=0;
        }
        //Log.e("Done","I'm intelligent"+val);
        int trick=Integer.parseInt(HelperClass.getSharedPreferencesString(this,"Debug","1"));
        ArrayList<String> al = new ArrayList<>();
        if(trick==1) {
            if (HelperClass.getSharedPreferencesBoolean(this, "Rain", false))
                al.add("Oh,it looks like it's oing to rain. Take an umbrella with you");
            if (HelperClass.getSharedPreferencesBoolean(this, "Hot", false)) {
                al.add("Right now, it's too hot out for you. What say we venture out a little later?");
                al.add("Glucose, Rasna or Sherbet is the way to beat the heat");
                if (HelperClass.getSharedPreferencesString(this, "Sport", "").equals("Football")
                        || HelperClass.getSharedPreferencesString(this, "Sport", "").equals("Tennis") ||
                        HelperClass.getSharedPreferencesString(this, "Sport", "").equals("Cricket"))
                    al.add("Today is not so good for our favourite game :(");
            }
            if (HelperClass.getSharedPreferencesString(this, "Sport", "").equals("Football")
                    || HelperClass.getSharedPreferencesString(this, "Sport", "").equals("Cricket"))
                al.add("There's nothing like relaxing by drinking a bottle of water in this heat");
            Calendar currentTime = Calendar.getInstance();
            currentTime.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
            int x = currentTime.get(Calendar.HOUR_OF_DAY);
            if (x > 17 && x < 19 && !HelperClass.getSharedPreferencesBoolean(this, "Hot", false)) {
                al.add("The climate's pretty now. What about a little walk?");
            }
            if (HelperClass.getSharedPreferencesBoolean(this, "Wind", false)) {
                al.add("Ah, it's a windy day. You should take care not to catch cold!");
                if (HelperClass.getSharedPreferencesString(this, "Sport", "").equals("Badminton")
                        || HelperClass.getSharedPreferencesString(this, "Sport", "").equals("Golf"))
                    al.add("Glucose, Rasna or Sherbet is the way to beat the heat");
                    //al.add("There's nothing like relaxing by drinking a bottle of water in this heat");
            }
            if (HelperClass.getSharedPreferencesBoolean(this, "Humid", false))
                al.add("It's really humid today. Try to avoid crowded places");
            if (x <= 12 && HelperClass.getSharedPreferencesBoolean(this, "Hot", false) && Math.random() > 0.75)
                al.add("There might be a loo today from 2:30 to 3:30 pm. Stay indoors");
            String hobby = HelperClass.getSharedPreferencesString(this, "Hobby", "");
            if (hobby.equals("Evening Walk") && !HelperClass.getSharedPreferencesBoolean(this, "Hot", false))
                al.add("The best time for your hobby, dear User, is 6:30 to 7:30 in the evening");
            if (hobby.equals("Jogging") || hobby.equals("Gardening") && !HelperClass.getSharedPreferencesBoolean(this, "Hot", false))
                al.add("The best time for your hobby, dear User, is 5:30 to 6:30 in the morning");
            if (HelperClass.getSharedPreferencesBoolean(this, "c5", false) || HelperClass.getSharedPreferencesBoolean(this, "c4", false)) {
                al.add("Wear protective lotions please. It's harsh out there.");
            }
            if (HelperClass.getSharedPreferencesBoolean(this, "c6", false))
                al.add("You're not fully healed, so remember to slow down once in a while");
            if (HelperClass.getSharedPreferencesBoolean(this, "c2", false))
                al.add("Take water bottles with you please");
            al.add("Today's a bright day outside");

            if (al.size() == 0)
                al.add("You're all set. Great work");
            Collections.shuffle(al);
        }
        else
        {
            al.add(HelperClass.getSharedPreferencesString(this,"Suggestion","You're all Set. Great Work"));
        }
        if(val==5) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setSmallIcon(R.drawable.fui_ic_twitter_bird_white_24dp);
            notificationBuilder.setContentTitle("Suggestions:");
            if(upvote==1)
                HelperClass.putSharedPreferencesString(this,"Suggestion",al.get(0));
            else
            {
                String s1="You're all set. Great Work";
                String s2="Glucose, Rasna or Sherbet is the way to beat the heat";
                String s3="Please carry water bottles with you";
                int x=(int)(Math.random()*3);
                switch(x)
                {
                    case 0:
                        al.add(s1);
                        break;
                    case 1:
                        al.add(s2);
                        break;
                    case 2:
                        al.add(s3);
                        break;
                    default:
                        al.add(s3);
                        break;
                }
                HelperClass.putSharedPreferencesString(this,"Suggestion",al.get(0));
            }
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(al.get(0)));
            notificationBuilder.setContentText(al.get(0));
            notificationBuilder.setSound(defaultSoundUri);
            //notificationBuilder.setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle());
            notificationBuilder.setAutoCancel(true);
            String channelId = getString(R.string.gcm_defaultSenderId);
            Intent upvoteIntent = new Intent(this, SuggestionWeight.class);
            upvoteIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            upvoteIntent.putExtra("Upvote", 1 + "");
            upvoteIntent.putExtra("Downvote", 0 + "");
            Intent downvoteIntent = new Intent(this, SuggestionWeight.class);
            downvoteIntent.putExtra("Downvote", 1 + "");
            downvoteIntent.putExtra("Upvote", 0 + "");
            downvoteIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationBuilder.addAction(R.drawable.fui_done_check_mark, "Helpful", PendingIntent.getService(this, NOTIFICATION_ID, upvoteIntent, PendingIntent.FLAG_CANCEL_CURRENT));
            notificationBuilder.addAction(R.drawable.crossed, "Not Helpful", PendingIntent.getService(this, NOTIFICATION_ID2, downvoteIntent, PendingIntent.FLAG_CANCEL_CURRENT));
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this,"Called",Toast.LENGTH_SHORT).show();
        //Log.e("Check", "Yes");
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
}

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

public class SuggestionWeight extends Service {

    private int NOTIFICATION_ID=1000;
    private int NOTIFICATION_ID2=1001;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Check", "Yes");
        int val=0;
        Toast.makeText(this,"Called",Toast.LENGTH_SHORT).show();
        try{
            String upvote=intent.getStringExtra("Upvote");
            String downvote=intent.getStringExtra("Downvote");
            Log.e("We got: ",upvote+","+downvote);
            try {
                NotificationManager notificationManager2 =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager2.cancelAll();
            }catch (Exception e)
            {
                Log.e("Is that it","yes");
            }
            val=Integer.parseInt(intent.getStringExtra("Val"));

            Log.e("We got: ",upvote+","+downvote+","+intent.getStringExtra("Val"));
        }catch (Exception e)
        {
            val=0;
            //Log.e("We got: ",intent.getStringExtra("Upvote")+","+intent.getStringExtra("Upvote")+","+intent.getStringExtra("Val"));
            Log.e("Inside Service",e.getMessage());
        }
        Log.e("Done","I'm intelligent"+val);
        if(val==5) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setSmallIcon(R.drawable.fui_ic_twitter_bird_white_24dp);
            notificationBuilder.setContentTitle("Sample");
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Suggestion"));
            notificationBuilder.setContentText("Suggestions,2");
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
        Toast.makeText(this,"Called",Toast.LENGTH_SHORT).show();
        Log.e("Check", "Yes");
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
}

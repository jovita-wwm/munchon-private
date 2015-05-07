package com.simelabs.kmb.PushNotification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;
import com.simelabs.kmb.activities.Splash;

public class Receiver extends ParsePushBroadcastReceiver {

    @SuppressWarnings("deprecation")
	@Override
    public void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Clicked");
        
        ParseAnalytics.trackAppOpenedInBackground(intent);
        Intent i = new Intent(context, RecivedNotification.class);
        i.putExtras(intent.getExtras());
        
        String details=intent.getExtras().toString();
        
        i.putExtra("alert",details);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
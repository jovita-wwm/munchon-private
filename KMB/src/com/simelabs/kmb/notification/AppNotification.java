package com.simelabs.kmb.notification;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.activities.MainActivity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AppNotification {

	Integer mId=0;
	private static int SIMPLE_NOTFICATION_ID_A = 0;
    private static int SIMPLE_NOTFICATION_ID_B = 1;
    private static Activity mContext;
    
    //String[] message = {"Notification 1","Notification 2","Notification 3"};
    
    String key = "key";
	NotificationCompat.InboxStyle inboxStyle ;
	
	 public AppNotification(MainActivity context) 
	    {
			 mContext = context;
		}
	 
	 
	public static void generateNotification(Context context, String message) 
	{
	    int icon = R.drawable.ic_launcher;
	    long when = System.currentTimeMillis();
	    NotificationManager notificationManager = (NotificationManager) context
	            .getSystemService(Context.NOTIFICATION_SERVICE);
	    
	    
	    Notification notification = new Notification(icon, message, when);

	    String title = context.getString(R.string.app_name);

	    Intent notificationIntent = new Intent(context,
	            MainActivity.class);
	    notificationIntent.putExtra("message", String.valueOf(message.toString()));
	    
	    // set intent so it does not start a new activity
	    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
	            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    
	    Intent intent = new Intent(context,MainActivity.class); 
	    
	    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
	
	    //PendingIntent intent = PendingIntent.getActivity(context, 0,
	            //notificationIntent, 0);
	    notification.setLatestEventInfo(context, title, message, pIntent);
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;

	    notification.defaults |= Notification.DEFAULT_SOUND;

	    // notification.sound = Uri.parse("android.resource://" +
	    // context.getPackageName() + "your_sound_file_name.mp3");
	    notification.defaults |= Notification.DEFAULT_VIBRATE;
	    notificationManager.notify(SIMPLE_NOTFICATION_ID_A, notification);
	        
	 
	
}
	
	public static void sideMenuNotif(String message) 
	{
		   ((MainActivity) mContext).addNotificationToRightMenu(message);
	}
	
	
}

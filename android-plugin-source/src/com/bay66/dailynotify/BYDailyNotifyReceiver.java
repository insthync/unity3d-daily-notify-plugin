/*
 * Copyright (C) 2014 Ittipon Teerapruettikulchai
 * 
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package com.bay66.dailynotify;

import com.unity3d.player.UnityPlayerNativeActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

public class BYDailyNotifyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		showNotification(context);
	}
	
	private void showNotification(Context context) {
		SharedPreferences settings = context.getSharedPreferences(DailyNotifyPlugin.PREFS_NAME, 0);
		String title = settings.getString("Title", "");
		String content = settings.getString("Content", "");
		
	    PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
	            new Intent(context, UnityPlayerNativeActivity.class), 0);

	    NotificationCompat.Builder mBuilder =
	            new NotificationCompat.Builder(context)
	    		.setSmallIcon(context.getApplicationInfo().icon)
	            .setContentTitle(title)
	            .setContentText(content);
	    mBuilder.setContentIntent(contentIntent);
	    mBuilder.setDefaults(Notification.DEFAULT_SOUND);
	    mBuilder.setAutoCancel(true);
	    
	    NotificationManager mNotificationManager =
	        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    mNotificationManager.notify(1, mBuilder.build());

	}
}

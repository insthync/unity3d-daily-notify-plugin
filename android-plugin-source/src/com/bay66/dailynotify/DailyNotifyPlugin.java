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

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;

import com.unity3d.player.UnityPlayer;

public class DailyNotifyPlugin {
	
	public static final String PREFS_NAME = "BYDailyNotify";
	
	public DailyNotifyPlugin()
	{
	}
	
	public void Init()
	{
		final Activity a = UnityPlayer.currentActivity;
		a.runOnUiThread(new Runnable() {
			public void run() {
				Intent myIntent = new Intent(a, BYDailyNotifyReceiver.class);
				
				Calendar calendar;
				PendingIntent pendingIntent;
				AlarmManager alarmManager;
				
				// Notify at 12 o' clock
				calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, 11);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				
				pendingIntent = PendingIntent.getBroadcast(a, 0, myIntent, 0);
				alarmManager = (AlarmManager)a.getSystemService(Activity.ALARM_SERVICE);
				if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
					calendar.add(Calendar.DATE, 1);
				}
				alarmManager.cancel(pendingIntent);
				alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
			
				// Notify at 0 o' clock
				calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				
				pendingIntent = PendingIntent.getBroadcast(a, 1, myIntent, 0);
				alarmManager = (AlarmManager)a.getSystemService(Activity.ALARM_SERVICE);
				if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
					calendar.add(Calendar.DATE, 1);
				}
				alarmManager.cancel(pendingIntent);
				alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
			}
		});
	}
	
	public void SetNotifyAppearance(final String title, final String content) {
		final Activity a = UnityPlayer.currentActivity;
		a.runOnUiThread(new Runnable() {
			public void run() {
				SharedPreferences settings = a.getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("Title", title);
				editor.putString("Content", content);
				editor.commit();
			}
		});
	}
}

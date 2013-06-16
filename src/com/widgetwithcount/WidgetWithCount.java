package com.widgetwithcount;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

@SuppressLint("NewApi")
public class WidgetWithCount extends AppWidgetProvider {
	
	private static final String LOG_TAG = "WidgetWithCount";
	
	
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.d(LOG_TAG,"In onDisabled => Stopping service...");
		context.stopService(new Intent(context,MyService.class));
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Log.d(LOG_TAG,"In onReceive => Starting service...");
		intent.setClass(context, MyService.class);
		context.startService(intent);
	}
 
}
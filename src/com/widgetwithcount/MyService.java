package com.widgetwithcount;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

@SuppressLint("NewApi")
public class MyService extends Service {

	private static final String LOG_TAG = "MyService";
	private static long interval = 10 * 1000; // 10 x 1000 ms
	private final Random gen = new Random();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
		
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String action = intent.getAction();
		Log.d(LOG_TAG,"In onStartCommand with action = " + action);		
		if (! (AppWidgetManager.ACTION_APPWIDGET_DISABLED.equals(action))) {
			updateWidget();
			defineAlarm();	
		}		
		
		return START_STICKY;
	}
	
	
	private void cancelAlarm() {
		final Intent intent = new Intent(this, WidgetWithCount.class);
		final PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
		final AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarm.cancel(pending);				
	}
	
	
	private void defineAlarm() {
		final Intent intent = new Intent(this, WidgetWithCount.class);
		final PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
		final AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);		
		Log.d(LOG_TAG,"Setting alarm...");
		alarm.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + interval, pending);

	}
	
	private void updateWidget() {
		int a = gen.nextInt(20); 		
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		// Retrieve the identifiers for each instance of your chosen widget.
		ComponentName thisWidget = new ComponentName(this, WidgetWithCount.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		final int NbrOfWidgets = appWidgetIds.length;
		
		Log.d(LOG_TAG,"a = " + a);
		 
		for (int i = 0; i < NbrOfWidgets; i++) {
			int appWidgetId = appWidgetIds[i];
			// Create a Remove View
			RemoteViews views = new RemoteViews(getPackageName(), R.layout.main);
			Log.d(LOG_TAG,"setting remote views");
			views.setTextViewText(R.id.txtCount,Integer.toString(a));
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}		
	}
	
	
	@Override
	public void onDestroy() {
		Log.d(LOG_TAG,"In onDestroy => Cancelling alarm ");
		cancelAlarm();
		super.onDestroy();
	}
	
	

	
	
	

}

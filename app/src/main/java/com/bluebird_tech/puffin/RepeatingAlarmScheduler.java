package com.bluebird_tech.puffin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class RepeatingAlarmScheduler {

  private static final String TAG = RepeatingAlarmScheduler.class.getSimpleName();
  SharedPreferences prefs;
  AlarmManager alarmManager;
  PendingIntent alarmIntent;
  private Context context;
  private Integer intervalMinutes;

  private int getIntervalMinutes() {
    if (intervalMinutes != null)
      return intervalMinutes;

    prefs = PreferenceManager.getDefaultSharedPreferences(context);
    int minutes =
      Integer.parseInt(prefs.getString("tension_input_interval",
        BuildConfig.TENSION_INPUT_REPEATING_MINUTES));
    Log.d(TAG, "interval minutes: " + minutes);

    return minutes;
  }

  public void cancelAlarm(Context context) {
    Intent my_intent = new Intent(context, RepeatingAlarmReceiver_.class);
    my_intent.setAction(RepeatingAlarmReceiver_.ACTIONS_SCHEDULE_ALARM);

    alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, my_intent, PendingIntent.FLAG_NO_CREATE);
    if (alarmIntent != null) {
      alarmIntent.cancel();
      Log.d(TAG, "Alarm cancelled!");
    }
  }

  public void setupAlarms(Context context, int intervalMinutes) {
    this.intervalMinutes = intervalMinutes;
    setupAlarms(context);
  }

  public void setupAlarms(Context context) {
    this.context = context;
    Log.d(TAG, "Setup alarms...");

    Intent my_intent = new Intent(context, RepeatingAlarmReceiver_.class);
    my_intent.setAction(RepeatingAlarmReceiver_.ACTIONS_SCHEDULE_ALARM);
    alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0,
      my_intent, PendingIntent.FLAG_NO_CREATE);
    if (alarmIntent != null) {
      Log.d(TAG, "Alarm exists. Nothing to do :-)");
      return;
    }

    int tension_input_wait_millis = getIntervalMinutes() * 60 * 1000;
    Log.d(TAG, "tension_input_repeating_millis = " + tension_input_wait_millis);

    alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0,
      my_intent, 0);

    alarmManager =
      (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setInexactRepeating(
      AlarmManager.ELAPSED_REALTIME_WAKEUP,
      1,
      tension_input_wait_millis,
      alarmIntent);
  }
}

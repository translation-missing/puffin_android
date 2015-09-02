package com.bluebird_tech.puffin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RepeatingAlarmScheduler {

  private static final String TAG = RepeatingAlarmScheduler.class.getSimpleName();
  AlarmManager alarmManager;
  PendingIntent alarmIntent;

  public void setupAlarms(Context context) {
    Log.d(TAG, "Setup alarms...");

    Intent my_intent = new Intent(context, RepeatingAlarmReceiver_.class);
    my_intent.setAction(RepeatingAlarmReceiver_.ACTIONS_SCHEDULE_ALARM);

    alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, my_intent, PendingIntent.FLAG_NO_CREATE);
    if (alarmIntent != null) {
      Log.d(TAG, "Alarm exists. Nothing to do :-)");
      return;
    }

    int tension_input_wait_millis =
      BuildConfig.TENSION_INPUT_REPEATING_MINUTES * 60 * 1000;
    Log.d(TAG, "tension_input_repeating_millis = " + tension_input_wait_millis);

    alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, my_intent, 0);

    alarmManager =
      (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setInexactRepeating(
      AlarmManager.ELAPSED_REALTIME_WAKEUP,
      1,
      tension_input_wait_millis,
      alarmIntent);
  }
}

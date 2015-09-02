package com.bluebird_tech.puffin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by opahk on 02/09/15.
 */
public class RepeatingAlarmScheduler {

  private static final String TAG = RepeatingAlarmScheduler.class.getSimpleName();
  AlarmManager alarmManager;
  PendingIntent alarmIntent;

  public void setupAlarms(Context context) {
    Log.d(TAG, "Setup alarms...");

    Intent intent = new Intent(context, RepeatingAlarmReceiver_.class);
    intent.setAction(RepeatingAlarmReceiver_.ACTIONS_SCHEDULE_ALARM);
    alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);

    if (alarmIntent != null) {
      // do nothing
      Log.d(TAG, "Alarm exists. Nothing to do :-)");
      return;
    }

    int every_two_hours = 2 * 60 * 60 * 1000; // millis
    // int every_two_hours = 30 * 1000; // millis

    alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

    alarmManager =
      (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setInexactRepeating(
      AlarmManager.ELAPSED_REALTIME_WAKEUP,
      1, // NOTE: like setExactRepeating when not using AlarmManager.INTERVAL_..
      every_two_hours,
      alarmIntent);
  }
}

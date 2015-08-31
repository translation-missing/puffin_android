package com.bluebird_tech.puffin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
  private static final String TAG = BootReceiver.class.getSimpleName();

  private AlarmManager alarmManager;
  private PendingIntent alarmIntent;

  public BootReceiver() {
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
      Log.d(TAG, "Setup alarms...");
      setupAlarms(context);
    }
  }

  public void setupAlarms(Context context) {
    Intent my_intent = new Intent(context, RepeatingAlarmReceiver_.class);
    my_intent.setAction(RepeatingAlarmReceiver_.ACTIONS_SCHEDULE_ALARM);
    alarmIntent = PendingIntent.getBroadcast(context, 0, my_intent, 0);

//    int every_two_hours = 2 * 60 * 60 * 1000; // millis
    int every_two_hours = 10 * 1000; // millis

    alarmManager =
      (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setInexactRepeating(
      AlarmManager.ELAPSED_REALTIME_WAKEUP,
      1, // NOTE: like setExactRepeating when not using AlarmManager.INTERVAL_..
      every_two_hours,
      alarmIntent);
  }
}

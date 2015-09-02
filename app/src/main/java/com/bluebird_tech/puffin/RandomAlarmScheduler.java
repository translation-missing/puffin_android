package com.bluebird_tech.puffin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.security.SecureRandom;
import java.util.Calendar;

public class RandomAlarmScheduler {

  private static final String TAG =
    RandomAlarmScheduler.class.getSimpleName();

  AlarmManager alarmManager;

  /**
   * Schedule an alarm sometime between now and in 60 minutes.
   */
  void scheduleAlarm(Context context) {
    alarmManager =
      (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    if (withinAlarmRange()) {
      alarmManager.set(
        AlarmManager.ELAPSED_REALTIME_WAKEUP,
        millis_from_now(),
        PendingIntent.getBroadcast(context, 0, notificationIntent(context), 0)
      );
    }
  }

  private Boolean withinAlarmRange() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    return calendar.after(startCalendar()) && calendar.before(endCalendar());
  }

  private Calendar startCalendar() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 8);
    calendar.set(Calendar.MINUTE, 0);
    return calendar;
  }

  private Calendar endCalendar() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 19);
    calendar.set(Calendar.MINUTE, 0);
    return calendar;
  }

  private int millis_from_now() {
    int minutes_from_now = 1 + new SecureRandom().nextInt(60);
    int millis_from_now = minutes_from_now * 60 * 1000;
    Log.d(TAG, "min = " + minutes_from_now + ", millis: " + millis_from_now);
    return millis_from_now;
  }

  private Intent notificationIntent(Context context) {
    Intent intent = new Intent(context, RandomAlarmReceiver_.class);
    intent.setAction(
      RandomAlarmReceiver_.ACTIONS_SHOW_TENSION_INPUT_NOTIFICATION);
    return intent;
  }
}

package com.bluebird_tech.puffin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bluebird_tech.puffin.models.StringTime;

import org.androidannotations.annotations.EBean;

import java.util.Calendar;
import java.util.Random;

@EBean
public class RandomAlarmScheduler {

  private static final String TAG =
    RandomAlarmScheduler.class.getSimpleName();

  AlarmManager alarmManager;
  SharedPreferences prefs;

  /**
   * Schedule an alarm sometime between now and in 60 minutes.
   */
  void scheduleAlarm(Context context) {
    prefs = PreferenceManager.getDefaultSharedPreferences(context);
    alarmManager =
      (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    if (withinAlarmRange()) {
      alarmManager.set(
        AlarmManager.ELAPSED_REALTIME_WAKEUP,
        millis_from_now(),
        PendingIntent.getBroadcast(context.getApplicationContext(), 0, notificationIntent(context), 0)
      );
    }
  }

  private Boolean withinAlarmRange() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    return calendar.after(startCalendar()) && calendar.before(endCalendar());
  }

  private Calendar startCalendar() {
    StringTime startTime =
      new StringTime(prefs.getString("tension_input_start", "07:00"));
    Log.d(TAG,
      "Prefs: start: " + startTime.getHour() + ":" + startTime.getMinute());

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, startTime.getHour());
    calendar.set(Calendar.MINUTE, startTime.getMinute());
    return calendar;
  }

  private Calendar endCalendar() {
    StringTime endTime =
      new StringTime(prefs.getString("tension_input_end", "19:00"));
    Log.d(TAG, "Prefs: end: " + endTime.getHour() + ":" + endTime.getMinute());

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, endTime.getHour());
    calendar.set(Calendar.MINUTE, endTime.getMinute());
    return calendar;
  }

  private int millis_from_now() {
    int minutes_from_now =
      1 + new Random().nextInt(BuildConfig.TENSION_INPUT_RANDOM_MINUTES);
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

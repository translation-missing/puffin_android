package com.bluebird_tech.puffin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.api.support.content.AbstractBroadcastReceiver;

import java.security.SecureRandom;
import java.util.Calendar;

@EReceiver
public class RepeatingAlarmReceiver extends AbstractBroadcastReceiver {
  private static final String TAG =
    RepeatingAlarmReceiver.class.getSimpleName();

  @SystemService
  AlarmManager alarmManager;

  /**
   * Schedule an alarm sometime between now and in 60 minutes.
   */
  @ReceiverAction
  void scheduleAlarm(Context context) {
    if(withinAlarmRange()) {
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
    return calendar;
  }

  private Calendar endCalendar() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 19);
    return calendar;
  }

  private Intent notificationIntent(Context context) {
    Intent my_intent = new Intent(context, RepeatingAlarmReceiver_.class);
    my_intent.setAction(
      RepeatingAlarmReceiver_.ACTIONS_SHOW_TENSION_INPUT_NOTIFICATION);
    return my_intent;
  }

  private int millis_from_now() {
    int minutes_from_now = 1 + new SecureRandom().nextInt(60);
    int millis_from_now = minutes_from_now * 60 * 1000;
    Log.d(TAG, "min = " + minutes_from_now + ", millis: " + millis_from_now);
    return millis_from_now;
  }

  /**
   * Triggered by scheduleAlarm(). Shows the TensionInputNotification.
   */
  @ReceiverAction
  void showTensionInputNotification(Context context) {
    Log.d(TAG, "Show notification");
    TensionInputNotification.notify(context);
  }
}

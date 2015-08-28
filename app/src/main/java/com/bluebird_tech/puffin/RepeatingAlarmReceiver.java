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
//    int minutes_from_now = 1 + new SecureRandom().nextInt(60);
    int minutes_from_now = 1 + new SecureRandom().nextInt(1);

    int millis_from_now = minutes_from_now * 60 * 1000;

    Log.d(TAG, "millis: " + millis_from_now);

    Intent my_intent = new Intent(context, RepeatingAlarmReceiver_.class);
    my_intent.setAction(
      RepeatingAlarmReceiver_.ACTIONS_SHOW_TENSION_INPUT_NOTIFICATION);

    alarmManager.set(
      AlarmManager.ELAPSED_REALTIME_WAKEUP,
      millis_from_now,
      PendingIntent.getBroadcast(context, 0, my_intent, 0)
    );
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

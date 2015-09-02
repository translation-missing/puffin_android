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

  /**
   * Triggered by repeating alarm. schedules random alarm.
   */
  @ReceiverAction
  void scheduleAlarm(Context context) {
    RandomAlarmScheduler scheduler = new RandomAlarmScheduler();
    scheduler.scheduleAlarm(context);
  }
}

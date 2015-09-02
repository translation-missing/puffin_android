package com.bluebird_tech.puffin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
  private static final String TAG = BootReceiver.class.getSimpleName();

  public BootReceiver() {
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
      RepeatingAlarmScheduler scheduler = new RepeatingAlarmScheduler();
      scheduler.setupAlarms(context);
    }
  }
}

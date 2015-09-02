package com.bluebird_tech.puffin;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;
import org.androidannotations.api.support.content.AbstractBroadcastReceiver;

/**
 * Created by opahk on 01/09/15.
 */
@EReceiver
public class RandomAlarmReceiver extends AbstractBroadcastReceiver {

  private static final String TAG =
    RandomAlarmScheduler.class.getSimpleName();

  /**
   * Triggered by scheduleAlarm(). Shows the TensionInputNotification.
   */
  @ReceiverAction
  void showTensionInputNotification(Context context) {
    Log.d(TAG, "Show notification");
    TensionInputNotification.notify(context);
  }
}

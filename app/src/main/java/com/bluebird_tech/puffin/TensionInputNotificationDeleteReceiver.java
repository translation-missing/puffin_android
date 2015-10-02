package com.bluebird_tech.puffin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.Date;

@EReceiver
public class TensionInputNotificationDeleteReceiver
  extends BroadcastReceiver
{
  private static final String TAG =
    TensionInputNotificationDeleteReceiver.class.getSimpleName();

  @OrmLiteDao(helper = DatabaseHelper.class)
  Dao<Event, Integer> eventDao;

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.d(TAG, "Notification dismissed");
    long notificationShownAt = intent.getLongExtra("notificationShownAt", -1);
    Date date = new Date();
    date.setTime(notificationShownAt);
    Log.d(TAG, "Notification shown at = " + date);

    Event event =
      Event.fromTensionAndNote(context, Float.parseFloat("0.0"), null);
    event.setNotificationDismissedAt(new Date());
    event.setNotificationShownAt(date);

    saveEventInDatabase(event);
    EventUploaderService_.intent(context.getApplicationContext()).start();
  }

  private void saveEventInDatabase(Event event) {
    try {
      eventDao.create(event);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}

package com.bluebird_tech.puffin;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.bluebird_tech.puffin.models.Event;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

@OptionsMenu(R.menu.menu_tension_list)
@EActivity(R.layout.activity_tension_list)
public class TensionListActivity extends AppCompatActivity {
  private static final String TAG = TensionListActivity.class.getSimpleName();

  @ViewById
  ListView tensionList;

  @Bean
  TensionListAdapter adapter;

  @Override
  protected void onStart() {
    super.onStart();
    // Setup the alarms
    RepeatingAlarmScheduler scheduler = new RepeatingAlarmScheduler();
    scheduler.setupAlarms(this);
  }

  @AfterViews
  void bindAdapter() {
    tensionList.setAdapter(adapter);
  }

  @ItemClick
  void tensionListItemClicked(Event event) {
    Log.d(TAG, new SimpleDateFormat().format(event.getMeasuredAt()));
  }

  @Click
  void fabClicked() {
    StressActivity_.intent(this).start();
  }

  @UiThread
  void showTensionEventCreated(boolean value) {
    String msg = value ? "❤️" : "\uD83D\uDC80";
    Log.d(TAG, "POSTing result msg: " + msg);
//    Snackbar.make(tensionList, msg, Snackbar.LENGTH_SHORT).show();
  }

  @Receiver(actions="com.bluebird_tech.puffin.TENSION_EVENT_CREATED")
  void tensionEventCreated(@Receiver.Extra("tensionEventCreated") boolean value) {
    showTensionEventCreated(value);
  }

  @OptionsItem
  void actionSettings() {
    SettingsActivity_.intent(this).start();
  }
}

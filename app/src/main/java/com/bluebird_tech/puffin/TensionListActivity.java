package com.bluebird_tech.puffin;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.bluebird_tech.puffin.models.Event;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    setupChart();
  }

  int chartIndex(Event event, List<Event> events) {
    long resolution = 1000 * 10;
    long init = 0;
    if (events.size() > 0) {
      init = events.get(events.size() - 1).getMeasuredAt().getTime();
    }
    return ((int) ((event.getMeasuredAt().getTime() - init) / resolution)) - 1;
  }

  void setupChart() {
    LineChart chart = (LineChart) findViewById(R.id.chart);

    List<Event> events = adapter.getItems();

    ArrayList<Entry> valsComp1 = new ArrayList<Entry>();

    for (Event event: events) {
      Entry entry = new Entry(event.getValue(), chartIndex(event, events));
      valsComp1.add(entry);
    }

    LineDataSet dataSet = new LineDataSet(valsComp1, "Tension");
    dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

    // styling of dataset
    dataSet.setDrawValues(false);
    dataSet.setLineWidth(4f);
    dataSet.setCircleSize(5f);
    dataSet.setDrawCircles(true);
    dataSet.setDrawCubic(true);
    dataSet.setCubicIntensity(0.05f);
    dataSet.setColors(new int[]{R.color.primary}, this);
    dataSet.setCircleColor(ContextCompat.getColor(this, R.color.primary));

    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    dataSets.add(dataSet);

    ArrayList<String> xVals = new ArrayList<String>();
    DateFormat df = new SimpleDateFormat("dd.MM HH:mm");
    int resolution = 1000 * 10;
    if (events.size() > 0) {
      long exit = events.get(0).getMeasuredAt().getTime();
      long init = events.get(events.size() - 1).getMeasuredAt().getTime();
      int xSize = (int) Math.max(Math.ceil((exit - init) / resolution), events.size());
      for (int i = 0; i < xSize; i++) {
        xVals.add(df.format(new Date(init + i * resolution)));
      }
    }

    LineData data = new LineData(xVals, dataSets);
    chart.setData(data);

    // styling
    Legend legend = chart.getLegend();
    legend.setEnabled(false);

    XAxis xAxis = chart.getXAxis();
    xAxis.setPosition(XAxisPosition.BOTTOM);

    YAxis leftAxis = chart.getAxisLeft();
    leftAxis.setStartAtZero(true);
    leftAxis.setAxisMaxValue(100);
    leftAxis.setValueFormatter(new IntegerFormatter());
    leftAxis.setLabelCount(10, false);

    YAxis rightAxis = chart.getAxisRight();
    rightAxis.setEnabled(false);

    chart.setDescription("");

    // refresh
    chart.invalidate();
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

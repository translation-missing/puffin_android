package com.bluebird_tech.puffin;

import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bluebird_tech.puffin.models.Event;
import com.crashlytics.android.Crashlytics;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;

@OptionsMenu(R.menu.menu_tension_list)
@EActivity(R.layout.activity_tension_list)
public class TensionListActivity extends AppCompatActivity
  implements DatePickerFragment.OnDateSetListener {
  private static final String TAG = TensionListActivity.class.getSimpleName();

  private GestureDetectorCompat detector;

  @ViewById
  ListView tensionList;

  @ViewById
  ImageButton yesterdayButton;

  @ViewById
  ImageButton tomorrowButton;

  @ViewById
  TextView currentDate;

  Date date;

  @ViewById
  LineChart chart;

  @Bean
  TensionListAdapter adapter;

  @Override
  protected void onStart() {
    super.onStart();
    Fabric.with(this, new Crashlytics()); /* TODO: user opt-in... */
    if (!BuildConfig.HAS_CONFIG_EMOTIONS) {
      // TODO: remove old/stale/non-editable preferences...
    }
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    RepeatingAlarmScheduler scheduler = new RepeatingAlarmScheduler();
    scheduler.setupAlarms(this);
    getSupportActionBar().setTitle(R.string.title_activity_tension_list);
    changeDate(new Date());
    setupChart();
//    detector = new GestureDetectorCompat(this, new MyGestureListener());
    detector = new GestureDetectorCompat(getBaseContext(), new MyGestureListener());
  }

  int chartIndex(Event event, List<Event> events) {
    long resolution = 1000 * 10;
    long init = 0;
    if (events.size() > 0) {
      init = events.get(events.size() - 1).getMeasuredAt().getTime();
    }
    return ((int) ((event.getMeasuredAt().getTime() - init) / resolution));
  }

  // TODO: extract
  void setupChart() {
    List<Event> events = adapter.getItems();

    ArrayList<Entry> valsComp1 = new ArrayList<Entry>();

    for (Event event: events) {
      Entry entry = new Entry(event.getValue(), chartIndex(event, events));
      valsComp1.add(0, entry); // reverse order
    }

    LineDataSet dataSet = new LineDataSet(valsComp1, "Tension");
    dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

    // styling of dataset
    dataSet.setDrawValues(false);
    dataSet.setLineWidth(4f);
    dataSet.setCircleSize(5f);
    dataSet.setDrawCircles(true);
    // dataSet.setDrawCubic(true);
    // dataSet.setCubicIntensity(0.05f);
    // dataSet.setDrawCircleHole(false);
    dataSet.setColors(new int[]{R.color.material_grey_600}, this);
    dataSet.setCircleColor(
      ContextCompat.getColor(this, R.color.material_grey_600));

    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    dataSets.add(dataSet);

    ArrayList<String> xVals = new ArrayList<String>();
    DateFormat df = new SimpleDateFormat("dd.MM HH:mm");
    int resolution = 1000 * 10;
    if (events.size() > 0) {
      long exit = events.get(0).getMeasuredAt().getTime();
      long init = events.get(events.size() - 1).getMeasuredAt().getTime();
      int xSize = (int) Math.max(Math.ceil((exit - init) / resolution), events.size());
      for (int i = 0; i <= xSize; i++) {
        xVals.add(df.format(new Date(init + i * resolution)));
      }
    }

    LineData data = new LineData(xVals, dataSets);
    chart.setData(data);

    // styling
    Legend legend = chart.getLegend();
    legend.setEnabled(false);

    XAxis xAxis = chart.getXAxis();
//    xAxis.setPosition(XAxisPosition.BOTTOM);
    xAxis.setEnabled(false);

    YAxis leftAxis = chart.getAxisLeft();
    leftAxis.setStartAtZero(true);
    leftAxis.setAxisMaxValue(100);
    leftAxis.setValueFormatter(new IntegerFormatter());
    leftAxis.setLabelCount(10, false);
    leftAxis.setDrawAxisLine(true);
    leftAxis.setGridLineWidth(2.0f);

    // custom grid lines
    YAxisRenderer rendererLeftYAxis = chart.getRendererLeftYAxis();
    ColorYAxisRenderer renderer = new ColorYAxisRenderer(this,
      chart.getViewPortHandler(), leftAxis, rendererLeftYAxis.getTransformer());
    renderer.setGridColors(new int[]{
      R.color.tension_level_low,
      android.R.color.transparent,
      android.R.color.transparent,
      R.color.tension_level_middle,
      android.R.color.transparent,
      android.R.color.transparent,
      android.R.color.transparent,
      R.color.tension_level_high,
      android.R.color.transparent,
      android.R.color.transparent,
      android.R.color.transparent,
    });
    chart.setRendererLeftYAxis(renderer);

    YAxis rightAxis = chart.getAxisRight();
    rightAxis.setEnabled(false);

    chart.setDescription("");
    chart.setGridBackgroundColor(
      ContextCompat.getColor(this, R.color.window_background));


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

  @Receiver(actions = "com.bluebird_tech.puffin.TENSION_EVENT_CREATED")
  void tensionEventCreated(@Receiver.Extra("tensionEventCreated") boolean value) {
    showTensionEventCreated(value);
  }

  @OptionsItem
  void actionSettings() {
    SettingsActivity_.intent(this).start();
  }

  @OptionsItem
  void actionCalendar() {
    DatePickerFragment newFragment = new DatePickerFragment();
    newFragment.show(getSupportFragmentManager(), "datePicker");
  }

  @Override
  public void onDateSet(Date date) {
    changeDate(date);
  }

  private void changeDate(Date date) {
    this.date = date;
    updateList();
    setupChart();

    DateFormat df = android.text.format.DateFormat.getMediumDateFormat(this);
    currentDate.setText(df.format(date));
  }

  private void updateList() {
    adapter.updateDate(date);
    adapter.notifyDataSetChanged();
  }

  @Click
  void tomorrowButtonClicked() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, 1);
    changeDate(cal.getTime());
  }

  @Click
  void yesterdayButtonClicked() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, -1);
    changeDate(cal.getTime());
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    detector.onTouchEvent(event);
    return super.onTouchEvent(event);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    super.dispatchTouchEvent(ev);
    return detector.onTouchEvent(ev);
  }

  class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private boolean newScroll = false;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float _velocityX, float _velocityY) {
      if (!newScroll)
        return false;

      if ((_velocityX < -40f) && e1.getX() < e2.getX()) {
        newScroll = false;
        yesterdayButtonClicked();
        return true;
      } else if ((_velocityX > 40.0f) && e1.getX() > e2.getX()) {
        newScroll = false;
        tomorrowButtonClicked();
        return true;
      }
      return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
      newScroll = true;
      return true;
    }
  }
}

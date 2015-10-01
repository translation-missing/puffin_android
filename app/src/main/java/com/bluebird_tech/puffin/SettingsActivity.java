package com.bluebird_tech.puffin;

import android.preference.Preference;
import android.preference.PreferenceActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

@PreferenceScreen(R.xml.preferences)
@EActivity
public class SettingsActivity extends PreferenceActivity {
  @PreferenceChange
  void tensionInputInterval(Preference _pref, Integer value) {
    RepeatingAlarmScheduler scheduler = new RepeatingAlarmScheduler();
    scheduler.cancelAlarm(this);
    scheduler.setupAlarms(this, value);
  }
}

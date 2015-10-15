package com.bluebird_tech.puffin;

import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

@PreferenceScreen(R.xml.preferences)
@EActivity
public class SettingsActivity extends PreferenceActivity {
  @PreferenceByKey(R.string.tension_input_help_show)
  CheckBoxPreference tensionInputHelpShow;

  @PreferenceByKey(R.string.internal_settings)
  PreferenceCategory internalSettings;

  @PreferenceByKey(R.string.tension_input_version)
  Preference version;

  @PreferenceChange
  void tensionInputInterval(Preference _pref, Integer value) {
    RepeatingAlarmScheduler scheduler = new RepeatingAlarmScheduler();
    scheduler.cancelAlarm(this);
    scheduler.setupAlarms(this, value);
  }

  @AfterPreferences
  void initPrefs() {
//    internalSettings.removePreference(tensionInputHelpShow);
    internalSettings.removeAll();
    version.setSummary(BuildConfig.VERSION_NAME);
  }
}

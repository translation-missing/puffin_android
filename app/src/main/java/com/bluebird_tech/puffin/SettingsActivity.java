package com.bluebird_tech.puffin;

import android.preference.CheckBoxPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.support.v7.app.ActionBar;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

import java.util.Set;

@PreferenceScreen(R.xml.preferences)
@EActivity
public class SettingsActivity extends AppCompatPreferenceActivity {
  @PreferenceByKey(R.string.tension_input_help_show)
  CheckBoxPreference tensionInputHelpShow;

  @PreferenceByKey(R.string.internal_settings)
  PreferenceCategory internalSettings;

  @PreferenceByKey(R.string.tension_input_version)
  Preference version;

  @PreferenceByKey(R.string.preference_device_id)
  Preference deviceId;

  @PreferenceByKey(R.string.emotions)
  MultiSelectListPreference emotions;

  @PreferenceByKey(R.string.preference_category_emotions)
  PreferenceCategory emotionCategory;

  @PreferenceByKey(R.string.preference_interval)
  Preference tensionInputInterval;

  @PreferenceByKey(R.string.preference_category_reminders)
  PreferenceCategory tensionInputReminders;

  @PreferenceChange
  void tensionInputInterval(Preference _pref, Integer value) {
    RepeatingAlarmScheduler scheduler = new RepeatingAlarmScheduler();
    scheduler.cancelAlarm(this);
    scheduler.setupAlarms(this, value);
  }

  @PreferenceChange
  void emotions(Set<String> value) {
    updateEmotionsSummary(value);
  }

  @Override
  protected void onStart() {
    super.onStart();
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
  }

  @AfterPreferences
  void initPrefs() {
    // internalSettings.removePreference(tensionInputHelpShow);
    internalSettings.removeAll();
    if (!BuildConfig.HAS_CONFIG_EMOTIONS) {
      getPreferenceScreen().removePreference(emotionCategory);
      tensionInputReminders.removePreference(tensionInputInterval);
    } else {
      updateEmotionsSummary();
    }

    version.setSummary(BuildConfig.VERSION_NAME);
    deviceId.setSummary(Utility.getDeviceId(this));
  }

  private void updateEmotionsSummary() {
    updateEmotionsSummary(emotions.getValues());
  }

  private void updateEmotionsSummary(Set<String> values) {
    String summary =
      getResources().getString(R.string.preferences_emotions_summary);
    emotions.setSummary(String.format(summary, values.size()));
  }
}

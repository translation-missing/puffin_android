package com.bluebird_tech.puffin;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.bluebird_tech.puffin.net.EventClient;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@OptionsMenu(R.menu.menu_emotion_input)
@EActivity(R.layout.activity_emotion_input)
public class EmotionInputActivity extends AppCompatActivity {
  private static final String TAG = EmotionInputActivity.class.getSimpleName();

  SharedPreferences prefs;

  @RestService
  EventClient eventClient;

  @OrmLiteDao(helper = DatabaseHelper.class)
  Dao<Event, Integer> eventDao;

  @Extra
  Integer eventId;

  @ViewById
  LinearLayout joy;
  @ViewById
  LinearLayout shame;
  @ViewById
  LinearLayout anger;
  @ViewById
  LinearLayout sadness;
  @ViewById
  LinearLayout emptiness;
  @ViewById
  LinearLayout guilt;
  @ViewById
  LinearLayout fear;
  @ViewById
  LinearLayout disgust;
  @ViewById
  LinearLayout confidence;
  @ViewById
  LinearLayout worry;
  @ViewById
  LinearLayout boredom;
  @ViewById
  LinearLayout loneliness;
  @ViewById
  LinearLayout desperation;
  @ViewById
  LinearLayout fatigue;

  private Map<String, LinearLayout> emotionLayouts;

  private Date measuredAt;

  @Override
  protected void onStart() {
    super.onStart();
    prefs = PreferenceManager.getDefaultSharedPreferences(this);
    if (activeEmotions().isEmpty()) {
      finishActivity();
    } else {
      setupEmotionLayouts();
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      setupEmoBars();
    }
  }

  void setupEmotionLayouts() {
    emotionLayouts = new HashMap<>();
    emotionLayouts.put("joy", joy);
    emotionLayouts.put("shame", shame);
    emotionLayouts.put("anger", anger);
    emotionLayouts.put("sadness", sadness);
    emotionLayouts.put("emptiness", emptiness);
    emotionLayouts.put("guilt", guilt);
    emotionLayouts.put("fear", fear);
    emotionLayouts.put("disgust", disgust);
    emotionLayouts.put("confidence", confidence);
    emotionLayouts.put("worry", worry);
    emotionLayouts.put("boredom", boredom);
    emotionLayouts.put("loneliness", loneliness);
    emotionLayouts.put("desperation", desperation);
    emotionLayouts.put("fatigue", fatigue);
  }

  void setupEmoBars() {
    for (String emotion: activeEmotions())
      setupSeekBar(emotion, emotionLayouts.get(emotion));
  }

  private Set<String> activeEmotions() {
    if (!BuildConfig.HAS_CONFIG_EMOTIONS) {
      return new HashSet<String>(Arrays.asList(
        getResources().getStringArray(R.array.emotions_default_values)));
    } else {
      return prefs.getStringSet("emotions", null);
    }
  }

  private void setupSeekBar(String emotion, LinearLayout layout) {
    final TextView label = (TextView) layout.findViewById(R.id.label);
    label.setText(
      Utility.getStringResourceByName(this, "emotion_" + emotion + "_label"));

    final TextView value = (TextView) layout.findViewById(R.id.value);

    SeekBar bar = (SeekBar) layout.findViewById(R.id.bar);
    int color = getResources().getColor(R.color.accent);
    bar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    bar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);

    bar.setOnSeekBarChangeListener(
      new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
          value.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
      }
    );
    layout.setVisibility(View.VISIBLE);
  }

  @UiThread
  void finishActivity() {
    finish();
  }

  @OptionsItem
  void actionSave() {
    Map<String, Integer> emotions = new HashMap<>();
    for (String emotion: activeEmotions()) {
      SeekBar bar = (SeekBar) emotionLayouts.get(emotion).findViewById(R.id.bar);
      emotions.put(emotion, bar.getProgress());
    }
    saveEmotionsInBackground(emotions);
  }

  private void saveEventInDatabase(Event event) {
    try {
      eventDao.create(event);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Background
  void saveEmotionsInBackground(Map<String, Integer> emotions) {
    updateTensionEvent(emotions);
    saveEmotionEvents(emotions);
  }

  void updateTensionEvent(Map<String, Integer> emotions) {
    if (eventId != null) {
      Event event = null;
      try {
        event = eventDao.queryForId(eventId);
        if (event != null) {
          event.setTags(new JSONObject(emotions).toString());
          eventDao.update(event);
          this.measuredAt = event.getMeasuredAt();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  void saveEmotionEvents(Map<String, Integer> emotions) {
    for (Map.Entry<String, Integer> entry: emotions.entrySet()) {
      String key = entry.getKey();
      Integer value = entry.getValue();

      Event event = Event.fromTensionAndNote(this, (float) value, "");
      event.setMeasuredAt(measuredAt);
      event.setMeasurement(key);

      if (eventId != null) {
        event.setTags("event_id=" + eventId);
      }

      saveEventInDatabase(event);
    }

    finishActivity();
    EventUploaderService_.intent(getApplication()).start();
  }
}

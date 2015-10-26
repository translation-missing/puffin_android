package com.bluebird_tech.puffin;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.bluebird_tech.puffin.net.EventClient;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.sql.SQLException;
import java.util.Date;

@OptionsMenu(R.menu.menu_stress)
@EActivity(R.layout.activity_stress)
public class StressActivity extends AppCompatActivity {
  private static final String TAG = StressActivity.class.getSimpleName();

  @RestService
  EventClient eventClient;

  @OrmLiteDao(helper = DatabaseHelper.class)
  Dao<Event, Integer> eventDao;

  SharedPreferences prefs;

  @Extra
  Long notificationShownAt;

  private Date notificationAcceptedAt;
  private Integer eventId;

  @ViewById(R.id.stress_seek_level)
  VerticalSeekBar bar;

  @ViewById(R.id.stress_text_level)
  TextView level;

  @ViewById
  EditText note;

  @ViewById(R.id.stress_button_save)
  Button save_button;

  private ActionBar actionBar;

  @Override
  protected void onStart() {
    super.onStart();
    prefs = PreferenceManager.getDefaultSharedPreferences(this);
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    colorize(0);

    if (notificationShownAt != null)
      this.notificationAcceptedAt = new Date();

    if (prefs.getBoolean("tension_input_help_show", true)) {
      prefs.edit().putBoolean("tension_input_help_show", false).commit();
      actionInfo();
    }
  }

  @OptionsItem
  void actionInfo() {
    TensionInfoFragment fragment = new TensionInfoFragment();
    fragment.show(getSupportFragmentManager(), "missiles");
  }

  void colorize(int progress) {
    int color = getResources().getColor(R.color.tension_level_low);
    if ((30 <= progress) && (progress < 70)) {
      color = getResources().getColor(R.color.tension_level_middle);
    } else if (70 <= progress) {
      color = getResources().getColor(R.color.tension_level_high);
    }
    bar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    bar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    actionBar.setBackgroundDrawable(new ColorDrawable(color));

    // status bar color (http://stackoverflow.com/a/22192691)
    if (android.os.Build.VERSION.SDK_INT >= 21) {
      Window window = getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(color);
      window.setNavigationBarColor(color);
    }
  }

  @SeekBarProgressChange(R.id.stress_seek_level)
  void onProgressChangedOnSeekBar(SeekBar seekBar, int progress, boolean b) {
    level.setText(Integer.toString(progress));
    colorize(progress);
  }

  @Click(R.id.stress_button_save)
  void clickSaveTension() {
    handleClickSaveTension();
  }

  @OptionsItem
  void actionSave() {
    handleClickSaveTension();
  }

  private void handleClickSaveTension() {
    Float tension = (float) bar.getProgress();
    save_button.setEnabled(false);
    saveTensionInBackground(tension, note.getText().toString());
  }

  @Background
  void saveTensionInBackground(Float tension, String theNote) {
    Event event = Event.fromTensionAndNote(this, tension, theNote);

    if (notificationShownAt != null) {
      Date date = new Date();
      date.setTime(notificationShownAt);
      event.setNotificationShownAt(date);
      event.setNotificationAcceptedAt(notificationAcceptedAt);
    }

    saveEventInDatabase(event);
    loadEmotionInputActivity();
    EventUploaderService_.intent(getApplication()).start();
  }

  @UiThread
  void loadEmotionInputActivity() {
    finish();
    EmotionInputActivity_.intent(this).eventId(this.eventId).start();
//    TensionListActivity_.intent(this)
//      .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
//      .start();
  }

  private void saveEventInDatabase(Event event) {
    try {
      eventDao.create(event);
      this.eventId = event.getId().intValue();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}

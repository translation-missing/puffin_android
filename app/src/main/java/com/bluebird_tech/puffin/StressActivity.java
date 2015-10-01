package com.bluebird_tech.puffin;

import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.sql.SQLException;

@OptionsMenu(R.menu.menu_stress)
@EActivity(R.layout.activity_stress)
public class StressActivity extends AppCompatActivity {
  private static final String TAG = StressActivity.class.getSimpleName();

  @RestService
  EventClient eventClient;

  @OrmLiteDao(helper = DatabaseHelper.class)
  Dao<Event, Integer> eventDao;

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
    actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    colorize(0);
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
  }

  @SeekBarProgressChange(R.id.stress_seek_level)
  void onProgressChangedOnSeekBar(SeekBar seekBar, int progress, boolean b) {
    level.setText(Integer.toString(progress));
    colorize(progress);
  }

  @Click(R.id.stress_button_save)
  void clickSaveTension() {
    Float tension = (float) bar.getProgress();
    save_button.setEnabled(false);
    saveTensionInBackground(tension, note.getText().toString());
  }

  @Background
  void saveTensionInBackground(Float tension, String theNote) {
    Event event = Event.fromTensionAndNote(this, tension, theNote);
    saveEventInDatabase(event);
    loadTensionListActivity();
    EventUploaderService_.intent(getApplication()).start();
  }

  @UiThread
  void loadTensionListActivity() {
//    TensionListActivity_.intent(this)
//      .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
//      .start();
    finish();
  }

  private void saveEventInDatabase(Event event) {
    try {
      eventDao.create(event);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}

package com.bluebird_tech.puffin;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.bluebird_tech.puffin.net.EventClient;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.sql.SQLException;

@EActivity(R.layout.activity_stress)
public class StressActivity extends OrmLiteBaseActivity<DatabaseHelper> {
  private static final String TAG = StressActivity.class.getSimpleName();

  @RestService
  EventClient eventClient;

  @ViewById(R.id.stress_seek_level)
  SeekBar bar;

  @ViewById(R.id.stress_text_level)
  TextView level;

  @ViewById(R.id.stress_button_save)
  Button save_button;

  @SeekBarProgressChange(R.id.stress_seek_level)
  void onProgressChangedOnSeekBar(SeekBar seekBar, int progress, boolean b) {
    level.setText(Integer.toString(progress));
  }

  @Click(R.id.stress_button_save)
  void clickSaveTension() {
    String tension = Integer.toString(bar.getProgress());
    save_button.setEnabled(false);
    saveTensionInBackground(tension);
  }

  @Background
  void saveTensionInBackground(String tension) {
    Event event = Event.fromTension(tension);
    saveEventInDatabase(event);
    boolean success = uploadEvent(event);
    showResult(success);
  }

  @UiThread
  void showResult(boolean success) {
    save_button.setEnabled(true);
    String msg = success ? "❤️" : "\uD83D\uDC80";
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }

  private void saveEventInDatabase(Event event) {
    try {
      Dao<Event, Integer> dao = getHelper().getEventDao();
      dao.create(event);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean uploadEvent(Event event) {
    try {
      eventClient.createEvent(event);
      return true;
    } catch (RestClientException e) {
      e.printStackTrace();
      return false;
    }
  }
}

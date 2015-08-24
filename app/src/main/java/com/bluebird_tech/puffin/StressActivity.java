package com.bluebird_tech.puffin;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class StressActivity
    extends OrmLiteBaseActivity<DatabaseHelper>
    implements SeekBar.OnSeekBarChangeListener {
  private static final String TAG = StressActivity.class.getSimpleName();

  private SeekBar bar;
  private TextView level;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_stress);

    bar = (SeekBar)findViewById(R.id.stress_seek_level);
    bar.setOnSeekBarChangeListener(this);

    level = (TextView)findViewById(R.id.stress_text_level);
//      stress_button_save
    findViewById(R.id.stress_button_save).setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        click();
      }
    });

//      updateScreenValue();
  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
    level.setText(Integer.toString(progress));
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_stress, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  // @Override
  // public void onCreate(Bundle savedInstanceState) {
  // public static void callMe(Context c, Integer clickCounterId) {
  // Intent intent = new Intent(c, CounterScreen.class);
  // intent.putExtra(CLICK_COUNTER_ID, clickCounterId);
  // c.startActivity(intent);
  // }

//	private void fillText(int resId, String text) {
//		TextView textView = (TextView) findViewById(resId);
//		textView.setText(text);
//	}

  private void click() {
    Log.v("app", "Klicketyklick!" + bar.getProgress());
    try {
      Dao<Event, Integer> dao = getHelper().getEventDao();
//        ClickCount clickCount = dao.queryForId(countId);
//        if (clickCount.getValue() < countValue) {
//          clickCount.changeValue(countValue);
//          dao.update()


//        > Account acc = new Account();
//        >
//        > acc.setName("Example");
//        >
//        > AccountDao.createOrupdate(acc);

       now = new Date();
      Event event = new Event();
      event.setMeasurement("tension");
      event.setFields("" + bar.getProgress());
      event.setCreatedAt(now);
      event.setUpdatedAt(now);
//        dao.createOrUpdate(event);
      dao.create(event);

      List<Event> results = dao.queryBuilder().query();
      for (Event e: results) {
        Log.d(TAG, "" + e.getMeasurement() + e.getFields() + e.getCreatedAt().toString() + e.getId());
        System.out.println(e.toString());
      }


//      Log.d(TAG, results);




    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
//	private void click() {
//		int value = countValue.incrementAndGet();
//
//		AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
//
//			@Override
//			protected Void doInBackground(Integer... integers) {
//				Integer countId = integers[0];
//				Integer countValue = integers[1];
//
//				try {
//					Dao<ClickCount, Integer> dao = getHelper().getClickDao();
//					ClickCount clickCount = dao.queryForId(countId);
//					if (clickCount.getValue() < countValue) {
//						clickCount.changeValue(countValue);
//						dao.update(clickCount);
//					}
//				} catch (SQLException e) {
//					throw new RuntimeException(e);
//				}
//
//				return null;
//			}
//
//			@Override
//			protected void onPostExecute(Void aVoid) {
//				updateScreenValue();
//				MediaPlayer mp = MediaPlayer.create(CounterScreen.this, R.raw.click);
//				mp.start();
//			}
//		};
//
//		asyncTask.execute(clickCounterid, value);
//	}

//	private void updateScreenValue() {
//		((TextView) findViewById(R.id.countValue)).setText(Integer.toString(countValue.get()));
//	}
}

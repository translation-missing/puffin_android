package com.bluebird_tech.puffin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class StressActivity
  extends OrmLiteBaseActivity<DatabaseHelper>
  implements SeekBar.OnSeekBarChangeListener {

    private SeekBar bar;
    private TextView level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_stress);

      bar = (SeekBar)findViewById(R.id.stress_seek_level);
      bar.setOnSeekBarChangeListener(this);

      level = (TextView)findViewById(R.id.stress_text_level);

//      findViewById(R.id.clickButton).setOnClickListener(new View.OnClickListener() {
//        public void onClick(View view) {
//          click();
//        }
//      });

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

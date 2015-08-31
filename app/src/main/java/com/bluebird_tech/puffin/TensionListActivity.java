package com.bluebird_tech.puffin;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import static android.widget.Toast.makeText;

@EActivity(R.layout.activity_tension_list)
//public class TensionListActivity extends OrmLiteBaseActivity<DatabaseHelper> {
public class TensionListActivity extends AppCompatActivity {
  private static final String TAG = TensionListActivity.class.getSimpleName();

  @ViewById
  ListView tensionList;

  @Bean
  TensionListAdapter adapter;

  @AfterViews
  void bindAdapter() {
    tensionList.setAdapter(adapter);
  }

  @ItemClick
  void tensionListItemClicked(Event event) {
    makeText(this, event.getMeasurement() + ", value = " + event.getFields(), Toast.LENGTH_SHORT).show();
  }

  @Click
  void fabClicked() {
    StressActivity_.intent(this).start();
  }
}

package com.bluebird_tech.puffin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OrmLiteDao;

//@EActivity(R.layout.activity_tension_list)
@EActivity
public class TensionListActivity extends OrmLiteBaseActivity<DatabaseHelper> {
  private static final String TAG = TensionListActivity.class.getSimpleName();

  @OrmLiteDao(helper = DatabaseHelper.class)
  Dao<Event, Integer> userDao;
}

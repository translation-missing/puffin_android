package com.bluebird_tech.puffin.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bluebird_tech.puffin.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * @see http://stackoverflow.com/q/17298773
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
  /* c&p start */
  private static final String DATABASE_NAME = "puffin.db";
  private static final int DATABASE_VERSION = 8;

  private Dao<Event, Integer> eventDao;

  public DatabaseHelper(Context context) {
    // first time:
    // super(context, DATABASE_NAME, null, DATABASE_VERSION, 1);
    super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
  }
  /* c&p end */

  @Override
  public void onCreate(SQLiteDatabase sqliteDatabase,
                       ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, Event.class);
    } catch (SQLException e) {
      Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqliteDatabase,
                        ConnectionSource connectionSource, int oldVer, int newVer) {
//    try {
//      // TODO: migrate, don't drop!
//      TableUtils.dropTable(connectionSource, Event.class, true);
//      onCreate(sqliteDatabase, connectionSource);
//    } catch (SQLException e) {
//      Log.e(DatabaseHelper.class.getName(),
//        "Unable to upgrade database from version " + oldVer + " to new "
//          + newVer, e);
//    }
  }

  public Dao<Event, Integer> getEventDao() throws SQLException {
    if (eventDao == null) {
      eventDao = getDao(Event.class);
    }
    return eventDao;
  }
}

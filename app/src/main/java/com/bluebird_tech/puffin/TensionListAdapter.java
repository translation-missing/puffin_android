package com.bluebird_tech.puffin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.RootContext;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EBean
public class TensionListAdapter extends BaseAdapter {

  List<Event> events;

  Date date;

  @OrmLiteDao(helper = DatabaseHelper.class)
  Dao<Event, Integer> eventDao;

  @RootContext
  Context context;

  @AfterInject
  void initAdapter() {
    if (this.date == null)
      this.date = new Date();
    queryDatabase();
  }

  public void updateDate(Date date) {
    this.date = date;
    queryDatabase();
  }

  // TODO: generalize date queries
  void queryDatabase() {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    try {
      QueryBuilder<Event, Integer> queryBuilder = eventDao.queryBuilder();
      queryBuilder.where().eq(Event.FIELD_MEASUREMENT, "tension").and()
        .isNull(Event.FIELD_NOTIFICATION_DISMISSED_AT).and()
        .raw("substr(measured_at, 0, 11) = '" + df.format(date) + "'");
      queryBuilder.orderBy(Event.FIELD_MEASURED_AT, false);
      events = queryBuilder.query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    TensionItemView tensionItemView;
    if (convertView == null) {
      tensionItemView = TensionItemView_.build(context);
    } else {
      tensionItemView = (TensionItemView) convertView;
    }
    tensionItemView.bind(getItem(position));
    return tensionItemView;
  }

  @Override
  public int getCount() {
    return events.size();
  }

  public List<Event> getItems() {
    return events;
  }

  @Override
  public Event getItem(int position) {
    return events.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }
}

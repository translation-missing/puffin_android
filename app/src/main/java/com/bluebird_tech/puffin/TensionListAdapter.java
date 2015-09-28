package com.bluebird_tech.puffin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.RootContext;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@EBean
public class TensionListAdapter extends BaseAdapter {

  List<Event> events;

  @OrmLiteDao(helper = DatabaseHelper.class)
  Dao<Event, Integer> eventDao;

  @RootContext
  Context context;

  @AfterInject
  void initAdapter() {
    try {
      QueryBuilder<Event, Integer> queryBuilder = eventDao.queryBuilder();
      queryBuilder.where().eq(Event.FIELD_MEASUREMENT, "tension");
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

    Date previousDate = null;
    if (position > 0)
      previousDate = getItem(position - 1).getMeasuredAt();

    tensionItemView.bind(getItem(position), previousDate);

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

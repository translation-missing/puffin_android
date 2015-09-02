package com.bluebird_tech.puffin;

import android.app.IntentService;
import android.content.Intent;

import com.bluebird_tech.puffin.models.DatabaseHelper;
import com.bluebird_tech.puffin.models.Event;
import com.bluebird_tech.puffin.net.EventClient;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@EService
public class EventUploaderService extends IntentService {

  @RestService
  EventClient eventClient;

  @OrmLiteDao(helper = DatabaseHelper.class, model = Event.class)
  Dao<Event, Integer> eventDao;

  public EventUploaderService() {
    super(EventUploaderService.class.getSimpleName());
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    for (Event event: pendingEvents()) {
      try {
        try {
          event.setCtx(getApplicationContext()); // for android id
          eventClient.createEvent(event);
          event.setUploadedAt(new Date());
          eventDao.update(event);
        } catch (RestClientException e) {
          e.printStackTrace();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

//    showToast();
  }

  private List<Event> pendingEvents() {
    try {
      QueryBuilder<Event, Integer> queryBuilder = eventDao.queryBuilder();
      queryBuilder.where().isNull(Event.FIELD_UPLOADED_AT);
      return queryBuilder.query();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

//  @UiThread
//  void showToast() {
//    Toast.makeText(getApplicationContext(), "Hello World!", Toast.LENGTH_LONG).show();
//  }
}

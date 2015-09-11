package com.bluebird_tech.puffin.models;

import android.support.annotation.NonNull;

/**
 * Created by gewo on 11/09/15.
 */
public class StringTime {
  private String[] hoursAndMinutes;

  public StringTime(@NonNull String time) {
    hoursAndMinutes = time.split(":");
  }

  public int getHour() {
    return Integer.parseInt(hoursAndMinutes[0]);
  }

  public int getMinute() {
    return Integer.parseInt(hoursAndMinutes[1]);
  }
}

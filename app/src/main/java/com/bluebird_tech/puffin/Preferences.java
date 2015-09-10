package com.bluebird_tech.puffin;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface Preferences {
  @DefaultInt(7)
  int tensionInputStartHour();

  @DefaultInt(0)
  int tensionInputStartMinute();

  @DefaultInt(19)
  int tensionInputEndHour();

  @DefaultInt(0)
  int tensionInputEndMinute();
}

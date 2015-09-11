package com.bluebird_tech.puffin;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface MyPrefs {
//  @DefaultString(value = "07:00", keyRes = R.string.preferences_tension_input_start)
  @DefaultString("07:00")
  String tensionInputStart();

  @DefaultInt(7)
  int tensionInputStartHour();

  @DefaultInt(0)
  int tensionInputStartMinute();

  @DefaultInt(19)
  int tensionInputEndHour();

  @DefaultInt(0)
  int tensionInputEndMinute();
}

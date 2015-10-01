package com.bluebird_tech.puffin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment
  extends DialogFragment
  implements DatePickerDialog.OnDateSetListener {

  private OnDateSetListener listener;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      listener = (OnDateSetListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() +
        " must implement OnDateSetListener");
    }
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog dialog =
      new DatePickerDialog(getActivity(), this, year, month, day);
    return dialog;
  }

  public void onDateSet(DatePicker view, int year, int month, int day) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.set(year, month, day);
    listener.onDateSet(calendar.getTime());
  }

  public interface OnDateSetListener {
    public void onDateSet(Date date);
  }
}

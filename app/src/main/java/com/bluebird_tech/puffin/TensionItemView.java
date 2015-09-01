package com.bluebird_tech.puffin;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluebird_tech.puffin.models.Event;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@EViewGroup(R.layout.activity_tension_list_item)
public class TensionItemView extends LinearLayout {
  @ViewById
  TextView measuredAt;

  @ViewById
  TextView fields;

  @ViewById
  TextView separator;

  public TensionItemView(Context context) {
    super(context);
  }

  public void bind(Event event, Date previousDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    measuredAt.setText(dateFormat.format(event.getMeasuredAt()));
    fields.setText(event.getFields());

    if(isNewDay(event.getMeasuredAt(), previousDate)) {
      showSeparator(event.getMeasuredAt());
    }
  }

  private boolean isNewDay(Date now, Date previous) {
    if (previous == null)
      return true;
    return (now.getDay() != previous.getDay());
  }

  private void showSeparator(Date date) {
    separator.setText(
      SimpleDateFormat.getDateInstance(DateFormat.LONG).format(date)
    );
    separator.setVisibility(VISIBLE);
  }
}

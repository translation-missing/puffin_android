package com.bluebird_tech.puffin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
  TextView value;

  @ViewById
  TextView note;

  public TensionItemView(Context context) {
    super(context);
  }

  public void bind(Event event) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    measuredAt.setText(dateFormat.format(event.getMeasuredAt()));
    value.setText(Integer.toString(Math.round(event.getValue())));
    note.setText(event.getNote());
    setCircleBackgroundColor(Math.round(event.getValue()));
  }

  private void setCircleBackgroundColor(int tension) {
    int color = getResources().getColor(R.color.tension_level_low);
    if ((30 <= tension) && (tension < 70)) {
      color = getResources().getColor(R.color.tension_level_middle);
    } else if (70 <= tension) {
      color = getResources().getColor(R.color.tension_level_high);
    }
    Drawable bg = value.getBackground();
    bg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
  }
}

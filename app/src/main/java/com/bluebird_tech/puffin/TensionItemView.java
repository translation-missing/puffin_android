package com.bluebird_tech.puffin;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluebird_tech.puffin.models.Event;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

@EViewGroup(R.layout.activity_tension_list_item)
public class TensionItemView extends LinearLayout {
  @ViewById
  TextView measuredAt;

  @ViewById
  TextView fields;

  public TensionItemView(Context context) {
    super(context);
  }

  public void bind(Event event) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    measuredAt.setText(dateFormat.format(event.getMeasuredAt()));
    fields.setText(event.getFields());
  }
}

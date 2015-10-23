package com.bluebird_tech.puffin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluebird_tech.puffin.models.Emotion;
import com.bluebird_tech.puffin.models.Event;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@EViewGroup(R.layout.activity_tension_list_item)
public class TensionItemView extends LinearLayout {
  @ViewById
  TextView measuredAt;

  @ViewById
  TextView value;

  @ViewById
  TextView note;

  @ViewById
  LinearLayout emotionLayout;

  private Context context;

  public TensionItemView(Context context) {
    super(context);
    this.context = context;
  }

  public void bind(Event event) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    measuredAt.setText(dateFormat.format(event.getMeasuredAt()));
    value.setText(Integer.toString(Math.round(event.getValue())));
    value.setTextColor(getTensionColor(Math.round(event.getValue())));
    note.setText(event.getNote());
    bindEmotions(event);
  }

  private ArrayList<Emotion> sortedEmotions(Event event) {
    ArrayList<Emotion> emotions = new ArrayList<Emotion>();

    if (event.getTags() == null)
      return emotions;

    try {
      JSONObject jobject = new JSONObject(event.getTags());
      if ((jobject != null) && (jobject.names() != null)) {
        for (int i = 0; i < jobject.names().length(); i++) {
          String name = jobject.names().getString(i);
          int value = jobject.getInt(name);
          if (value > 0) {
            emotions.add(new Emotion(name, value));
          }
        }
      }
    } catch(JSONException e) {
      e.printStackTrace();
    }

    Collections.sort(emotions);
    return emotions;
  }

  void bindEmotions(Event event) {
    emotionLayout.removeAllViewsInLayout();

    LayoutInflater layoutInflater =
      (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    for(Emotion emotion: sortedEmotions(event)) {
      TextView tv = (TextView) layoutInflater.inflate(
        R.layout.activity_tension_list_item_emotions, this, false);
      tv.setText(Utility.getStringResourceByName(context,
        "emotion_" + emotion.getName() + "_label"));

      // Drawable d = tv.getBackground().getConstantState().newDrawable().mutate();
      tv.getBackground().mutate().setAlpha(emotion.getValue() * 25);
      emotionLayout.addView(tv);
    }
  }

  private int getTensionColor(int tension) {
    int color = R.color.tension_level_low;
    if ((30 <= tension) && (tension < 70)) {
      color = R.color.tension_level_middle;
    } else if (70 <= tension) {
      color = R.color.tension_level_high;
    }
    return ContextCompat.getColor(context, color);
  }
}

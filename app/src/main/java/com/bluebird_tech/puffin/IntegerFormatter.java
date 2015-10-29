package com.bluebird_tech.puffin;


import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;

public class IntegerFormatter implements YAxisValueFormatter {
  private DecimalFormat format;

  public IntegerFormatter() {
    format = new DecimalFormat("##0"); // use one decimal
  }

  public String getFormattedValue(float value) {
    HashSet<Integer> labels =
      new HashSet<Integer>(Arrays.asList(0, 30, 70, 100));
    if (labels.contains(Math.round(value)))
      return format.format(value);
    else
      return "";
  }

  @Override
  public String getFormattedValue(float value, YAxis yAxis) {
    return getFormattedValue(value);
  }
}

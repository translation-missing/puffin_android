package com.bluebird_tech.puffin;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

public class IntegerFormatter implements ValueFormatter {
  private DecimalFormat format;

  public IntegerFormatter() {
    format = new DecimalFormat("##0"); // use one decimal
  }

  @Override
  public String getFormattedValue(float value) {
    return format.format(value);
  }
}

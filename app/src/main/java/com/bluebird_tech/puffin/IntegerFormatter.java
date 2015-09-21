package com.bluebird_tech.puffin;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by opahk on 16/09/15.
 */
public class IntegerFormatter implements ValueFormatter {
  private DecimalFormat mFormat;

  public IntegerFormatter() {
    mFormat = new DecimalFormat("##0"); // use one decimal
  }

  @Override
  public String getFormattedValue(float value) {
    return mFormat.format(value);
  }
}

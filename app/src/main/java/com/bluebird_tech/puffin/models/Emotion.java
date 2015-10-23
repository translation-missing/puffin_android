package com.bluebird_tech.puffin.models;

import com.bluebird_tech.puffin.R;

public class Emotion implements Comparable {
  private String name;
  private int value;

  public Emotion(String name, int value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public int compareTo(Object other) {
    return ((Emotion)other).getValue() - value; // desc
  }

  @Override
  public String toString() {
    return "[name = '" + name + "', value = '" + value + "']";
  }

  /* generated */
  public String getName() {
    return name;
  }

  public int getValue() {
    return value;
  }
}

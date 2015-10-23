package com.bluebird_tech.puffin;

import android.content.Context;

public class Utility {
  // http://stackoverflow.com/a/11595723
  public static String getStringResourceByName(Context ctx, String aString) {
    String packageName = ctx.getPackageName();
    int resId = ctx.getResources().getIdentifier(aString, "string", packageName);
    return ctx.getString(resId);
  }
}

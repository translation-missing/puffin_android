package com.bluebird_tech.puffin;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import java.util.Date;

/**
 * Helper class for showing and canceling tension input
 * notifications.
 * <p/>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class TensionInputNotification {
  /**
   * The unique identifier for this type of notification.
   */
  private static final String NOTIFICATION_TAG = "TensionInput";

  /**
   * Shows the notification, or updates a previously shown notification of
   * this type, with the given parameters.
   * <p/>
   * TODO: Customize this method's arguments to present relevant content in
   * the notification.
   * <p/>
   * TODO: Customize the contents of this method to tweak the behavior and
   * presentation of tension input notifications. Make
   * sure to follow the
   * <a href="https://developer.android.com/design/patterns/notifications.html">
   * Notification design guidelines</a> when doing so.
   *
   * @see #cancel(Context)
   */
  public static void notify(final Context context) {
//                            final String exampleString, final int number) {
    final Resources res = context.getResources();

    // This image is used as the notification's large icon (thumbnail).
    // TODO: Remove this if your notification has no relevant thumbnail.
    final Bitmap picture = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);

    final String title =
      res.getString(R.string.tension_input_notification_title_template);
    final String text =
      res.getString(R.string.tension_input_notification_placeholder_text_template);

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

    int notificationDefaults = 0;
    if (prefs.getBoolean("tension_input_sound", true))
      notificationDefaults |= Notification.DEFAULT_SOUND;
    if (prefs.getBoolean("tension_input_vibrate", true))
      notificationDefaults |= Notification.DEFAULT_VIBRATE;

    Intent intent = new Intent(context, StressActivity_.class);
    intent.putExtra("notificationShownAt", new Date().getTime());

    Intent delete_intent =
      new Intent(context, TensionInputNotificationDeleteReceiver_.class);
    delete_intent.putExtra("notificationShownAt", new Date().getTime());

    final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
      .setDefaults(notificationDefaults)
      .setLights(0xff0000ff, 1000, 400)
      .setSmallIcon(R.drawable.ic_notification_small_icon)
      .setContentTitle(title)
      .setContentText(text)

        // All fields below this line are optional.

        // Use a default priority (recognized on devices running Android
        // 4.1 or later)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Provide a large icon, shown with the notification in the
        // notification drawer on devices running Android 3.0 or later.
      .setLargeIcon(picture)

        // Set ticker text (preview) information for this notification.
//      .setTicker(ticker)

        // Show a number. This is useful when stacking notifications of
        // a single type.
//      .setNumber(number)

        // If this notification relates to a past or upcoming event, you
        // should set the relevant time information using the setWhen
        // method below. If this call is omitted, the notification's
        // timestamp will by set to the time at which it was shown.
        // TODO: Call setWhen if this notification relates to a past or
        // upcoming event. The sole argument to this method should be
        // the notification timestamp in milliseconds.
        //.setWhen(...)

        // Set the pending intent to be initiated when the user touches
        // the notification.
      .setContentIntent(PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT))
      .setDeleteIntent(PendingIntent.getBroadcast(
        context, 0, delete_intent, PendingIntent.FLAG_CANCEL_CURRENT))

        // Show expanded text content on devices running Android 4.1 or
        // later.
      .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(text)
        .setBigContentTitle(title)
      )
//        .setSummaryText("Dummy summary text"))

        // Automatically dismiss the notification when it is touched.
      .setAutoCancel(true);

    notify(context, builder.build());
  }

  @TargetApi(Build.VERSION_CODES.ECLAIR)
  private static void notify(final Context context, final Notification notification) {
    final NotificationManager nm = (NotificationManager) context
      .getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
      nm.notify(NOTIFICATION_TAG, 0, notification);
    } else {
      nm.notify(NOTIFICATION_TAG.hashCode(), notification);
    }
  }

  /**
   * Cancels any notifications of this type previously shown using
   * {@link #notify(Context, String, int)}.
   */
  @TargetApi(Build.VERSION_CODES.ECLAIR)
  public static void cancel(final Context context) {
    final NotificationManager nm = (NotificationManager) context
      .getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
      nm.cancel(NOTIFICATION_TAG, 0);
    } else {
      nm.cancel(NOTIFICATION_TAG.hashCode());
    }
  }
}

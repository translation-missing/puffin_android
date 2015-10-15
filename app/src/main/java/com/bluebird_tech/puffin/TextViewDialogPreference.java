package com.bluebird_tech.puffin;

import android.content.Context;
import android.preference.DialogPreference;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class TextViewDialogPreference extends DialogPreference {
  private TextView textView;

  public TextViewDialogPreference(Context context, AttributeSet attrs) {
    super(context, attrs);
    setDialogLayoutResource(R.layout.dialog_textview);
  }

  @Override
  protected View onCreateDialogView() {
    View dialogView = super.onCreateDialogView();
    textView = (TextView) dialogView.findViewById(R.id.textView);
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setText(Html.fromHtml(getDialogMessage().toString()));
    return dialogView;
  }
}

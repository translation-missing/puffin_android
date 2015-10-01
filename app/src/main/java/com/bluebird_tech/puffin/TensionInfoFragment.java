package com.bluebird_tech.puffin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by opahk on 23/09/15.
 */
public class TensionInfoFragment extends DialogFragment {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder
      .setMessage(
        Html.fromHtml(getResources().getString(R.string.dialog_message)))
      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
          // FIRE ZE MISSILES!
        }
      });
    return builder.create();
  }
}

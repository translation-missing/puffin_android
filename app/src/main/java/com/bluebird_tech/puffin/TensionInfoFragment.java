package com.bluebird_tech.puffin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by opahk on 23/09/15.
 */
public class TensionInfoFragment extends DialogFragment {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder
      .setTitle(R.string.dialog_title)
      .setMessage(R.string.dialog_message)
      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
          // FIRE ZE MISSILES!
        }
      });
    // Create the AlertDialog object and return it
    return builder.create();
  }
}

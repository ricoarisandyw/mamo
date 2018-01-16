package com.example.rico.mamo.ui.Helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by rico on 1/13/2018.
 */

public class EditTextDialog implements OnDone {

    Context context;
    String result;
    String init;
    final EditText input = new EditText(context);

    public EditTextDialog(Context context, final OnDone callback) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input Dialog");

        input.setHint("Input here");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                result = input.getText().toString();
                callback.onDone(result);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                result = "cancel";
            }
        }).create().show();
    }

    @Override
    public void onDone(String result) {

    }

    public void setInit(String init){
        this.init = init;
        input.setText(init);
        input.setSelectAllOnFocus(true);
    }
}

package com.example.rico.mamo.ui.Helper;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by rico on 1/23/2018.
 */

public class FormHelper {

    public FormHelper(){}

    public static boolean Required(EditText editText){
        if(editText.getText().toString().isEmpty()) {
            editText.setError("Required");
            return false;
        } else
            return true;
    }

    public static boolean Required(TextView textView){
        if(textView.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

    public static boolean Required(AutoCompleteTextView autoCompleteTextView){
        if(autoCompleteTextView.getText().toString().isEmpty()) {
            autoCompleteTextView.setError("Required");
            return false;
        } else
            return true;
    }

    public static boolean Required(Switch aSwitch){
        if(!aSwitch.isChecked())
            return false;
        else
            return true;
    }

    public static boolean Required(MultiAutoCompleteTextView multiAutoCompleteTextView){
        if(!multiAutoCompleteTextView.getText().toString().isEmpty()){
            multiAutoCompleteTextView.setError("Required");
            return false;
        } else
            return true;
    }
}

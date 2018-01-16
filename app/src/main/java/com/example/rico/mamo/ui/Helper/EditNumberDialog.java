package com.example.rico.mamo.ui.Helper;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rico.mamo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rico on 1/13/2018.
 */

public class EditNumberDialog{

    Context context;
    String init;
    EditText editText;

    public EditNumberDialog(Context context, final OnDone callback) {
        this.context = context;
        final Dialog dialog = new Dialog(context);
        View view = View.inflate(context, R.layout.number_dialog, null);
        dialog.setContentView(view);

        Button mOne = (Button) dialog.findViewById(R.id.one);
        Button mTwo = (Button) dialog.findViewById(R.id.two);
        Button mThree = (Button) dialog.findViewById(R.id.three);
        Button mFour = (Button) dialog.findViewById(R.id.four);
        Button mFive = (Button) dialog.findViewById(R.id.five);
        Button mSix = (Button) dialog.findViewById(R.id.six);
        Button mSeven = (Button) dialog.findViewById(R.id.seven);
        Button mEight = (Button) dialog.findViewById(R.id.eight);
        Button mNine = (Button) dialog.findViewById(R.id.nine);
        Button mZero = (Button) dialog.findViewById(R.id.zero);
        Button mC = (Button) dialog.findViewById(R.id.c);
        Button mPlusmin = (Button) dialog.findViewById(R.id.plusmin);
        Button done = (Button) dialog.findViewById(R.id.btn_dialog);
        editText = (EditText) dialog.findViewById(R.id.et_dialog);


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    editText.setSelectAllOnFocus(true);
                }
            }
        });
        mOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("1");
                }else{
                    editText.append("1");
                }

            }
        });
        mTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("2");
                }else
                    editText.append("2");
            }
        });
        mThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("3");
                }else
                editText.append("3");
            }
        });
        mFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("4");
                }else
                editText.append("4");
            }
        });
        mFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("5");
                }else
                editText.append("5");
            }
        });
        mSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("6");
                }else
                editText.append("6");
            }
        });
        mSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("7");
                }else
                editText.append("7");
            }
        });
        mEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("8");
                }else
                editText.append("8");
            }
        });
        mNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("9");
                }else
                editText.append("9");
            }
        });
        mZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("0");
                }else
                    editText.append("0");
            }
        });
        mPlusmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et = editText.getText().toString();
                if(editText.getText().toString().equalsIgnoreCase("0")){
                    editText.setText("0");
                }else
                    if(et.contains("-")){
                        StringBuilder sb = new StringBuilder(et);
                        sb.deleteCharAt(0);
                        editText.setText(sb.toString());
                    }else{
                        editText.setText("-"+et);
                    }
            }
        });
        mC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("0");
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onDone(editText.getText().toString());
//                Log.d("exce","Done with result : "+editText.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setInit(String init){
        this.init = init;
        editText.setText(init);
        editText.setSelectAllOnFocus(true);
    }
}

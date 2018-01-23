package com.example.rico.mamo.ui.statistic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.LogActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticActivity extends AppCompatActivity {

    @BindView(R.id.stat) TextView mStat;
    @BindView(R.id.tDate) TextView mDate;
    @BindView(R.id.mean) TextView mMean;

    List<LogActivity> logActivities;
    private DatePickerDialog datePickerDialog;
    DaoSession daoSession;
    Calendar calendar;
    private SimpleDateFormat dateFormatter;
    int year,day,month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.bind(this);
        daoSession = ((App)getApplication()).getDaoSession();

        try {
            logActivities = daoSession.getLogActivityDao().loadAll();
            float sumOfMoney = 0;
            for (LogActivity l : logActivities) {
                mStat.append(new SimpleDateFormat("dd/MM/yyyy").format(l.getDate())+ "\n" + l.getDescription() + "\n");
                sumOfMoney += l.getMoney();
                Log.d("Exce","Log\t"+l.getMoney());
            }
            sumOfMoney = sumOfMoney/logActivities.size();
            mMean.setText("MEAN\t:"+ sumOfMoney);
        }catch (Exception e){

        }

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pickDate();
                showDate(year, month+1, day);
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void showDate(int year, int month, int day) {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                mDate.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
}

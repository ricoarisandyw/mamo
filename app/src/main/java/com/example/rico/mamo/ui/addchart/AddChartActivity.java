package com.example.rico.mamo.ui.addchart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.Chart;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.Item;
import com.example.rico.mamo.data.db.model.ItemDao;
import com.example.rico.mamo.ui.additem.AddItemActivity;
import com.example.rico.mamo.ui.dashboard.DashboardActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddChartActivity extends AppCompatActivity {

    @BindView(R.id.add_chart_name) EditText mName;
    @BindView(R.id.add_chart_budget) EditText mBudget;
    @BindView(R.id.add_chart_description) EditText mDescription;
    @BindView(R.id.dynamic_budget) Switch mDynamic;
    @BindView(R.id.add_chart_submit) Button mSubmit;

    DaoSession daoSession;
    boolean editMode = false;
    String id_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chart);
        //init something
        ButterKnife.bind(this);
        daoSession = ((App)getApplication()).getDaoSession();
        //Edit Mode
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id_chart= null;
            } else {
                id_chart= extras.getString("id_chart");
                editMode = true;
            }
        } else {
            id_chart= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        if(editMode){
            edit();
        }

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        mBudget.setText("0");
        mBudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validate();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void submit(){
        //Validate Value
        if(mName.getText().toString().equals("")){
            Toast.makeText(getApplication(), "Name cannot be blank", Toast.LENGTH_SHORT).show();
        }else {
            Chart chart = new Chart();
            if(editMode)chart = daoSession.getChartDao().load(Long.parseLong(id_chart));
            chart.setName(mName.getText().toString());
            String bud = mBudget.getText().toString();

            if (bud.equals("0"))
                chart.setBudget(0);
            else
                chart.setBudget(Integer.parseInt(bud));

            if(mDynamic.isChecked()){
                chart.setDynamicbudget(true);
                chart.setBudget(0);
            }else{
                chart.setDynamicbudget(false);
            }

            chart.setDescription(mDescription.getText().toString());
            if(editMode) {
                chart.getId_chart();
                daoSession.getChartDao().update(chart);
            } else {
                daoSession.getChartDao().insert(chart);
            }
            //Validate
            Toast.makeText(getApplication(), "Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DashboardActivity.class));
        }
    }

    boolean validate(){
        if(mBudget.getText().toString().equals("")){
            mBudget.setText("0");
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish(); startActivity(new Intent(this,DashboardActivity.class));
    }

    void edit(){
        Chart chart = daoSession.getChartDao().load(Long.parseLong(id_chart));
        //Validate with remain data
        List<Item> itemList = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id_chart.eq(chart.getId_chart())).list();
        int sumOfItems = 0;
        for(Item i : itemList)
            sumOfItems+=(i.getPrice()*i.getQuantity());

        if(chart.getBudget()<sumOfItems){
            Toast.makeText(this,"Minimum budget must" + sumOfItems + "\nor\nEdit your items",Toast.LENGTH_SHORT).show();
        }else{
            mName.setText(chart.getName());
            mBudget.setText(chart.getBudget()+"");
            mDescription.setText(chart.getDescription());
        }
    }
}

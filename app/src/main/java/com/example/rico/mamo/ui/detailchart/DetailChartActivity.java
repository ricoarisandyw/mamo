package com.example.rico.mamo.ui.detailchart;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.Chart;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.Item;
import com.example.rico.mamo.data.db.model.ItemDao;
import com.example.rico.mamo.ui.addchart.AddChartActivity;
import com.example.rico.mamo.ui.additem.AddItemActivity;
import com.example.rico.mamo.ui.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailChartActivity extends AppCompatActivity {

    String id_chart;
    Chart chart;
    List<Item> itemList = new ArrayList<>();
    DaoSession daoSession;
    ItemAdapter itemAdapter;

    @BindView(R.id.detail_list_item) RecyclerView mRecyclerItem;
    @BindView(R.id.add_item) FloatingActionButton mFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        daoSession = ((App)getApplication()).getDaoSession();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id_chart= null;
            } else {
                id_chart= extras.getString("id_chart");
            }
        } else {
            id_chart= (String) savedInstanceState.getSerializable("id_chart");
        }

        init();

        //RecyclerItem
        itemAdapter = new ItemAdapter(this,itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerItem.setLayoutManager(mLayoutManager);
        mRecyclerItem.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();

        //AddItem
        mFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailChartActivity.this, AddItemActivity.class);
                i.putExtra("id_chart",id_chart);
                finish();
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailchart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.detail_edit) {
            edit();
        }else if(id == R.id.detail_delete){
            delete();
        } else if (id == R.id.detail_share){
            share();
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        chart = daoSession.getChartDao().load(Long.parseLong(id_chart));
        setTitle(chart.getName());
        itemList = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id_chart.eq(id_chart)).list();
    }

    public void edit(){
        finish();
        Intent i = new Intent(this, AddChartActivity.class);
        i.putExtra("id_chart",id_chart);
        startActivity(i);
    }
    public void delete(){
        daoSession.getChartDao().deleteByKey(Long.parseLong(id_chart));
        daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id_chart.eq(id_chart)).buildDelete().executeDeleteWithoutDetachingEntities();
        finish();
        startActivity(new Intent(this, DashboardActivity.class));
    }
    public void share(){

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, DashboardActivity.class));
    }
}

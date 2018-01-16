package com.example.rico.mamo.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.Account;
import com.example.rico.mamo.data.db.model.Chart;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.Item;
import com.example.rico.mamo.ui.Helper.EditNumberDialog;
import com.example.rico.mamo.ui.Helper.EditTextDialog;
import com.example.rico.mamo.ui.Helper.OnDone;
import com.example.rico.mamo.ui.addchart.AddChartActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Chart> chartList = new ArrayList<>();
    //Adapter
    ChartsAdapter chartsAdapter;

    DaoSession daoSession;

    //InitView
    @BindView(R.id.dashboard_list_chart) RecyclerView mRecycleListChart;
    @BindView(R.id.dashboard_balance) TextView mBalance;
    @BindView(R.id.remain) TextView mRemain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init Something
        ButterKnife.bind(this);
        daoSession = ((App)getApplication()).getDaoSession();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, AddChartActivity.class));
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        chartList = daoSession.getChartDao().loadAll();

        //RecyclerChart
        chartsAdapter = new ChartsAdapter(this,chartList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecycleListChart.setLayoutManager(mLayoutManager);
        mRecycleListChart.setAdapter(chartsAdapter);
        chartsAdapter.notifyDataSetChanged();

        //Account
        init();

        //

        mBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new EditNumberDialog(DashboardActivity.this, new OnDone() {
                @Override
                public void onDone(String result) {
                    Toast.makeText(DashboardActivity.this,result,Toast.LENGTH_SHORT).show();
                    Account account = daoSession.getAccountDao().load(1l);
                    account.setBalance(Integer.parseInt(result));
                    daoSession.getAccountDao().update(account);
                    init();
                }
            }).setInit(mBalance.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_account) {
            // Handle the camera action
        } else if (id == R.id.edit_account) {

        } else if (id == R.id.statistic) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void init(){
        Account account = new Account();
        if(daoSession.getAccountDao().load(1l)==null){
            //Create new Account -- First Us
            account.setName("Wallet");
            account.setBalance(0);
            daoSession.getAccountDao().insert(account);
        }else{
            account = daoSession.getAccountDao().load(1l);
        }
        //After bought all
        List<Item> items = daoSession.getItemDao().loadAll();
        int sumOfItems = 0;
        int sumOfBoughtItems = 0;
        for(Item i : items){
            sumOfItems += (i.getPrice()*(i.getQuantity()));
            sumOfBoughtItems += (i.getPrice()*(i.getBought()));
        }
        mBalance.setText((account.getBalance()-sumOfBoughtItems)+"");
        mRemain.setText((account.getBalance()-sumOfItems)+"");
    }
}

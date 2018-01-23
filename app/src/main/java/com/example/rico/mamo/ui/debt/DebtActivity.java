package com.example.rico.mamo.ui.debt;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.Debt;
import com.example.rico.mamo.ui.adddebt.AddDebtActivity;
import com.example.rico.mamo.ui.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DebtActivity extends AppCompatActivity {

    @BindView(R.id.list_debt) RecyclerView mList;
    @BindView(R.id.add_debt) FloatingActionButton mAddDebt;

    List<Debt> debtList = new ArrayList<>();
    DebtsAdapter debtsAdapter;
    DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt);
        ButterKnife.bind(this);
        daoSession = ((App)getApplication()).getDaoSession();

        debtList = daoSession.getDebtDao().loadAll();

        debtsAdapter = new DebtsAdapter(this,debtList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(debtsAdapter);
        debtsAdapter.notifyDataSetChanged();

        mAddDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(DebtActivity.this, AddDebtActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(DebtActivity.this, DashboardActivity.class));
    }
}

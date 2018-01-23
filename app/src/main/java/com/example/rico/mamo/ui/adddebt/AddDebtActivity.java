package com.example.rico.mamo.ui.adddebt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.Chart;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.Debt;
import com.example.rico.mamo.ui.dashboard.DashboardActivity;
import com.example.rico.mamo.ui.debt.DebtActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDebtActivity extends AppCompatActivity {
    @BindView(R.id.add_debt_submit) Button mAdd;
    @BindView(R.id.add_debt_name) EditText mName;
    @BindView(R.id.add_debt_description) EditText mDescription;
    @BindView(R.id.add_debt_price) EditText mPrice;

    DaoSession daoSession;
    boolean editMode;
    String id_debt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);
        ButterKnife.bind(this);
        daoSession = ((App)getApplication()).getDaoSession();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id_debt= null;
            } else {
                id_debt= extras.getString("id_debt");
                editMode = true;
            }
        } else {
            id_debt= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        if(editMode){
            edit();
        }

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    void submit(){
        if(mName.getText().toString().equals("")){
            Toast.makeText(getApplication(), "Name cannot be blank", Toast.LENGTH_SHORT).show();
        }else {
            Debt debt = new Debt();
            if(editMode)debt = daoSession.getDebtDao().load(Long.parseLong(id_debt));
            debt.setName(mName.getText().toString());
            String bud = mPrice.getText().toString();

            if (bud.equals("0"))
                debt.setBalance(0);
            else
                debt.setBalance(Integer.parseInt(bud));

            if(editMode)
                daoSession.getDebtDao().update(debt);
            else
                daoSession.getDebtDao().insert(debt);

            //Validate
            Toast.makeText(getApplication(), "Success", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, DebtActivity.class));
        }
    }

    void edit(){
        Debt debt = daoSession.getDebtDao().load(Long.parseLong(id_debt));
        mPrice.setText(debt.getName());
        mPrice.setText(debt.getBalance()+"");
    }
}

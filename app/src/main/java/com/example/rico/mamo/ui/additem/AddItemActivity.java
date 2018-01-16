package com.example.rico.mamo.ui.additem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.Chart;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.Item;
import com.example.rico.mamo.data.db.model.ItemDao;
import com.example.rico.mamo.ui.detailchart.DetailChartActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemActivity extends AppCompatActivity {

    @BindView(R.id.add_item_name) EditText mName;
    @BindView(R.id.add_item_price) EditText mPrice;
    @BindView(R.id.add_item_quantity) EditText mQuantity;
    @BindView(R.id.add_item_description) EditText mDescription;
    @BindView(R.id.dynamic_item) Switch mDynamic;
    @BindView(R.id.add_item_submit) Button mSubmit;

    String id_chart;
    boolean editMode = false;

    DaoSession daoSession;
    String id_item;
    Chart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
        daoSession = ((App)getApplication()).getDaoSession();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id_chart= null;
            } else {
                id_chart= extras.getString("id_chart");
                id_item= extras.getString("id_item");
            }
        } else {
            id_chart = (String) savedInstanceState.getSerializable("id_chart");
            id_item = (String) savedInstanceState.getSerializable("id_item");
        }
        if(id_item!=null){ editMode = true;
            edit();
        }

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        mDynamic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(AddItemActivity.this,"True",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddItemActivity.this,"False",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void submit(){
        Item item = new Item();
        if(editMode){
            item = daoSession.getItemDao().load(Long.parseLong(id_item));
        }

        if(mName.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(AddItemActivity.this,"Name cannot be blank",Toast.LENGTH_SHORT).show();
        }else{
            item.setName(mName.getText().toString());
            item.setBought(0);
            if(mDescription.getText().toString().equalsIgnoreCase("")){
                item.setDescription("");
            }else{
                item.setDescription(mDescription.getText().toString());
            }
            item.setId_chart(Long.parseLong(id_chart));
            if(mPrice.getText().toString().equalsIgnoreCase("")){
                //Default
                item.setPrice(0);
            }else{
                item.setPrice(Integer.parseInt(mPrice.getText().toString()));
            }
            if(mDynamic.isChecked()){
                //Default
                item.setDynamicQuantity(true);
                item.setQuantity(0);
            }else{
                item.setDynamicQuantity(false);
                item.setQuantity(Integer.parseInt(mQuantity.getText().toString()));
            }

            chart = daoSession.getChartDao().load(Long.parseLong(id_chart));

            List<Item> items = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id_chart.eq(id_chart)).list();
            int SumOfItemsNow = 0;
            for(Item i: items)
                SumOfItemsNow += (i.getPrice()*i.getQuantity());

            if(chart.getBudget()>=((item.getPrice()*item.getQuantity())+SumOfItemsNow)
                    ||chart.getDynamicbudget()){
                if(editMode){
                    daoSession.getItemDao().update(item);
                } else {
                    daoSession.getItemDao().insert(item);
                }
                finish();
                Intent i = new Intent(this, DetailChartActivity.class);
                i.putExtra("id_chart",id_chart);
                startActivity(i);
            }else{
                Toast.makeText(this,"Cannot bigger than budget\nRemain :"+(chart.getBudget()-SumOfItemsNow),Toast.LENGTH_SHORT).show();
            }



        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, DetailChartActivity.class);
        i.putExtra("id_chart",id_chart);
        finish();
        startActivity(i);
        super.onBackPressed();
    }

    void edit(){
        Item item = daoSession.getItemDao().load(Long.parseLong(id_item));
        mName.setText(item.getName());
        mDescription.setText(item.getDescription());
        mDynamic.setChecked(item.getDynamicQuantity());
        mPrice.setText(item.getPrice()+"");
        mQuantity.setText(item.getQuantity()+"");
    }
}

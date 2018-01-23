package com.example.rico.mamo.ui.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.example.rico.mamo.ui.Helper.FormHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFromWidgetActivity extends AppCompatActivity {
    @BindView(R.id.add_widget_chart)
    AutoCompleteTextView mChart;
    @BindView(R.id.add_widget_name)
    AutoCompleteTextView mItem;
    @BindView(R.id.add_widget_price)
    EditText mPrice;
    @BindView(R.id.add_widget_quantity)
    EditText mQuantity;
    @BindView(R.id.add_widget_description)
    EditText mDescription;
    @BindView(R.id.dynamic_widget)
    Switch mSwitch;
    @BindView(R.id.add_widget_submit)
    Button mSubmit;


    ArrayAdapter<String> chartAdapter;
    ArrayAdapter<String> itemAdapter;

    DaoSession daoSession;

    String mode = "";
    List<Chart> charts;
    List<Item> items;
    Long idChart;
    int indexOfSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_widget);
        ButterKnife.bind(this);
        daoSession = ((App) getApplication()).getDaoSession();

        init();

        mChart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fSetAdapterForItem(mChart.getText() + "");
            }
        });
        mItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fUpdateForm(mItem.getText() + "");
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fSubmit();
            }
        });
    }

    public void init() {
        charts = daoSession.getChartDao().loadAll();
        List<String> chartName = new ArrayList<>();
        for (Chart c : charts) {
            Log.d("Exce", "Name chart:" + c.getName());
            chartName.add(c.getName());
        }
        String NAMES[] = chartName.toArray(new String[0]);
        chartAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, NAMES);
        mChart.setAdapter(chartAdapter);
    }

    void fSetAdapterForItem(String selectedItem) {
        Chart selectedCharts = new Chart();
        for (int i = 0; i < charts.size(); i++) {
            if (charts.get(i).getName().equalsIgnoreCase(selectedItem)) {
                selectedCharts = charts.get(i);
                indexOfSelected = i;
            }
        }

        idChart = selectedCharts.getId_chart();
        items = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id_chart.eq(selectedCharts.getId_chart())).list();
        List<String> itemName = new ArrayList<>();
        for (Item c : items) {
            Log.d("Exce", "Name item:" + c.getName());
            itemName.add(c.getName());
        }
        String[] NAMES = itemName.toArray(new String[0]);
        itemAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, NAMES);
        mItem.setAdapter(itemAdapter);
    }

    void fUpdateForm(String text) {
        Item item = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Name.eq(text)).unique();
        mQuantity.setText(item.getQuantity() + "");
        mDescription.setText(item.getDescription());
        mPrice.setText(item.getPrice() + "");
        mSwitch.setChecked(item.getDynamicQuantity());
    }


    void fSubmit() {
        if ( FormHelper.Required(mChart) && FormHelper.Required(mItem) && FormHelper.Required(mPrice) && (FormHelper.Required(mSwitch)|| FormHelper.Required(mQuantity))) {
            Item item = new Item();

            Chart chart = new Chart();

            if (charts.get(indexOfSelected).getName().equalsIgnoreCase(mChart.getText().toString()) && idChart != null) {
                item.setId_chart(idChart);
            } else {
                chart.setName(mChart.getText() + "");
                chart.setDynamicbudget(true);
                chart.setDescription("");
                chart.setBudget(0);
                idChart = daoSession.getChartDao().insert(chart);
                item.setId_chart(idChart);
            }

            item.setName(mItem.getText() + "");
            item.setBought(1);
            item.setQuantity(Integer.parseInt(mQuantity.getText() + ""));
            item.setDescription(mDescription.getText() + "");
            item.setPrice(Integer.parseInt(mPrice.getText() + ""));

            daoSession.getItemDao().insert(item);
        }else{
            Toast.makeText(this,"Something missing,fill the blank",Toast.LENGTH_SHORT).show();
        }
    }
}

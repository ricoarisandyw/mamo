package com.example.rico.mamo.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rico.mamo.ui.detailchart.DetailChartActivity;
import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.model.Chart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rico on 1/8/2018.
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.MyViewHolder> {

    private List<Chart> chartList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chart_name) TextView mName;
        @BindView(R.id.chart_budget) TextView mBudget;
        @BindView(R.id.chart_bought) TextView mBought;
        @BindView(R.id.chart_description) TextView mDescription;
        @BindView(R.id.chart) LinearLayout mLayout;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Chart chart = chartList.get(position);
        holder.mName.setText(chart.getName());
        holder.mDescription.setText(chart.getDescription());
        if(chart.getDynamicbudget()){
            holder.mBudget.setText("Dynamic");
        }else{
            holder.mBudget.setText(chart.getBudget()+"");
        }

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)context).finish();
                Intent i = new Intent(context, DetailChartActivity.class);
                i.putExtra("id_chart",chart.getId_chart().toString());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(chartList==null)return 0;
        return chartList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chart, parent, false);

        return new MyViewHolder(itemView);
    }

    public ChartsAdapter(Context context, List<Chart> chartList) {
        this.chartList = chartList;
        this.context = context;
    }
}

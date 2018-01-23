package com.example.rico.mamo.ui.debt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.model.Chart;
import com.example.rico.mamo.data.db.model.Debt;
import com.example.rico.mamo.ui.detailchart.DetailChartActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rico on 1/8/2018.
 */

public class DebtsAdapter extends RecyclerView.Adapter<DebtsAdapter.MyViewHolder> {

    private List<Debt> debtList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_debt) TextView mName;
        @BindView(R.id.debt_price) TextView mPrice;
        @BindView(R.id.debt_description) TextView mDescription;
        @BindView(R.id.debt) LinearLayout mLayout;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Debt debt = debtList.get(position);
        holder.mName.setText(debt.getName());
        holder.mPrice.setText(debt.getBalance()+"");
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(debtList ==null)return 0;
        return debtList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chart, parent, false);

        return new MyViewHolder(itemView);
    }

    public DebtsAdapter(Context context, List<Debt> debtList) {
        this.debtList = debtList;
        this.context = context;
    }
}

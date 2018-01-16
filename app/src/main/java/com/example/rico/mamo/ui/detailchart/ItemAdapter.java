package com.example.rico.mamo.ui.detailchart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.Account;
import com.example.rico.mamo.data.db.model.Chart;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.Item;
import com.example.rico.mamo.data.db.model.LogActivity;
import com.example.rico.mamo.data.db.model.LogActivityDao;
import com.example.rico.mamo.ui.Helper.EditNumberDialog;
import com.example.rico.mamo.ui.Helper.OnDone;
import com.example.rico.mamo.ui.additem.AddItemActivity;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rico on 1/8/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Item> itemList;
    Context context;
    DaoSession daoSession;
    Chart chart;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name) TextView mName;
        @BindView(R.id.item_price) TextView mPrice;
        @BindView(R.id.item_quantity) TextView mQuantity;
        @BindView(R.id.item_description) TextView mDescription;
        @BindView(R.id.item) LinearLayout mLayout;
        @BindView(R.id.item_menu) ImageView mMenu;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Item item = itemList.get(position);
        holder.mName.setText(item.getName());
        holder.mDescription.setText(item.getDescription());
        holder.mPrice.setText(item.getPrice()+"");
        if(item.isDynamicQuantity())
            holder.mQuantity.setText(item.getBought()+" bought");
        else
            holder.mQuantity.setText(item.getBought()+"/"+item.getQuantity());

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.mMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.item, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        final int id = menuItem.getItemId();
                        if(id==R.id.item_menu_delete){
                            String[] strings = {"Wallet not back","Wallet back"};
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Delete Type")
                                    .setItems(strings, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // The 'which' argument contains the index position
                                            // of the selected item
                                            if(which==0){
                                                //Wallet not back
                                                Account account =   daoSession.getAccountDao().load(1l);
                                                account.setBalance(
                                                        account.getBalance() - (item.getPrice()*item.getBought()));
                                                daoSession.getItemDao().deleteByKey(item.getId_item());

                                                ((AppCompatActivity)context).finish();
                                                Intent i = new Intent(context,DetailChartActivity.class);
                                                i.putExtra("id_chart",item.getId_chart()+"");
                                                context.startActivity(i);
                                            }else{
                                                //Wallet back
                                                daoSession.getLogActivityDao().queryBuilder().where(LogActivityDao.Properties.Id_item.eq(item.getId_item())).buildDelete().executeDeleteWithoutDetachingEntities();
                                                daoSession.getItemDao().deleteByKey(item.getId_item());

                                                ((AppCompatActivity)context).finish();
                                                Intent i = new Intent(context,DetailChartActivity.class);
                                                i.putExtra("id_chart",item.getId_chart()+"");
                                                context.startActivity(i);
                                            }
                                        }
                                    }).create().show();
                        }
                        else if(id==R.id.item_menu_edit){
                            ((AppCompatActivity)context).finish();
                            Intent i = new Intent(context,AddItemActivity.class);
                            i.putExtra("id_item",item.getId_item()+"");
                            i.putExtra("id_chart",item.getId_chart()+"");
                            context.startActivity(i);
                        }else if(id==R.id.item_menu_bought){
                            //When item bought
                            new EditNumberDialog(context, new OnDone() {
                                @Override
                                public void onDone(String result) {
                                    //Condition
                                    //1. Bought cannot bigger than quantity
                                    //2. If dynamic quantity, price of dynamic quantity cannot bigger than budget
                                    int res = Integer.parseInt(result);
                                    int sumOfItems = 0;
                                    for(Item i : itemList) {
                                        if(i.isDynamicQuantity())
                                            sumOfItems += (i.getPrice() * i.getBought());
                                        else
                                            sumOfItems += (i.getPrice() * i.getQuantity());
                                    }

                                    if(item.getQuantity()>=(item.getBought()+res) ||
                                            (item.isDynamicQuantity()&&
                                                    chart.getBudget()>=(sumOfItems+(item.getPrice()*res)))){
                                        item.setBought(item.getBought() + (Integer.parseInt(result)));
                                        daoSession.getItemDao().update(item);

                                        LogActivity logActivity = new LogActivity();
                                        logActivity.setDate(new Date());
                                        logActivity.setDescription("Bought "+res+" "+item.getName()+" cost"+(res*item.getPrice()));
                                        logActivity.setId_item(item.getId_item());
                                        daoSession.getLogActivityDao().insert(logActivity);

                                        ((AppCompatActivity) context).finish();
                                        Intent i = new Intent(context, DetailChartActivity.class);
                                        i.putExtra("id_chart", item.getId_chart() + "");
                                        context.startActivity(i);
                                    }else{
                                        Toast.makeText(context,"Cannot bigger than Quantity or Budget",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(itemList ==null)return 0;
        return itemList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(itemView);
    }

    public ItemAdapter(Context context, List<Item> itemList) {
        this.itemList = itemList;
        this.context = context;
        daoSession = ((App)context.getApplicationContext()).getDaoSession();
        try {
            chart = daoSession.getChartDao().load(itemList.get(0).getId_chart());
        }catch (Exception e){

        }
    }
}

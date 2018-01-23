package com.example.rico.mamo.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.rico.mamo.R;
import com.example.rico.mamo.data.db.App;
import com.example.rico.mamo.data.db.model.Account;
import com.example.rico.mamo.data.db.model.DaoSession;
import com.example.rico.mamo.data.db.model.Item;

import java.util.List;

/**
 * Created by rico on 1/23/2018.
 */

public class MamoAppWidgetProvider extends AppWidgetProvider {

    DaoSession daoSession;
    Account account;
    int sumOfBoughtItems = 0;

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        daoSession = ((App)context.getApplicationContext()).getDaoSession();

        init();

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Bundle extras = new Bundle();
            extras.putString("mode", "add");
            Intent intent = new Intent(context, AddFromWidgetActivity.class);
            intent.putExtras(extras);
            PendingIntent AddIntent = PendingIntent.getActivity(context, 0, intent, 0);

            Intent intentMin = new Intent(context, AddFromWidgetActivity.class);
            Bundle extramin = new Bundle();
            extramin.putString("mode", "min");
            intentMin.putExtras(extramin);
            PendingIntent MinIntent = PendingIntent.getActivity(context, 0, intentMin, 0);

            Intent dashboardIntent = new Intent(context, AddFromWidgetActivity.class);
            PendingIntent dashIntent = PendingIntent.getActivity(context, 0, dashboardIntent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mamo_widget);

            int balanace = (account.getBalance()-sumOfBoughtItems);
            Log.d("Exce","widget updated");
            Log.d("Exce","balance :" +balanace);
            views.setTextViewText(R.id.balance_widget,balanace+"");
            views.setOnClickPendingIntent(R.id.balance_widget, dashIntent);
            views.setOnClickPendingIntent(R.id.widget_add, AddIntent);
            views.setOnClickPendingIntent(R.id.widget_min, MinIntent);


            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public void init(){
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
        for(Item i : items){
            sumOfBoughtItems += (i.getPrice()*(i.getBought()));
        }
    }
}

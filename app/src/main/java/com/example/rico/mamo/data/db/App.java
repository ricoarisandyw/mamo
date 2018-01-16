package com.example.rico.mamo.data.db;


import android.app.Application;

import com.example.rico.mamo.data.db.model.DaoMaster;
import com.example.rico.mamo.data.db.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by rico on 1/8/2018.
 */

public class App extends Application {

    DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        mDaoSession = new DaoMaster(new DbOpenHelper(this,"mamo3").getWritableDb()).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}

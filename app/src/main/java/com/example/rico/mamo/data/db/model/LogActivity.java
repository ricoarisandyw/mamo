package com.example.rico.mamo.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by rico on 1/16/2018.
 */
@Entity
public class LogActivity {
    @Id
    Long id_log;

    Long id_item;
    Date date;
    int money;
    String description;

    @Generated(hash = 2122648884)
    public LogActivity(Long id_log, Long id_item, Date date, int money,
            String description) {
        this.id_log = id_log;
        this.id_item = id_item;
        this.date = date;
        this.money = money;
        this.description = description;
    }

    @Generated(hash = 1250897767)
    public LogActivity() {
    }

    public Long getId_log() {
        return id_log;
    }

    public void setId_log(Long id_log) {
        this.id_log = id_log;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId_item() {
        return this.id_item;
    }

    public void setId_item(Long id_item) {
        this.id_item = id_item;
    }
}

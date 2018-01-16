package com.example.rico.mamo.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by rico on 1/8/2018.
 */
@Entity
public class Account {
    @Id
    Long id_account;

    String name;
    int balance;

    @Generated(hash = 1290804779)
    public Account(Long id_account, String name, int balance) {
        this.id_account = id_account;
        this.name = name;
        this.balance = balance;
    }
    @Generated(hash = 882125521)
    public Account() {
    }

    public Long getId_account() {
        return this.id_account;
    }
    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getBalance() {
        return this.balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }


}

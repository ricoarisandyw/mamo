package com.example.rico.mamo.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by rico on 1/17/2018.
 */
@Entity
public class Debt {
    @Id
    Long id_debt;

    String name;
    int balance;

    @Generated(hash = 1804674592)
    public Debt(Long id_debt, String name, int balance) {
        this.id_debt = id_debt;
        this.name = name;
        this.balance = balance;
    }

    @Generated(hash = 488411483)
    public Debt() {
    }

    public Long getId_debt() {
        return id_debt;
    }

    public void setId_debt(Long id_debt) {
        this.id_debt = id_debt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}

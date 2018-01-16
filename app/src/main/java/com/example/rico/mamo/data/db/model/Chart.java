package com.example.rico.mamo.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by rico on 1/8/2018.
 */
@Entity
public class Chart {
    @Id(autoincrement = true)
    Long id_chart;

    String name;
    int budget = 0;
    boolean dynamicbudget;
    String description;
    @Generated(hash = 737591306)
    public Chart(Long id_chart, String name, int budget, boolean dynamicbudget,
            String description) {
        this.id_chart = id_chart;
        this.name = name;
        this.budget = budget;
        this.dynamicbudget = dynamicbudget;
        this.description = description;
    }
    @Generated(hash = 845533582)
    public Chart() {
    }
    public Long getId_chart() {
        return this.id_chart;
    }
    public void setId_chart(Long id_chart) {
        this.id_chart = id_chart;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getBudget() {
        return this.budget;
    }
    public void setBudget(int budget) {
        this.budget = budget;
    }
    public boolean getDynamicbudget() {
        return this.dynamicbudget;
    }
    public void setDynamicbudget(boolean dynamicbudget) {
        this.dynamicbudget = dynamicbudget;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}

package com.example.rico.mamo.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by rico on 1/8/2018.
 */
@Entity
public class Item {
    @Id(autoincrement = true)
    Long id_item;

    String name;
    Long id_chart;
    int price;
    boolean dynamicQuantity;
    String description;
    int quantity;
    int bought;

    @Generated(hash = 1602146921)
    public Item(Long id_item, String name, Long id_chart, int price,
            boolean dynamicQuantity, String description, int quantity, int bought) {
        this.id_item = id_item;
        this.name = name;
        this.id_chart = id_chart;
        this.price = price;
        this.dynamicQuantity = dynamicQuantity;
        this.description = description;
        this.quantity = quantity;
        this.bought = bought;
    }

    @Generated(hash = 1470900980)
    public Item() {
    }

    public Long getId_chart() {
        return id_chart;
    }

    public void setId_chart(Long id_chart) {
        this.id_chart = id_chart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId_item() {
        return id_item;
    }

    public void setId_item(Long id_item) {
        this.id_item = id_item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isDynamicQuantity() {
        return dynamicQuantity;
    }

    public void setDynamicQuantity(boolean dynamicQuantity) {
        this.dynamicQuantity = dynamicQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public boolean getDynamicQuantity() {
        return this.dynamicQuantity;
    }
}

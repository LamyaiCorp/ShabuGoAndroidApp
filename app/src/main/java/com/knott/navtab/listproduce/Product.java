package com.knott.navtab.listproduce;

import java.io.Serializable;

public class Product implements Serializable {
    final int id;
    final String name;
    final int price;
    final int quantity;
    final String img;

    public Product(int id, String name, int price, int quantity, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.img = img;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImg() {
        return img;
    }
}

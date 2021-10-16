package com.company.privan2;

import android.widget.ImageView;

public class Sweat {
    //Модель данных товара
    private String name,price;
    private String image;
    private String id;

    public Sweat(){

    }

    public Sweat(String id ,String name, String price, String image) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

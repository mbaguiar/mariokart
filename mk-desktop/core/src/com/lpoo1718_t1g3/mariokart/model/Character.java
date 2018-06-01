package com.lpoo1718_t1g3.mariokart.model;

import java.io.Serializable;

public class Character implements Serializable {

    private int id;

    private String name;
    private String kart;

    private boolean available = true;

    public Character(String name) {
        this.name = name;
    }

    public Character(String name, String kart) {
        this(name);
        this.kart = kart;
    }

    public String getName() {
        return this.name;
    }

    public String getFileName() {
        return kart;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
package com.lpoo1718_t1g3.mariokart.model;

import java.io.Serializable;

public class Character implements Serializable {

    private String name;
    private boolean available = true;

    public Character(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
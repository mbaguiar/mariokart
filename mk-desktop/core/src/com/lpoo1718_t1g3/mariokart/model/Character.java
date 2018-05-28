package com.lpoo1718_t1g3.mariokart.model;

public class Character {

    private int id;

    private String name;
    private String kart;

    public Character(String name){
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

}
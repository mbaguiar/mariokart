package com.lpoo1718_t1g3.mariokart.Model.entities;

public class BananaModel extends EntityModel {

    public static final float WIDTH = 16;
    public static final float HEIGHT = 16;

    public boolean toDelete;

    public BananaModel(float x, float y, float rotation) {
        super(x, y, rotation);
        toDelete = false;
    }

    public void setToDelete() {
        this.toDelete = true;
    }

    public boolean isToDelete() {
        return toDelete;
    }
}

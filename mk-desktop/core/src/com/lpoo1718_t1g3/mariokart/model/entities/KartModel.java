package com.lpoo1718_t1g3.mariokart.model.entities;

import com.lpoo1718_t1g3.mariokart.model.GameModel;

import java.util.Random;

public class KartModel extends EntityModel {

    private GameModel.object_type object;
    private boolean collision;

    public KartModel(float x, float y, float rotation) {
        super(x, y, rotation);
        object = null;
        collision = true;
    }

    public void generateObject() {
        if (object == null) {
            Random rand = new Random();
            int n = rand.nextInt(2);
            object = GameModel.object_type.values()[n];
            System.out.println(object);
        }
    }

    public GameModel.object_type getObject() {
        if (object == null) {
            System.out.println("Object is null");
            return null;
        }
        System.out.println("Released object " + object);
        int obj = object.ordinal();
        object = null;
        return GameModel.object_type.values()[obj];
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}

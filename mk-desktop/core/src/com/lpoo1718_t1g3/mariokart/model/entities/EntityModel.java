package com.lpoo1718_t1g3.mariokart.model.entities;

import com.badlogic.gdx.physics.box2d.World;

abstract class EntityModel {


    private float x;
    private float y;
    private float rotation;

    public EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

}

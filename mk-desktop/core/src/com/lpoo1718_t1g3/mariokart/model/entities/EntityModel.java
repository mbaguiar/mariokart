package com.lpoo1718_t1g3.mariokart.model.entities;

/**
 * Class that represents an entity of the game
 */
public abstract class EntityModel {

    private float x;
    private float y;
    private float rotation;

    /**
     * Constructs an entity in the given coordinates with the given rotation
     * @param x starting x position
     * @param y starting y position
     * @param rotation starting angle rotation
     */
    public EntityModel(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * Returns current x coordinate
     * @return x coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Returns current y coordinate
     * @return y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Returns current rotation
     * @return rotation angle
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets entity position to the given coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets entity rotation to the given angle
     * @param rotation angle
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

}

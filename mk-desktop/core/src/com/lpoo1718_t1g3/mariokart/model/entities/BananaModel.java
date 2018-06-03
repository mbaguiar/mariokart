package com.lpoo1718_t1g3.mariokart.model.entities;

/**
 * Class that represents a Banana in the game
 * @see EntityModel
 */
public class BananaModel extends EntityModel {

    public static final float WIDTH = 16;
    public static final float HEIGHT = 16;

    private boolean toDelete;

    /**
     * Constructs a BananaModel in the given coordinates and with the given rotation
     * @param x Starting x coordinate
     * @param y Starting y coordinate
     * @param rotation rotation angle
     */
    public BananaModel(float x, float y, float rotation) {
        super(x, y, rotation);
        toDelete = false;
    }

    /**
     * Sets BananaModel to be deleted
     */
    public void setToDelete() {
        this.toDelete = true;
    }

    /**
     * Checks if BananaModel is to be deleted
     * @return Returns true if it is to be deleted and false otherwise
     */
    public boolean isToDelete() {
        return toDelete;
    }
}

package com.lpoo1718_t1g3.mariokart.model.entities;

/**
 * Class that represents a Fake Mystery Box in the game
 * @see EntityModel
 */
public class FakeMysteryBoxModel extends EntityModel {

    public static final float WIDTH = 16;
    public static final float HEIGHT = 16;

    private boolean toDelete;

    /**
     * Constructs a FakeMysteryBoxModel in the given coordinates and with the given rotation
     * @param x Starting x coordinate
     * @param y Starting y coordinate
     * @param rotation rotation angle
     */
    public FakeMysteryBoxModel(float x, float y, float rotation) {
        super(x, y, rotation);
        this.toDelete = false;
    }

    /**
     * Sets FakeMysteryBox to be deleted
     */
    public void setToDelete() {
        this.toDelete = true;
    }

    /**
     * Checks if the FakeMysteryBox is to be deleted
     * @return Returns true if it is to be deleted and false otherwise
     */
    public boolean isToDelete() {
        return toDelete;
    }
}

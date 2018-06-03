package com.lpoo1718_t1g3.mariokart.model.entities;

/**
 * Class that represents a Finish Line of a Track in the Game
 * @see EntityModel
 */
public class FinishLineModel extends EntityModel {

    public static final float WIDTH = 10;
    public static final float HEIGHT = 84;

    /**
     * Constructs a FinishLineModel in the given coordinates and with the given rotation
     * @param x Starting x coordinate
     * @param y Starting y coordinate
     * @param rotation rotation angle
     */
    public FinishLineModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }
}

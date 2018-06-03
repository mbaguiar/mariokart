package com.lpoo1718_t1g3.mariokart.model.entities;

import java.util.ArrayList;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;

/**
 * Class that represents a Track in the game
 * @see EntityModel
 */
public class TrackModel extends EntityModel {

    private ArrayList<MysteryBoxModel> boxes;
    private ArrayList<EntityModel> objects;
    private FinishLineModel finishLineModel;

    public float xStartPosition = 530 * PIXEL_TO_METER;
    public float yStartPosition = 90 * PIXEL_TO_METER;
    public float incStartPosition = 50 * PIXEL_TO_METER;
    public static final int LAPS = 3;

    /**
     * Constructs a TrackModel in the given coordinates and with the given rotation
     * @param x Starting x coordinate
     * @param y Starting y coordinate
     * @param rotation rotation angle
     */
    public TrackModel(float x, float y, float rotation) {
        super(x, y, rotation);
        boxes = new ArrayList<MysteryBoxModel>();
        objects = new ArrayList<EntityModel>();
        finishLineModel = new FinishLineModel(495 * PIXEL_TO_METER, 90 * PIXEL_TO_METER, 0);
    }

    /**
     * Adds MysteryBox to the track
     * @param box box to be added
     */
    public void addBox(MysteryBoxModel box) {
        boxes.add(box);
    }

    /**
     * Gets all the MysteryBoxes in the track
     * @return mystery boxes
     */
    public ArrayList<MysteryBoxModel> getBoxes() {
        return boxes;
    }

    /**
     * Adds object to the track
     * @param model object to be added
     */
    public void addObject(EntityModel model) {
        objects.add(model);
    }

    /**
     * Gets all the objects currently in the track
     * @return objects
     */
    public ArrayList<EntityModel> getObjects() {
        return objects;
    }

    /**
     * Gets the track finish line
     * @return track finish line model
     */
    public FinishLineModel getFinishLineModel() {
        return finishLineModel;
    }

}

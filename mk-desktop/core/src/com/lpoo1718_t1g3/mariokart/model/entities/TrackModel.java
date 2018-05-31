package com.lpoo1718_t1g3.mariokart.model.entities;

import java.util.ArrayList;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;

public class TrackModel extends EntityModel {

    ArrayList<MysteryBoxModel> boxes;
    ArrayList<EntityModel> objects;
    FinishLineModel finishLineModel;

    public TrackModel(float x, float y, float rotation) {
        super(x, y, rotation);
        boxes = new ArrayList<MysteryBoxModel>();
        objects = new ArrayList<EntityModel>();
        finishLineModel = new FinishLineModel(495 * PIXEL_TO_METER, 90 * PIXEL_TO_METER, 0);
    }

    public float xStartPosition = 530 * PIXEL_TO_METER;
    public float yStartPosition = 90 * PIXEL_TO_METER;
    public float incStartPosition = 50 * PIXEL_TO_METER;

    public void addBox(MysteryBoxModel box) {
        boxes.add(box);
    }

    public ArrayList<MysteryBoxModel> getBoxes() {
        return boxes;
    }

    public void addObject(EntityModel model) {
        objects.add(model);
    }

    public ArrayList<EntityModel> getObjects() {
        return objects;
    }

    public FinishLineModel getFinishLineModel() {
        return finishLineModel;
    }
}

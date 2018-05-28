package com.lpoo1718_t1g3.mariokart.model.entities;

import java.util.ArrayList;

public class TrackModel extends EntityModel {

    ArrayList<MysteryBoxModel> boxes;

    public TrackModel(float x, float y, float rotation) {
        super(x, y, rotation);
        boxes = new ArrayList<MysteryBoxModel>();
    }

    public void addBox(MysteryBoxModel box) {
        boxes.add(box);
    }

    public ArrayList<MysteryBoxModel> getBoxes() {
        return boxes;
    }
}

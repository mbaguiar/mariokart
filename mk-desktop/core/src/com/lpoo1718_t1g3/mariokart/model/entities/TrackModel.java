package com.lpoo1718_t1g3.mariokart.Model.entities;

import java.util.ArrayList;

public class TrackModel extends EntityModel {

    ArrayList<MysteryBoxModel> boxes;
    ArrayList<EntityModel> objects;

    public TrackModel(float x, float y, float rotation) {
        super(x, y, rotation);
        boxes = new ArrayList<MysteryBoxModel>();
        objects = new ArrayList<EntityModel>();
    }

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
}

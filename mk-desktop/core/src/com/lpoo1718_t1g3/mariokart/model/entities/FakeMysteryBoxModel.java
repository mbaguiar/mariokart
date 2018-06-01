package com.lpoo1718_t1g3.mariokart.model.entities;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;

public class FakeMysteryBoxModel extends EntityModel {

    public static final float WIDTH = 16;
    public static final float HEIGHT = 16;

    private boolean toDelete;

    public FakeMysteryBoxModel(float x, float y, float rotation) {
        super(x, y, rotation);
        this.toDelete = false;
    }

    public void setToDelete() {
        this.toDelete = true;
    }

    public boolean isToDelete() {
        return toDelete;
    }
}

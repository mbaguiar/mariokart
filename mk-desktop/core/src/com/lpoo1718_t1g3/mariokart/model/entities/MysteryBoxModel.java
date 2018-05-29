package com.lpoo1718_t1g3.mariokart.model.entities;

public class MysteryBoxModel extends EntityModel {

    private boolean enable;

    public MysteryBoxModel(float x, float y, float rotation) {
        super(x, y, rotation);
        this.enable = true;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

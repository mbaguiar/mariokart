package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public class TrackPart extends EntityModel {

    private boolean main;
    private boolean back;
    private boolean divisions;

    public TrackPart(boolean main, boolean back, boolean divisions) {
        super(34, 49, 0);
        this.main = main;
        this.back = back;
        this.divisions = divisions;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public void setDivisions(boolean divisions) {
        this.divisions = divisions;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public boolean isBack() {
        return back;
    }

    public boolean isDivisions() {
        return divisions;
    }

    public boolean isMain() {
        return main;
    }
}

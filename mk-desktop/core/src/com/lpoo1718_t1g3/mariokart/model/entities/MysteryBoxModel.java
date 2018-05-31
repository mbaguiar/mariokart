package com.lpoo1718_t1g3.mariokart.model.entities;

import java.util.Timer;
import java.util.TimerTask;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;


public class MysteryBoxModel extends EntityModel {

    public static final float WIDTH = 16;
    public static final float HEIGHT = 16;

    private boolean enable;

    public MysteryBoxModel(float x, float y, float rotation) {
        super(x * PIXEL_TO_METER, y * PIXEL_TO_METER, rotation);
        this.enable = true;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void enable() {
        setEnable(true);
    }

    public void disable() {
        setEnable(false);
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        enable();
                    }
                }, 3000
        );
    }
}

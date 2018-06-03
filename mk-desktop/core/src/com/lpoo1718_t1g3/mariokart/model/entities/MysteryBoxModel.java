package com.lpoo1718_t1g3.mariokart.model.entities;

import java.util.Timer;
import java.util.TimerTask;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;

/**
 * Class that represents a Mystery Box in the game
 * @see EntityModel
 */
public class MysteryBoxModel extends EntityModel {

    public static final float WIDTH = 16;
    public static final float HEIGHT = 16;

    private boolean enable;

    /**
     * Constructs a MysteryBoxModel in the given coordinates and with given angle
     * @param x Starting x coordinate
     * @param y Starting y coordinate
     * @param rotation rotation angle
     */
    public MysteryBoxModel(float x, float y, float rotation) {
        super(x * PIXEL_TO_METER, y * PIXEL_TO_METER, rotation);
        this.enable = true;
    }

    /**
     * Checks if the Box is enabled
     * @return Returns true if it is enabled and false otherwise
     */
    public boolean isEnable() {
        return enable;
    }

    private void setEnable(boolean enable) {
        this.enable = enable;
    }

    private void enable() {
        setEnable(true);
    }

    /**
     * Disables box for one and a half seconds
     */
    public void disable() {
        setEnable(false);
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        enable();
                    }
                }, 1500
        );
    }
}

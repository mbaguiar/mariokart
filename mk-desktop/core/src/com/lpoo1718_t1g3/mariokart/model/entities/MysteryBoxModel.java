package com.lpoo1718_t1g3.mariokart.model.entities;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

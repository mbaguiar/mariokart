package com.lpoo1718_t1g3.mariokart.model.entities;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import java.util.Random;

import static com.lpoo1718_t1g3.mariokart.model.entities.KartModel.speed_type.NORMAL;

public class KartModel extends EntityModel {

    public final static float POWER = 100000;
    public final static float MINSTEERANGLE = 30;
    public final static float MAXSTEERANGLE = 45;
    public final static float MAXSPEED = 500;
    public static final float WIDTH = 24;
    public static final float HEIGHT = 28;

    public final static float POWER_HIGH = 100000;
    public final static float MAXSPEED_HIGH = 500;

    public final static float POWER_LOW = 1000;
    public final static float MAXSPEED_LOW = 100;

    public enum speed_type {NORMAL, LOW, HIGH}

    public speed_type speed = NORMAL;

    private GameModel.object_type object;
    private boolean collision;
    boolean isColliding = false;

    private int playerId;

    private int laps = -1;

    public KartModel(float x, float y, float rotation, int playerId) {
        super(x, y, rotation);
        object = null;
        collision = true;
    }

    public boolean isColliding() {
        return isColliding;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }

    public void generateObject() {

        if (object == null) {
            Random rand = new Random();
            int n = rand.nextInt(2);
            object = GameModel.object_type.values()[n];
            System.out.println(object);
        }
    }

    public GameModel.object_type getObject() {
        if (object == null) {
            System.out.println("Object is null");
            return null;
        }
        System.out.println("Released object " + object);
        int obj = object.ordinal();
        object = null;
        return GameModel.object_type.values()[obj];
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public speed_type getSpeed() {
        return speed;
    }

    public void setSpeed(speed_type speed) {
        this.speed = speed;
    }

    public int getLaps() {
        return laps;
    }

    public void incLaps() {
        this.laps++;
        System.out.println("inc laps");
    }
}


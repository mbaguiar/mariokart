package com.lpoo1718_t1g3.mariokart.model.entities;

import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import java.util.Random;
import static com.lpoo1718_t1g3.mariokart.model.GameModel.object_type.NULL;

/**
 * Class that represents a Kart in the game
 * @see EntityModel
 */
public class KartModel extends EntityModel {

    public final static float POWER = 100000;
    public final static float MINSTEERANGLE = 30;
    public final static float MAXSTEERANGLE = 45;
    public final static float MAXSPEED = 500;

    public static final float WIDTH = 24;
    public static final float HEIGHT = 28;

    private GameModel.object_type object;
    private boolean collision;
    private int playerId;

    /**
     * Constructs a KartModel in the given coordinates with the given rotation and associated to a player
     * @param x Starting x coordinate
     * @param y Starting y coordinate
     * @param rotation rotation angle
     * @param playerId player's id
     */
    public KartModel(float x, float y, float rotation, int playerId) {
        super(x, y, rotation);
        object = NULL;
        collision = true;
        this.playerId = playerId;
    }

    /**
     * Generates randomly a object if the kart does't already have one
     */
    public void generateObject() {

        if (object == NULL) {
            Random rand = new Random();
            int n = rand.nextInt(2) + 1;
            object = GameModel.object_type.values()[n];
            Message m = new Message(Message.MESSAGE_TYPE.POWER_UP, Message.SENDER.SERVER);
            m.addOption("powerUp", object);
            GameController.getInstance().writeToClient(m, playerId);
        }
    }

    /**
     * Returns the kart object
     * @return the generated kart object
     */
    public GameModel.object_type getObject() {
        if (object == NULL) {
            return NULL;
        }
        System.out.println("Released object " + object);
        int obj = this.object.ordinal();
        this.object = NULL;
        System.out.println(object);
        return GameModel.object_type.values()[obj];
    }

    /**
     * Checks if the kart can collide with other objects in the world
     * @return Return true if it can collide and false otherwise
     */
    public boolean isCollision() {
        return collision;
    }

    /**
     * Sets if the kart in can colliding or not
     * @param collision true if the kart can collide and false otherwise
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    /**
     * Gets the associated player's id
     * @return player's id
     */
    public int getPlayerId() {
        return playerId;
    }
}


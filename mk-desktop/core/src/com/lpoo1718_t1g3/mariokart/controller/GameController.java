package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public class GameController {

    private static GameController ourInstance = new GameController();
    private final World world;
    private final KartBody kartBody;
    private float accumulator;

    private GameController() {
        world = new World(new Vector2(0, 0), true);
        kartBody = new KartBody(world, GameModel.getInstance().getKart());
    }

    public static GameController getInstance() {
        return ourInstance;
    }

    public void update (float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        ((EntityModel) kartBody.getUserdata()).setPosition(kartBody.getX(), kartBody.getY());
        ((EntityModel) kartBody.getUserdata()).setRotation(kartBody.getAngle());
    }

    public World getWorld() {
        return world;
    }

    public void accelerate() {
        kartBody.applyForceToCenter(10, 0, true);
    }
}
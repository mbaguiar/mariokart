package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;

import java.util.List;

public class GameController {

    private static GameController ourInstance = new GameController();
    private ServerManager server;
    private final World world;
    private final KartBody kartBody;
    private float accumulator;

    private GameController() {
        world = new World(new Vector2(0, 0), true);
        kartBody = new KartBody(world, GameModel.getInstance().getKart());
        server = new ServerManager(4444);
        new Thread(server).start();
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

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);


        for (Body body : bodies) {

            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());

        }
        if (kartBody.getBody().getLinearVelocity().y < 0.01) {
            kartBody.getBody().setLinearVelocity(0f, 0f);
        } else {
            kartBody.getBody().setLinearDamping(kartBody.getBody().getLinearVelocity().y * 0.4f);
        }
    }

    public World getWorld() {
        return world;
    }

    public void accelerate() {
        float x = 0.05f * - (float) Math.sin(kartBody.getAngle() * Math.PI / 180);
        float y = 0.05f * (float) Math.cos(kartBody.getAngle() * Math.PI / 180);
        System.out.println(x + " " + y);
        kartBody.getBody().setTransform(kartBody.getX() + x, kartBody.getY() + y, kartBody.getAngle());
    }

    public void rotateLeft() {
        if (kartBody.getBody().getAngle() == 360) {
            kartBody.getBody().setTransform(kartBody.getX(), kartBody.getY(), 0);
        }
        kartBody.getBody().setTransform(kartBody.getX(), kartBody.getY(), kartBody.getAngle() + 1);
    }

    public void rotateRight() {
        if (kartBody.getBody().getAngle() == 0) {
            kartBody.getBody().setTransform(kartBody.getX(), kartBody.getY(), 360);
        }
        kartBody.getBody().setTransform(kartBody.getX(), kartBody.getY(), kartBody.getAngle() - 1);
        System.out.println(kartBody.getAngle());
    }
}
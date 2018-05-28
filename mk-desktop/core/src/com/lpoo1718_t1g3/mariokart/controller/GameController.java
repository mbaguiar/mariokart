package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.TireBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.TrackBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;
import com.lpoo1718_t1g3.mariokart.view.GameView;

public class GameController {

    private static GameController ourInstance = new GameController();
    private ServerManager server;
    private final World world;
    private final KartBody kartBody;
    private final TrackBody trackBody;
    private float accumulator;
    private boolean gas = false;
    private boolean left = false;
    private boolean right = false;

    private GameController() {
        world = new World(new Vector2(0, 0), true);
        world.clearForces();
        kartBody = new KartBody(world, GameModel.getInstance().getKart(), 1, 2, 10, 15, 20, 20,(float) Math.PI);
        trackBody = new TrackBody(world, GameModel.getInstance().getTrack1());
        server = new ServerManager();
    }

    public static GameController getInstance() {
        return ourInstance;
    }

    public void update (float delta) {
        handleMovement();
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        kartBody.update(delta);

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);


        for (Body body : bodies) {

            verifyBounds(body);

            if (!body.getUserData().equals(new Integer(0))) {
                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
                ((EntityModel) body.getUserData()).setRotation(MathUtils.radiansToDegrees * kartBody.getAngle() + 180);
            }


        }


    }

    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, 0, body.getAngle());

        if (body.getPosition().x > GameView.VIEWPORT_WIDTH)
            body.setTransform(GameView.VIEWPORT_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > GameView.VIEWPORT_WIDTH)
            body.setTransform(body.getPosition().x, GameView.VIEWPORT_WIDTH, body.getAngle());
    }

    private void handleMovement() {
        if (gas) setKartState(KartBody.acc_type.ACC_ACCELERATE);
        if (left) setKartState(KartBody.steer_type.STEER_LEFT);
        if (right) setKartState(KartBody.steer_type.STEER_RIGHT);
    }

    public void getControllerInput(Message m){
        if ((Boolean) m.getOptions().get("upPressed")) gas = true;
        else gas = false;
        if ((Boolean) m.getOptions().get("leftPressed")) left = true;
        else left = false;
        if ((Boolean) m.getOptions().get("rightPressed")) right = true;
        else right = false;
    }

    public World getWorld() {
        return world;
    }

    public void setKartState(KartBody.steer_type value) {
        kartBody.setSteer(value);
    }

    public void setKartState(KartBody.acc_type value) {
        kartBody.setAccelerate(value);
    }
}
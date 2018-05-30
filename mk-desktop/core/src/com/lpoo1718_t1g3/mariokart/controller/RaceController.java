package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.TrackBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.view.RaceView;

public class RaceController {

    private final World world;
    private final KartBody kartBody;
    private final RaceView raceView;
    private final TrackBody trackBody;
    private float accumulator;
    private boolean gas = false;
    private boolean left = false;
    private boolean right = false;

    RaceController(){
        world = new World(new Vector2(0, 0), true);
        world.clearForces();
        kartBody = new KartBody(world, GameModel.getInstance().getKart(), 1, 2, 10, 15, 20, 20,(float) Math.PI);
        trackBody = new TrackBody(world, GameModel.getInstance().getTrack1());
        raceView = new RaceView();
        //kartBody = new KartBody(world, GameModel.getInstance().getKart());
    }

    public RaceView getRaceView() {
        return raceView;
    }

    public World getWorld() {
        return world;
    }

    public void update(float delta) {

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

            if (!body.getUserData().equals(0)) {
                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
                ((EntityModel) body.getUserData()).setRotation(MathUtils.radiansToDegrees * kartBody.getAngle() + 180);
            }
        }
    }

    public void handleInput(Message m) {

        gas = (Boolean) m.getOptions().get("upPressed");
        left = (Boolean) m.getOptions().get("leftPressed");
        right = (Boolean) m.getOptions().get("rightPressed");

    }

    private void handleMovement() {
        if (gas) setKartState(KartBody.acc_type.ACC_ACCELERATE);
        if (left) setKartState(KartBody.steer_type.STEER_LEFT);
        if (right) setKartState(KartBody.steer_type.STEER_RIGHT);
    }

    public void setKartState(KartBody.steer_type value) {
        kartBody.setSteer(value);
    }

    public void setKartState(KartBody.acc_type value) {
        kartBody.setAccelerate(value);
    }

    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, 0, body.getAngle());

        if (body.getPosition().x > raceView.VIEWPORT_WIDTH)
            body.setTransform(raceView.VIEWPORT_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > raceView.VIEWPORT_WIDTH)
            body.setTransform(body.getPosition().x, raceView.VIEWPORT_WIDTH, body.getAngle());
    }

}

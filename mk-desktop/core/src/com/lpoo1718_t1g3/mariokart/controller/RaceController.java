package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.MysteryBoxBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.TrackBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.view.RaceView;

import java.util.HashMap;

public class RaceController implements ContactListener {

    private final World world;
    private final RaceView raceView;
    private final TrackBody trackBody;
    private HashMap<Integer, KartBody> kartBodies = new HashMap<Integer, KartBody>();
    private float accumulator;
    private boolean gas = false;
    private boolean left = false;
    private boolean right = false;

    RaceController() {
        world = new World(new Vector2(0, 0), true);
        world.clearForces();
        //kartBody = new KartBody(world, GameModel.getInstance().getKart(), 1, 2, 10, 15, 20, 20, (float) Math.PI);
        trackBody = new TrackBody(world, GameModel.getInstance().getTrack1());
        raceView = new RaceView();
        for (MysteryBoxModel box : GameModel.getInstance().getTrack1().getBoxes()) {
            MysteryBoxBody boxBody = new MysteryBoxBody(world, box);
        }
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
        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }

        for (KartBody kartBody : kartBodies.values()) {
            kartBody.update(delta);
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);


        for (Body body : bodies) {

            verifyBounds(body);

            if (!body.getUserData().equals(0)) {
                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
                ((EntityModel) body.getUserData()).setRotation(MathUtils.radiansToDegrees * body.getAngle() + 180);
            }


        }

    }

    public void handleInput(Message m) {

        gas = (Boolean) m.getOptions().get("upPressed");
        left = (Boolean) m.getOptions().get("leftPressed");
        right = (Boolean) m.getOptions().get("rightPressed");

    }

    private void handleMovement() {
        if (gas) setKartState(KartBody.acc_type.ACC_ACCELERATE, 1);
        if (left) setKartState(KartBody.steer_type.STEER_LEFT, 1);
        if (right) setKartState(KartBody.steer_type.STEER_RIGHT, 1);
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

    public void setKartState(KartBody.steer_type value, int playerId) {
        KartBody kartBody = kartBodies.get(playerId);
        if (kartBody != null) kartBody.setSteer(value);
    }

    public void setKartState(KartBody.acc_type value, int playerId) {
        KartBody kartBody = kartBodies.get(playerId);
        if (kartBody != null) kartBody.setAccelerate(value);
    }


    public void addKartBody(Player player) {
        kartBodies.put(player.getPlayerId(), new KartBody(world, player.getKartModel(), 1, 2, 10, 20, 25, 20, (float) Math.PI));
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

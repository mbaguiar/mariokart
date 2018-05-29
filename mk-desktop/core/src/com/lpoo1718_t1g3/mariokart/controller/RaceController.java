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
import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.view.RaceView;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.VIEWPORT_HEIGHT;
import static com.lpoo1718_t1g3.mariokart.view.RaceView.VIEWPORT_WIDTH;

public class RaceController implements ContactListener {

    private final World world;
    private final RaceView raceView;
    private final TrackBody trackBody;
    private HashMap<Integer, KartBody> kartBodies = new HashMap<Integer, KartBody>();
    private ArrayList<MysteryBoxBody> mysteryBoxes = new ArrayList<MysteryBoxBody>();
    private float accumulator;
    private boolean gas = false;
    private boolean left = false;
    private boolean right = false;

    RaceController() {
        world = new World(new Vector2(0, 0), true);
        world.clearForces();
        trackBody = new TrackBody(world, GameModel.getInstance().getTrack1());
        raceView = new RaceView();

        for (MysteryBoxModel box : GameModel.getInstance().getTrack1().getBoxes()) {
            mysteryBoxes.add(new MysteryBoxBody(world, box));
        }

        world.setContactListener(this);

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

        if (body.getPosition().x > VIEWPORT_WIDTH)
            body.setTransform(VIEWPORT_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > VIEWPORT_HEIGHT)
            body.setTransform(body.getPosition().x, VIEWPORT_HEIGHT, body.getAngle());
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
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof KartModel && bodyB.getUserData() instanceof MysteryBoxModel) {
            mysteryBoxCollision(bodyB, bodyA);
            return;
        }

        if (bodyA.getUserData() instanceof MysteryBoxModel && bodyB.getUserData() instanceof KartModel) {
           mysteryBoxCollision(bodyA, bodyB);
           return;
        }

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

    private void mysteryBoxCollision(Body mysteryBox, Body kartBody) {
        MysteryBoxModel box = (MysteryBoxModel) mysteryBox.getUserData();
        KartModel kart = (KartModel) kartBody.getUserData();
        if (box.isEnable()) {
            box.setEnable(false);
            //gerar objecto aleatorio
        }
    }
}

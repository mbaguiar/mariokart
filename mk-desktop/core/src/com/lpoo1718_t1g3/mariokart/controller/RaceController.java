package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lpoo1718_t1g3.mariokart.controller.entities.*;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.Position;
import com.lpoo1718_t1g3.mariokart.model.entities.*;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.view.RaceView;

import java.util.*;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;

/**
 * Class that controls the logic and physics of a Race and its Players
 */
public class RaceController implements ContactListener {

    private final World world;
    private final RaceView raceView;
    private final TrackBody trackBody;
    private HashMap<Integer, KartBody> kartBodies = new HashMap<Integer, KartBody>();
    private ArrayList<MysteryBoxBody> mysteryBoxes = new ArrayList<MysteryBoxBody>();
    private ArrayList<EntityBody> objectBodies = new ArrayList<EntityBody>();
    private float accumulator;

    /**
     * Constructs a new RaceController and initializes all its data
     */
    RaceController() {
        world = new World(new Vector2(0, 0), true);
        world.clearForces();
        trackBody = new TrackBody(world, GameModel.getInstance().getCurrentRace().getTrack());
        raceView = new RaceView();

        for (MysteryBoxModel box : GameModel.getInstance().getCurrentRace().getTrack().getBoxes()) {
            mysteryBoxes.add(new MysteryBoxBody(world, box));
        }

        float x = GameModel.getInstance().getCurrentRace().getTrack().xStartPosition;
        float y = GameModel.getInstance().getCurrentRace().getTrack().yStartPosition;

        for (Player player : GameModel.getInstance().getPlayers()) {
                player.getKartModel().setPosition(x, y);
            x += GameModel.getInstance().getCurrentRace().getTrack().incStartPosition;
            kartBodies.put(player.getPlayerId(), new KartBody(world, player.getKartModel(), - (float) Math.PI / 2));
            player.resetPosition();
            GameModel.getInstance().getCurrentRace().addPosition(player.getPosition());
        }

        GameModel.getInstance().getCurrentRace().sortPositions();
        world.setContactListener(this);

        GameModel.getInstance().getCurrentRace().initRace();

    }


    /**
     * Gets RaceView
     * @return returns instance of raceView
     */
    public RaceView getRaceView() {
        return raceView;
    }

    public World getWorld() {
        return world;
    }

    public void update(float delta) {

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }

        for (KartBody kartBody : kartBodies.values()) {
            kartBody.update(delta);
        }

        for (Position position : GameModel.getInstance().getCurrentRace().getPlayerPositions()) {
            if (position.isFinished() && kartBodies.get(position.playerId).isUpdate()) {
                kartBodies.get(position.playerId).disable();
                world.destroyBody(kartBodies.get(position.playerId).getBody());
            }
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);


        for (Body body : bodies) {

            verifyBounds(body);

            if (!body.getUserData().equals(0)) {

                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x * PIXEL_TO_METER, body.getPosition().y * PIXEL_TO_METER);
                ((EntityModel) body.getUserData()).setRotation(body.getAngle());

            }
        }

        if (checkRaceOver()) GameModel.getInstance().getCurrentRace().finishRace();

    }

    /**
     * Handles karts' movements based on the received messages
     * @param m received message
     */
    void handleMovement(Message m) {
        //System.out.println(m.toString());
        if ((Boolean) m.getOptions().get("throttle")) setKartState(KartBody.acc_type.ACC_ACCELERATE, m.getSenderId());
        if ((Boolean) m.getOptions().get("brake")) setKartState(KartBody.acc_type.ACC_BRAKE, m.getSenderId());
        if (!(Boolean) m.getOptions().get("throttle") && !(Boolean) m.getOptions().get("brake")) setKartState(KartBody.acc_type.ACC_NONE, m.getSenderId());
        float direction = (Float) m.getOptions().get("direction");
        if (direction >= 5) setKartState(KartBody.steer_type.STEER_RIGHT, m.getSenderId());
        else if (direction <= -5) setKartState(KartBody.steer_type.STEER_LEFT, m.getSenderId());
        else setKartState(KartBody.steer_type.STEER_NONE, m.getSenderId());
    }


    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, 0, body.getAngle());

        if (body.getPosition().x > 1080)
            body.setTransform(1080, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > 1080)
            body.setTransform(body.getPosition().x, 1080, body.getAngle());
    }

    private void setKartState(KartBody.steer_type value, int playerId) {
        KartBody kartBody = kartBodies.get(playerId);
        if (kartBody != null && kartBody.isUpdate()) kartBody.setSteer(value);
    }

    private void setKartState(KartBody.acc_type value, int playerId) {
        KartBody kartBody = kartBodies.get(playerId);
        if (kartBody != null && kartBody.isUpdate()) kartBody.setAccelerate(value);
    }


    /**
     * Makes player with the id playerId use its object
     * @param playerId player id
     */
    public void useObject(int playerId) {
        GameModel.object_type obj = ((KartModel) kartBodies.get(playerId).getUserdata()).getObject();

        if (obj != null) {
            switch (obj) {
                case BANANA:
                    useBanana(playerId);
                    break;
                case FAKE_MYSTERY_BOX:
                    useFakeMysteryBox(playerId);
                    break;
                case NULL:
                    break;
            }
        }

    }

    private void useBanana(int playerId) {
        KartModel kartModel = ((KartModel) kartBodies.get(playerId).getUserdata());
        kartModel.setCollision(false);
        BananaModel banana = new BananaModel(kartModel.getX(), kartModel.getY(), 0);
        objectBodies.add(new BananaBody(world, banana));
        GameModel.getInstance().getCurrentRace().getTrack().addObject(banana);
    }

    private void useFakeMysteryBox(int playerId) {
        KartModel kartModel = ((KartModel) kartBodies.get(playerId).getUserdata());
        kartModel.setCollision(false);
        FakeMysteryBoxModel fakeBox = new FakeMysteryBoxModel(kartModel.getX() , kartModel.getY(), 0);
        objectBodies.add(new FakeMysteryBoxBody(world, fakeBox));
        GameModel.getInstance().getCurrentRace().getTrack().addObject(fakeBox);
    }


    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof FakeMysteryBoxModel && bodyB.getUserData() instanceof KartModel) {
            if (((KartModel) bodyB.getUserData()).isCollision()) {
                fakeMysteryBoxCollision(bodyB);
                ((FakeMysteryBoxModel) bodyA.getUserData()).setToDelete();
            }

            return;
        }

        if (bodyA.getUserData() instanceof KartModel && bodyB.getUserData() instanceof  FakeMysteryBoxModel) {
            if (((KartModel) bodyA.getUserData()).isCollision()) {
                fakeMysteryBoxCollision(bodyA);
                ((FakeMysteryBoxModel) bodyB.getUserData()).setToDelete();
            }

            return;

        }

        if (bodyA.getUserData() instanceof KartModel && bodyB.getUserData() instanceof MysteryBoxModel) {
            mysteryBoxCollision(bodyB, bodyA);

        }

        if (bodyA.getUserData() instanceof MysteryBoxModel && bodyB.getUserData() instanceof KartModel) {
           mysteryBoxCollision(bodyA, bodyB);


        }

        if (bodyA.getUserData() instanceof BananaModel && bodyB.getUserData() instanceof KartModel) {
            if (((KartModel) bodyB.getUserData()).isCollision()) {
                bananaCollision(bodyB);
                ((BananaModel) bodyA.getUserData()).setToDelete();
            }

            return;

        }

        if (bodyA.getUserData() instanceof  KartModel && bodyB.getUserData() instanceof BananaModel) {
            if (((KartModel) bodyA.getUserData()).isCollision()) {
                bananaCollision(bodyA);
                ((BananaModel) bodyB.getUserData()).setToDelete();
            }

            return;
        }

        if (bodyA.getUserData() instanceof KartModel && bodyB.getUserData() instanceof FinishLineModel) {
            if (bodyA.getLinearVelocity().x < 0) {
                GameModel.getInstance().getPlayer(((KartModel) bodyA.getUserData()).getPlayerId()).getPosition().incLaps();
                GameModel.getInstance().getCurrentRace().sortPositions();

            }
        }

        if (bodyB.getUserData() instanceof KartModel && bodyA.getUserData() instanceof FinishLineModel) {
            if (bodyB.getLinearVelocity().x < 0) {
                GameModel.getInstance().getPlayer(((KartModel) bodyB.getUserData()).getPlayerId()).getPosition().incLaps();
                System.out.println("sort positions");
                GameModel.getInstance().getCurrentRace().sortPositions();
            }
        }


    }

    @Override
    public void endContact(Contact contact) {


        final Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof BananaModel && bodyB.getUserData() instanceof KartModel) {
            if (!((KartModel) bodyB.getUserData()).isCollision()) {
                ((KartModel) bodyB.getUserData()).setCollision(true);
            }

            return;
        }

        if (bodyA.getUserData() instanceof  KartModel && bodyB.getUserData() instanceof BananaModel) {
            if (!((KartModel) bodyA.getUserData()).isCollision()) {
                ((KartModel) bodyA.getUserData()).setCollision(true);
            }

            return;

        }

        if (bodyA.getUserData() instanceof FakeMysteryBoxModel && bodyB.getUserData() instanceof KartModel) {
            if (!((KartModel) bodyB.getUserData()).isCollision()) {
                ((KartModel) bodyB.getUserData()).setCollision(true);
            }

            return;
        }

        if (bodyA.getUserData() instanceof  KartModel && bodyB.getUserData() instanceof FakeMysteryBoxModel) {
            if (!((KartModel) bodyA.getUserData()).isCollision()) {
                ((KartModel) bodyA.getUserData()).setCollision(true);
            }

            return;

        }
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
        System.out.println("collision box");
        if (box.isEnable()) {
            box.disable();
            kart.generateObject();
        }
    }

    private void bananaCollision(Body kartBody) {
        for (KartBody body : kartBodies.values()) {
            if (body.getBody() == kartBody) {
                body.steerHard();
            }
        }
    }

    private void fakeMysteryBoxCollision(Body kartBody) {
        kartBody.setLinearVelocity(0, 0);
    }

    /**
     * Removes from the world all the bodies with the flag toDelete set
     */
    public void removeFlagged() {
        for (EntityBody body : objectBodies) {
            if (body.getUserdata() instanceof FakeMysteryBoxModel) {
                if ( ((FakeMysteryBoxModel) body.getUserdata()).isToDelete()) {
                    world.destroyBody(body.getBody());
                }
            }

            if (body.getUserdata() instanceof BananaModel) {
                if ( ((BananaModel) body.getUserdata()).isToDelete()) {
                    world.destroyBody(body.getBody());
                }
            }
        }
    }

    /**
     * Enables all the karts on the current race
     */
    public void enableKarts() {
        for (KartBody kartBody : kartBodies.values()) {
            kartBody.enable();
        }
    }

    private boolean checkRaceOver() {
        for (com.lpoo1718_t1g3.mariokart.model.Position position : GameModel.getInstance().getCurrentRace().getPlayerPositions()) {
            if (!position.isFinished()) return false;
        }

        return true;
    }
}

package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class that represents a body of a Kart in the Game
 * @see EntityBody
 */
public class KartBody extends EntityBody {

    private float maxSteerAngle, minSteerAngle, maxSpeed, power;
    private float wheelAngle;
    private steer_type steer;
    private acc_type accelerate;
    private List<TireBody> wheels;
    private boolean update;

    /**
     * Kart steer type
     */
    public enum steer_type {STEER_NONE, STEER_LEFT, STEER_RIGHT, STEER_HARD_LEFT, STEER_HARD_RIGHT}

    /**
     * Kart acceleration type
     */
    public enum acc_type {ACC_NONE, ACC_ACCELERATE, ACC_BRAKE}

    /**
     * Constructs a KartBody in the given world with the given model and with the given rotation angle
     * @param world world in which to create the body
     * @param model model from which to create the body
     * @param angle rotation angle to set the body to
     */
    public KartBody(World world, EntityModel model, float angle) {
        super(world, model, BodyDef.BodyType.DynamicBody);

        this.steer = steer_type.STEER_NONE;
        this.accelerate = acc_type.ACC_NONE;
        this.body.setTransform(body.getPosition().x, body.getPosition().y, angle);
        this.maxSteerAngle = KartModel.MAXSTEERANGLE;
        this.minSteerAngle = KartModel.MINSTEERANGLE;
        this.maxSpeed = KartModel.MAXSPEED;
        this.power = KartModel.POWER;
        this.wheelAngle = 0;
        this.update = false;

        createFixture(body);

        this.wheels = new ArrayList<TireBody>();
        this.wheels.add(new TireBody(world, this, -12f, -14f, 5f, 10f, true, true));
        this.wheels.add(new TireBody(world, this, 12f, -14f, 5f, 10f, true, true));
        this.wheels.add(new TireBody(world, this, -12f, 14f, 5f, 10f, false, false));
        this.wheels.add(new TireBody(world, this, 12f, 14f, 5f, 10f, false, false));


    }

    private void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;
        PolygonShape kartShape = new PolygonShape();
        kartShape.setAsBox(KartModel.WIDTH/2, KartModel.HEIGHT/2);
        fixtureDef.shape = kartShape;
        this.body.createFixture(fixtureDef);
        this.body.setLinearDamping(0);
    }

    private List<TireBody> getPoweredWheels() {
        ArrayList<TireBody> poweredWheels = new ArrayList<TireBody>();
        for (TireBody tireBody : this.wheels) {
            if (tireBody.powered) {
                poweredWheels.add(tireBody);
            }
        }

        return poweredWheels;
    }

    private Vector2 getLocalVelocity() {

        return this.body.getLocalVector(this.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
    }

    private List<TireBody> getRevolvingWheels() {
        List<TireBody> revolvingWheels = new ArrayList<TireBody>();
        for (TireBody tireBody : this.wheels) {
            if (tireBody.revolving) {
                revolvingWheels.add(tireBody);
            }
        }

        return revolvingWheels;
    }

    private float getSpeedKMH() {
        Vector2 velocity = this.body.getLinearVelocity();
        float len = velocity.len();
        return (len / 1000) * 3600;
    }

    private void setSpeed(float speed) {
        Vector2 velocity = this.body.getLinearVelocity();
        velocity = velocity.nor();
        velocity = new Vector2(velocity.x * ((speed * 1000.0f) / 3600.0f), velocity.y * ((speed * 1000.0f) / 3600.0f));
        this.body.setLinearVelocity(velocity);
    }

    /**
     * Sets the current steer type of the kart
     * @param value steer type
     */
    public void setSteer(steer_type value) {
        this.steer = value;
    }

    /**
     * Sets the current acceleration type of the kart
     * @param value acceleration type
     */
    public void setAccelerate(acc_type value) {
        this.accelerate = value;
    }

    /**
     * Updates the kart based on the time passed
     * @param deltaTime time passed
     */
    public void update(float deltaTime) {

        for (TireBody tire : wheels) {
            tire.killSidewaysVelocity();
        }

        float incr = (this.maxSteerAngle) * deltaTime * 5;

        switch (this.steer) {
            case STEER_LEFT:
                this.wheelAngle = Math.max(Math.min(this.wheelAngle, 0) + incr, this.minSteerAngle);
                break;
            case STEER_RIGHT:
                this.wheelAngle = Math.min(Math.max(this.wheelAngle, 0) - incr, -this.minSteerAngle);
                break;
            case STEER_HARD_LEFT:
                this.wheelAngle = Math.max(Math.min(this.wheelAngle, 0) + incr, this.maxSteerAngle);
                break;
            case STEER_HARD_RIGHT:
                this.wheelAngle = Math.max(Math.min(this.wheelAngle, 0) - incr, -this.maxSteerAngle);
                break;
            case STEER_NONE:
                this.wheelAngle = 0;
                break;
        }


        for (TireBody tire : getRevolvingWheels()) {
            tire.setAngle(this.wheelAngle);
        }


        Vector2 baseVector;

        if ((this.accelerate == acc_type.ACC_ACCELERATE) && (this.getSpeedKMH() < this.maxSpeed)) {
            baseVector = new Vector2(0, -1);
        } else if (this.accelerate == acc_type.ACC_BRAKE && (-this.getSpeedKMH() > -this.maxSpeed)) {
            if (this.getLocalVelocity().y < 0) {
                baseVector = new Vector2(0f, 1.3f);
            } else {
                baseVector = new Vector2(0f, 0.7f);
            }
        } else if (this.accelerate == acc_type.ACC_NONE) {
            baseVector = new Vector2(0, 0);
            if (this.getSpeedKMH() < 7) {
                this.setSpeed(0);
            } else if (this.getLocalVelocity().y < 0) {
                baseVector = new Vector2(0, 0.7f);

            } else if (this.getLocalVelocity().y > 0) {
                baseVector = new Vector2(0, -0.7f);
            }
        } else baseVector = new Vector2(0, 0);

        Vector2 forceVector = new Vector2(this.power * baseVector.x, this.power * baseVector.y);

        for (TireBody tire : getPoweredWheels()) {
            Vector2 position = tire.body.getWorldCenter();
            tire.body.applyForce(tire.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true);
        }

    }

    /**
     * Checks if the kart can update
     * @return returns true if kart can update and false otherwise
     */
    public boolean isUpdate() {
        return update;
    }

    /**
     * Disables kart, prevents it from updating
     */
    public void disable() {
        this.update = false;
    }

    /**
     * Enables kart, sets it to possible to update
     */
    public void enable() {
        this.update = true;
    }

    /**
     * Steers kart hard to the right for half a second
     */
    public void steerHard() {
        this.steer = steer_type.STEER_HARD_RIGHT;
        this.update = false;
        this.maxSteerAngle = 50;
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        update = true;
                        maxSteerAngle = 25;
                    }
                }, 500
        );
    }
}

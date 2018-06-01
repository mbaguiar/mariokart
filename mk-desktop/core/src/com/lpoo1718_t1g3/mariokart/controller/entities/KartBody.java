package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.lpoo1718_t1g3.mariokart.Model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.Model.entities.KartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class KartBody extends EntityBody {

    float maxSteerAngle, minSteerAngle, maxSpeed, power;
    float wheelAngle;
    private steer_type steer;
    private acc_type accelerate;
    public List<TireBody> wheels;
    boolean update;

    public enum steer_type {STEER_NONE, STEER_LEFT, STEER_RIGHT, STEER_HARD_LEFT, STEER_HARD_RIGHT}

    ;

    public enum acc_type {ACC_NONE, ACC_ACCELERATE, ACC_BRAKE}

    ;

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
        this.update = true;

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
    }

    public List<TireBody> getPoweredWheels() {
        ArrayList<TireBody> poweredWheels = new ArrayList<TireBody>();
        for (TireBody tireBody : this.wheels) {
            if (tireBody.powered) {
                poweredWheels.add(tireBody);
            }
        }

        return poweredWheels;
    }

    public Vector2 getLocalVelocity() {

        return this.body.getLocalVector(this.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
    }

    public List<TireBody> getRevolvingWheels() {
        List<TireBody> revolvingWheels = new ArrayList<TireBody>();
        for (TireBody tireBody : this.wheels) {
            if (tireBody.revolving) {
                revolvingWheels.add(tireBody);
            }
        }

        return revolvingWheels;
    }

    public float getSpeedKMH() {
        Vector2 velocity = this.body.getLinearVelocity();
        float len = velocity.len();
        return (len / 1000) * 3600;
    }

    public void setSpeed(float speed) {
        Vector2 velocity = this.body.getLinearVelocity();
        velocity = velocity.nor();
        velocity = new Vector2(velocity.x * ((speed * 1000.0f) / 3600.0f), velocity.y * ((speed * 1000.0f) / 3600.0f));
        this.body.setLinearVelocity(velocity);
    }

    public void setSteer(steer_type value) {
        this.steer = value;
    }

    public void setAccelerate(acc_type value) {
        this.accelerate = value;
    }

    public void update(float deltaTime) {

        System.out.println(steer + "; " + accelerate);

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

        //System.out.println(this.wheelAngle);

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

    public void speedUp() {
        this.power = 30;
        this.maxSpeed = 40;
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        power = 15;
                        maxSpeed = 20;
                    }
                }, 1000
        );
    }

    public boolean isUpdate() {
        return update;
    }

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

    public float getPower() {
        return power;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }
}

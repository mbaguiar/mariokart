package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * Class that represents a tire of a kartBody in the game
 */
public class TireBody {

    Body body;

    public KartBody kartBody;
    private float width;
    private float length;
    public boolean revolving;
    public boolean powered;

    /**
     * Constructs a tire body for a given kartBody in the given world
     * @param world world in which to create the body
     * @param kartBody kartBody that the tire belongs to
     * @param posX tire's starting x position
     * @param posY tire's starting y position
     * @param width tire's width
     * @param length tire's length
     * @param revolving true if a tire is revolving and false otherwise
     * @param powered true if a tire is powered and false otherwise
     */
    public TireBody(World world, KartBody kartBody, float posX, float posY, float width, float length, boolean revolving, boolean powered) {
        super();
        this.kartBody = kartBody;
        this.width = width;
        this.length = length;
        this.revolving = revolving;
        this.powered = powered;

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(kartBody.body.getWorldPoint(new Vector2(posX, posY)));
        def.angle = kartBody.getAngle();
        body = world.createBody(def);
        createFixture(body);
        body.setUserData(new Integer(0));
        body.setLinearDamping(0);

        if (this.revolving) {
            RevoluteJointDef jointDef = new RevoluteJointDef();
            jointDef.initialize(this.kartBody.body, this.body, this.body.getWorldCenter());
            jointDef.enableMotor = false;
            world.createJoint(jointDef);
        } else {
            PrismaticJointDef jointDef = new PrismaticJointDef();
            jointDef.initialize((this.kartBody.body), this.body, this.body.getWorldCenter(), new Vector2(1, 0));
            jointDef.enableLimit = true;
            jointDef.lowerTranslation = jointDef.upperTranslation = 0;
            world.createJoint(jointDef);
        }
    }

    private void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.isSensor = true;
        PolygonShape wheelShape = new PolygonShape();
        wheelShape.setAsBox(this.width / 2, this.length / 2);
        fixtureDef.shape = wheelShape;
        this.body.createFixture(fixtureDef);
        wheelShape.dispose();
    }

    /**
     * Sets body angle
     * @param angle value to set the body's angle to
     */
    public void setAngle(float angle) {
        this.body.setTransform(body.getPosition(), this.kartBody.getAngle() + (float) Math.toRadians(angle));
    }

    private Vector2 getLocalVelocity() {
        return this.kartBody.body.getLocalVector(this.kartBody.body.getLinearVelocityFromLocalPoint(this.body.getPosition()));
    }

    private Vector2 getDirectionVector() {
        Vector2 directionVector;
        if (this.getLocalVelocity().y > 0) {
            directionVector = new Vector2(0, 1);
        } else {
            directionVector = new Vector2(0, -1);
        }

        return directionVector.rotate((float) Math.toDegrees(this.body.getAngle()));
    }

    private Vector2 getKillVelocityVector() {
        Vector2 velocity = this.body.getLinearVelocity();
        Vector2 sidewaysAxis = this.getDirectionVector();
        float dotprod = velocity.dot(sidewaysAxis);
        return new Vector2(sidewaysAxis.x * dotprod, sidewaysAxis.y * dotprod);
    }

    /**
     * Kills tire sideways velocity
     */
    public void killSidewaysVelocity() {
        this.body.setLinearVelocity(this.getKillVelocityVector());
    }


}

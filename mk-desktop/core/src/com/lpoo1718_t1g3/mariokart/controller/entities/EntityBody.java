package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;

public abstract class EntityBody {

    Body body;

    public EntityBody(World world, EntityModel model, BodyDef.BodyType bodyType) {
        BodyDef def = new BodyDef();
        def.type = bodyType;
        def.position.set(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        def.angle = model.getRotation();
        body = world.createBody(def);
        body.setUserData(model);
    }

    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    public void setAngularVelocity(float omega) {
        body.setAngularVelocity(omega);
    }

    public void applyForceToCenter(float forceX, float forceY, boolean awake) {
        body.applyForceToCenter(forceX, forceY, awake);
    }

    public Object getUserdata() {
        return body.getUserData();
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public float getAngle() {
        return body.getAngle();
    }

    public Body getBody() {
        return  body;
    }
}

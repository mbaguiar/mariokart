package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public abstract class EntityBody {

    Body body;

    public EntityBody(World world, EntityModel model) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(model.getX(), model.getY());
        def.angle = model.getRotation();
        body = world.createBody(def);
        body.setUserData(model);
    }

    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    public void setAngularVelocuty(float omega) {
        body.setAngularVelocity(omega);
    }

    public void applyForceToCenter(float forceX, float forceY, boolean awake) {
        body.applyForceToCenter(forceX, forceY, awake);
    }

    public Object getUserdata() {
        return body.getUserData();
    }
}

package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public class TrackBody {

    Body body1;
    Body body2;

    PhysicsShapeCache physicsBodies;
    PhysicsShapeCache physicsBodie1;

    public TrackBody(World world, EntityModel model) {
        physicsBodies = new PhysicsShapeCache("track1.xml");
        body1 = physicsBodies.createBody("track1", world, 1080/4033f, 1080/3875f);
        body1.setUserData(model);
        physicsBodie1 = new PhysicsShapeCache("track1-back.xml");
        body2 = physicsBodie1.createBody("track1-back", world, 1080/4033f, 1080/3875f);
        body2.setUserData(model);
        //createFixture(body);
    }

    private void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(10, 10);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

    public Body getBody() {
        return body1;
    }

}
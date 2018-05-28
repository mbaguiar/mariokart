package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public class MysteryBoxBody extends EntityBody {

    public MysteryBoxBody(World world, EntityModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);
        createFixture(body);
    }

    public void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(1, 1);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

    public Body getBody() {
        return body;
    }


}
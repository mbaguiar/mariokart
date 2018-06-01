package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.lpoo1718_t1g3.mariokart.Model.entities.BananaModel;
import com.lpoo1718_t1g3.mariokart.Model.entities.EntityModel;

public class BananaBody extends EntityBody {

    public BananaBody(World world, EntityModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);
        createFixture(body);
    }

    public void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(BananaModel.WIDTH / 2 , BananaModel.HEIGHT / 2);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }


}

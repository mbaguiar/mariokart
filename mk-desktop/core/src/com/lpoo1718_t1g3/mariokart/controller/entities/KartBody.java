package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public class KartBody extends EntityBody {

    public KartBody(World world, EntityModel model) {
        super(world, model);
        body.setLinearDamping(0.1f);
        createFixture(body);
    }

    private void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(10, 20);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;
        fixtureDef.friction = 1/2;
        fixtureDef.restitution = 1/10;

        body.createFixture(fixtureDef);
        polygonShape.dispose();

    }


    public Body getBody() {
        return body;
    }
}

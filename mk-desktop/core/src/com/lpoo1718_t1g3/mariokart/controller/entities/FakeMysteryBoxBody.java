package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.FakeMysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.model.entities.FinishLineModel;

/**
 * Class that represents a body of a Fake Mystery Box object in the game
 */
public class FakeMysteryBoxBody extends EntityBody {

    /**
     * Constructs a FakeMysteryBoxBody in the given world and with the given model
     * @param world world in which to create de body
     * @param model model form which to create the body
     */
    public FakeMysteryBoxBody(World world, EntityModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);
        createFixture(body);
    }

    private void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(FakeMysteryBoxModel.WIDTH / 2, FakeMysteryBoxModel.HEIGHT / 2);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

}

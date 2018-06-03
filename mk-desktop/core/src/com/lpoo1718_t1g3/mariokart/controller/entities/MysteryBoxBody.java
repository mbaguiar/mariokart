package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;

/**
 * Class that represents a body of a Mystery Box in the game
 * @see EntityBody
 */
public class MysteryBoxBody extends EntityBody {

    /**
     * Constructs a MysteryBoxBody in the given model
     * @param world world in which to create the body
     * @param model model from which to create the body
     */
    public MysteryBoxBody(World world, EntityModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);
        createFixture(body);
    }

    private void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(MysteryBoxModel.WIDTH / 2, MysteryBoxModel.HEIGHT / 2);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

}
package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.*;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.FinishLineModel;

/**
 * Class that represents a body of a Finish Line of a Track in the game
 * @see EntityBody
 */
public class FinishLineBody extends EntityBody {

    /**
     * Constructs a FinishLineBody in the given World and with the given model
     * @param world world in which to create the body
     * @param model model from which to create the body
     */
    public FinishLineBody(World world, EntityModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);
        createFixture(body);
    }

    private void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(FinishLineModel.WIDTH / 2, FinishLineModel.HEIGHT / 2);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }


}

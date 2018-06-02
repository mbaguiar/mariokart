package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;

/**
 * Class that represents a body of an entity in the game
 */
public abstract class EntityBody {

    Body body;

    /**
     * Creates a entity body in the given world, based on the given model and with the given body type
     * @param world world in which to create the body
     * @param model model to base the body on
     * @param bodyType body's type
     */
    public EntityBody(World world, EntityModel model, BodyDef.BodyType bodyType) {
        BodyDef def = new BodyDef();
        def.type = bodyType;
        def.position.set(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        def.angle = model.getRotation();
        body = world.createBody(def);
        body.setUserData(model);
    }

    /**
     * Returns the model from which the body was based on
     * @return EntityModel of the object
     */
    public Object getUserdata() {
        return body.getUserData();
    }

    /**
     * Returns the body x coordinate
     * @return current x coordinate
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Returns the body y coordinate
     * @return current y coordinate
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * Returns the rotation angle of the body
     * @return current rotation angle
     */
    public float getAngle() {
        return body.getAngle();
    }

    /**
     * Returns the physics body
     * @return physics body
     */
    public Body getBody() {
        return  body;
    }
}

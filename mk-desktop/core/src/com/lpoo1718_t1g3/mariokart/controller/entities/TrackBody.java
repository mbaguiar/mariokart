package com.lpoo1718_t1g3.mariokart.controller.entities;
import com.badlogic.gdx.physics.box2d.*;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.lpoo1718_t1g3.mariokart.model.TrackPart;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.FinishLineModel;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;

/**
 * Class that represents the body of a track in the game
 */
public class TrackBody {

    private Body body2;
    private FinishLineBody finishLineBody;
    private PhysicsShapeCache physicsBodies;

    /**
     * Constricts a TrackBody in the given world from the given model
     * @param world world in which to create the body
     * @param model model from which to create the body
     */
    public TrackBody(World world, EntityModel model) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.awake = true;
        bodyDef.angle = 0;

        TrackPart trackPart = new TrackPart(false, true, false);
        physicsBodies = new PhysicsShapeCache("track1-walls.xml");
        body2 = physicsBodies.createBody("track1-walls", world, bodyDef, 1, 1);
        body2.setUserData(trackPart);
        finishLineBody = new FinishLineBody(world, ((TrackModel) model).getFinishLineModel());
    }
}
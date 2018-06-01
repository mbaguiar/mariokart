package com.lpoo1718_t1g3.mariokart.controller.entities;
import com.badlogic.gdx.physics.box2d.*;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.lpoo1718_t1g3.mariokart.model.TrackPart;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.FinishLineModel;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;

public class TrackBody {

    private Body body1;
    private Body body2;

    private FinishLineBody finishLineBody;

    float y = 49*0.04f;
    float x = 34*0.04f;

    private PhysicsShapeCache physicsBodies;
    private PhysicsShapeCache physicsBodies1;

    public TrackBody(World world, EntityModel model) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.awake = true;
        bodyDef.angle = 0;

        //TrackPart trackPart1 = new TrackPart(true, false, false);

        //physicsBodies = new PhysicsShapeCache("track1.xml");
        //body1 = physicsBodies.createBody("track1", world, bodyDef, 1013/4033f, 977/3875f);
        //body1.setUserData(trackPart1);


        TrackPart trackPart2 = new TrackPart(false, true, false);
        physicsBodies1 = new PhysicsShapeCache("track1-walls.xml");
        body2 = physicsBodies1.createBody("track1-walls", world, bodyDef, 1, 1);
        body2.setUserData(trackPart2);

        finishLineBody = new FinishLineBody(world, ((TrackModel) model).getFinishLineModel());

    }

    public Body getBody() {
        return body1;
    }

}
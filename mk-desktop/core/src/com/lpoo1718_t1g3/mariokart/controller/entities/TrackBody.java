package com.lpoo1718_t1g3.mariokart.controller.entities;
import com.badlogic.gdx.physics.box2d.*;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import com.lpoo1718_t1g3.mariokart.model.TrackPart;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public class TrackBody {

    private Body body1;
    private Body body2;

    float y = 49*0.04f;
    float x = 34*0.04f;

    private PhysicsShapeCache physicsBodies;
    private PhysicsShapeCache physicsBodie1;

    public TrackBody(World world, EntityModel model) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(34, 49);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.awake = true;
        bodyDef.angle = 0;

        TrackPart trackPart1 = new TrackPart(true, false, false);
        TrackPart trackPart2 = new TrackPart(false, true, false);

        //physicsBodies = new PhysicsShapeCache("track1.xml");
        //body1 = physicsBodies.createBody("track1", world, bodyDef, 1013/4033f, 977/3875f);
       // body1.setUserData(trackPart1);
        physicsBodie1 = new PhysicsShapeCache("track1-back.xml");
        body2 = physicsBodie1.createBody("track1-back", world, bodyDef, 1013/4033f, 977/3875f);
        body2.setUserData(trackPart2);

    }

    public Body getBody() {
        return body1;
    }

}
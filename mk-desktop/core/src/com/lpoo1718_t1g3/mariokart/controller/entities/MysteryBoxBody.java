package com.lpoo1718_t1g3.mariokart.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public class MysteryBoxBody extends EntityBody {

    public MysteryBoxBody(World world, EntityModel model) {
        super(world, model, BodyDef.BodyType.StaticBody);
    }
}
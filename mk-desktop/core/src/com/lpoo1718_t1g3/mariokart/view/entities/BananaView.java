package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo1718_t1g3.mariokart.controller.entities.EntityBody;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

public class BananaView extends EntityView {

    public BananaView() {
        super("banana.png");
    }

    @Override
    public Sprite createSprite() {
        return null;
    }
}

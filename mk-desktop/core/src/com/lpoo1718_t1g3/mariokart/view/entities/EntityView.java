package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

abstract class EntityView {

    Sprite sprite;

    public EntityView(MarioKart game) {
        sprite = createSprite(game);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public abstract Sprite createSprite(MarioKart game);

    public void update(EntityModel model) {
        sprite.setCenter(model.getX(), model.getY());
        sprite.setRotation(model.getRotation());
    }

}

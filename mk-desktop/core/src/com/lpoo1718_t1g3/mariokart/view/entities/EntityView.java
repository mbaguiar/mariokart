package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;


public abstract class EntityView {

    Sprite sprite;
    String fileName;

    public EntityView(String fileName) {
        this.fileName = fileName;
        sprite = createSprite();
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Sprite createSprite() {
        if (MarioKart.getInstance().getAssetManager().isLoaded(fileName)) {
            Sprite sprite = new Sprite(MarioKart.getInstance().getAssetManager().get(fileName, Texture.class));
            return sprite;

        } else {
            System.out.println("not loaded");
            return null;
        }
    }

    public void update(EntityModel model) {
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation(model.getRotation());
    }

}

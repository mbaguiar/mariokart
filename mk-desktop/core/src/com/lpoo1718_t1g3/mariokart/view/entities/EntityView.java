package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;

import static com.lpoo1718_t1g3.mariokart.view.RaceView.PIXEL_TO_METER;

/**
 * Class that represents a view of a model in the game
 */
public abstract class EntityView {

    Sprite sprite;
    String fileName;

    /**
     * Constructs a entity view with the given sprite file
     * @param fileName sprite file name
     */
    public EntityView(String fileName) {
        this.fileName = fileName;
        sprite = createSprite();
    }

    /**
     * Draws view
     * @param batch game batch
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    private Sprite createSprite() {
        if (MarioKart.getInstance().getAssetManager().isLoaded(fileName)) {
            Sprite sprite = new Sprite(MarioKart.getInstance().getAssetManager().get(fileName, Texture.class));
            return sprite;

        } else {
            System.out.println("not loaded");
            return null;
        }
    }

    /**
     * Updates view based on a model
     * @param model Model to update view from
     */
    public void update(EntityModel model) {
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
        sprite.setRotation((float) Math.toDegrees(model.getRotation()));
    }

}

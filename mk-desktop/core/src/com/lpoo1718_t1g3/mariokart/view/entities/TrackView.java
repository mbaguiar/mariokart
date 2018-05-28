package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lpoo1718_t1g3.mariokart.MarioKart;

public class TrackView extends EntityView {

    public TrackView() {
        super("track1.png");
    }

    @Override
    public Sprite createSprite() {
        if (MarioKart.getInstance().getAssetManager().isLoaded(fileName)) {
            return new Sprite(MarioKart.getInstance().getAssetManager().get(fileName, Texture.class));
        }
        else System.out.print("not loaded");
        return null;
    }
}

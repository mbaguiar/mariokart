package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lpoo1718_t1g3.mariokart.MarioKart;

public class TrackView extends EntityView {

    public TrackView() {
        super();
    }

    @Override
    public Sprite createSprite() {
        if (MarioKart.getInstance().getAssetManager().isLoaded("track1.png")) {
            return new Sprite(MarioKart.getInstance().getAssetManager().get("track1.png", Texture.class));
        }
        else System.out.print("not loaded");
        return null;
    }
}

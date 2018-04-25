package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.view.GameView;

public class KartView extends EntityView {

    public KartView() {
        super();
    }

    @Override
    public Sprite createSprite() {
        if (MarioKart.getInstance().getAssetManager().isLoaded("mariokart.png")) {
            return new Sprite(MarioKart.getInstance().getAssetManager().get("mariokart.png", Texture.class));
        }
        else System.out.print("not loaded");
        return null;
    }
}

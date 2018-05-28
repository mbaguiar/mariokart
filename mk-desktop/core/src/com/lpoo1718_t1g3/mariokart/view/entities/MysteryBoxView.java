package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lpoo1718_t1g3.mariokart.MarioKart;

public class MysteryBoxView extends EntityView {

    public MysteryBoxView() {
        super("mysteryBox.png");

    }

    @Override
    public Sprite createSprite() {
        if (MarioKart.getInstance().getAssetManager().isLoaded(fileName)) {
            Sprite sprite = new Sprite(MarioKart.getInstance().getAssetManager().get(fileName, Texture.class));
            sprite.setSize(30, 30);
            return sprite;

        } else {
            System.out.println("not loaded");
            return null;
        }
    }
}

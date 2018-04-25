package com.lpoo1718_t1g3.mariokart.view.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.view.GameView;

public class KartView extends EntityView {

    public KartView() {
        super();
    }

    @Override
    public Sprite createSprite() {
        return MarioKart.getInstance().getAssetManager().get("badlogic.jpg");
    }
}

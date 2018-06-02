package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class AccelerometerControlView extends ControlView {

    public AccelerometerControlView(){
        throttle.setSize(stage.getWidth(), stage.getHeight()/5f);
        throttle.setPosition(stage.getWidth(), 0, Align.bottomRight);

        changeControls.setRotation(-90);
        changeControls.setPosition(stage.getWidth()/5f, stage.getHeight()/2f, Align.center);

        brake.setSize(stage.getWidth(), stage.getHeight()/5f);
        brake.setPosition(0, stage.getHeight(), Align.topLeft);

        powerUp.setRotation(-90);
        powerUp.setPosition(stage.getWidth()*3f/4f,stage.getHeight()/2f, Align.center);

        this.stage.addActor(throttle);
        this.stage.addActor(brake);

    }

    @Override
    public void render(float delta) {
        super.direction = Gdx.input.getAccelerometerY();
        super.render(delta);
    }

}

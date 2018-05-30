package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class ButtonControlView extends ControlView {

    private Touchpad joystick;


    public ButtonControlView(){
        throttle.setSize(stage.getWidth()/2f, stage.getHeight()/5f);
        throttle.setPosition(stage.getWidth(), 0, Align.bottomRight);

        brake.setSize(stage.getWidth()/2f, stage.getHeight()/5f);
        brake.setPosition(0, 0, Align.bottomLeft);

        Touchpad.TouchpadStyle joystickStyle = new Touchpad.TouchpadStyle();
        Texture t = new Texture(Gdx.files.internal("knob.png"));
        joystickStyle.knob = new TextureRegionDrawable(new TextureRegion(t));

        this.joystick = new Touchpad(50, joystickStyle);
        this.joystick.setSize(stage.getHeight()*2f/5f, stage.getHeight()*2f/5f);
        this.joystick.setPosition(stage.getWidth()/3f, stage.getHeight()*4f/5f, Align.center);
        this.joystick.setDebug(true);

        changeControls.setRotation(-90);
        changeControls.setPosition(stage.getWidth()/5f, stage.getHeight()*2f/5f, Align.center);

        powerUp.setRotation(-90);
        powerUp.setPosition(stage.getWidth()/2f,stage.getHeight()*2f/5f, Align.center);
        this.stage.addActor(joystick);

    }

    @Override
    public void render(float delta) {
        super.direction = - joystick.getKnobPercentY() * 10f;
        super.render(delta);
    }
}

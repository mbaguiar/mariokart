package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;

public class ButtonControlView extends ScreenAdapter {

    private Stage stage;

    public ButtonControlView(){
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = ViewDefaults.getDefaultButtonFont();

        TextButton throttle = new TextButton(">", style);
        throttle.setDebug(true);
        throttle.setTransform(true);
        throttle.setSize(stage.getWidth()/2f, stage.getHeight()/5f);
        throttle.setPosition(stage.getWidth(), 0, Align.bottomRight);

        TextButton brake = new TextButton("<", style);
        brake.setDebug(true);
        brake.setTransform(true);
        brake.setSize(stage.getWidth()/2f, stage.getHeight()/5f);
        brake.setPosition(0, 0, Align.bottomLeft);

        Touchpad.TouchpadStyle joystickStyle = new Touchpad.TouchpadStyle();
        Texture t = new Texture(Gdx.files.internal("knob.png"));
        joystickStyle.knob = new TextureRegionDrawable(new TextureRegion(t));

        Touchpad joystick = new Touchpad(50, joystickStyle);

        joystick.setSize(stage.getHeight()/2f, stage.getHeight()/2f);
        joystick.setPosition(stage.getWidth()/2f, stage.getHeight()*2f/3f, Align.center);
        joystick.setDebug(true);
        this.stage.addActor(throttle);
        this.stage.addActor(brake);
        this.stage.addActor(joystick);
    }

    @Override
    public void render(float delta) {
        drawBackground();
        stage.act();
        stage.draw();
        GameController.getInstance().update();
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
}

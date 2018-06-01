package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.GameModel;

public class ControlView extends ScreenAdapter {
    Stage stage;
    private boolean isAccelerating = false;
    private boolean isBraking = false;
    float direction = 0f;
    private final TextButton.TextButtonStyle style;
    final TextButton throttle;
    final TextButton brake;
    final TextButton changeControls;
    final TextButton powerUp;
    private long lastMessage = 0;

    public ControlView(){
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        style = new TextButton.TextButtonStyle();
        style.font = ViewDefaults.getDefaultButtonFont();
        throttle = new TextButton(">", style);
        throttle.setDebug(true);
        throttle.setTransform(true);
        throttle.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isAccelerating = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isAccelerating = false;
                super.touchUp(event, x, y, pointer, button);
            }

        });

        brake = new TextButton("<", style);
        brake.setDebug(true);
        brake.setTransform(true);

        brake.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isBraking = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isBraking = false;
                super.touchUp(event, x, y, pointer, button);
            }

        });
        changeControls = new TextButton("Change", style);
        changeControls.setTransform(true);
        changeControls.setOrigin(Align.center);
        changeControls.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameModel.getInstance().getAccelerometer()) {
                    MarioKart.getInstance().setScreen(new ButtonControlView());
                    GameModel.getInstance().setAccelerometer(false);
                }
                else {
                    MarioKart.getInstance().setScreen(new AccelerometerControlView());
                    GameModel.getInstance().setAccelerometer(true);
                }
                super.clicked(event, x, y);
            }
        });

        powerUp = new TextButton("P", style);
        powerUp.setTransform(true);
        powerUp.setOrigin(Align.center);

        powerUp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //trigger power up
            }
        });

        this.stage.addActor(throttle);
        this.stage.addActor(brake);
        this.stage.addActor(changeControls);
        this.stage.addActor(powerUp);
    }

    @Override
    public void render(float delta) {
        if (System.currentTimeMillis() - this.lastMessage >= 50){
            GameController.getInstance().controllerMessage(this.isAccelerating, this.isBraking, this.direction);
            this.lastMessage = System.currentTimeMillis();
        }
        drawBackground();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }


}

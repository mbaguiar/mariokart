package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.GameModel;


public class ControlView extends ScreenAdapter {
    Stage stage;
    private boolean isAccelerating = false;
    private boolean isBraking = false;
    float direction = 0f;
    final TextButton throttle;
    final TextButton brake;
    final TextButton changeControls;
    final Image powerUp;
    private long lastMessage = 0;
    private SpriteDrawable[] powerUpSprites = new SpriteDrawable[2];

    ControlView() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        loadAssets();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = ViewDefaults.getDefaultButtonFont();
        style.up = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("button.png"))));
        style.down = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("button.png"))));

        throttle = new TextButton(">", style);
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

        TextButton.TextButtonStyle nobackground_style = new TextButton.TextButtonStyle();
        nobackground_style.font = ViewDefaults.getDefaultButtonFont();

        changeControls = new TextButton("Change", nobackground_style);
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

        powerUp = new Image(powerUpSprites[0]);
        powerUp.setSize(stage.getWidth()/4f, stage.getWidth()/4f);
        powerUp.setOrigin(Align.center);
        powerUp.setRotation(-90);

        powerUp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameModel.getInstance().getPowerUp() != GameModel.object_type.NULL){
                    GameController.getInstance().usePowerUp();
                    GameModel.getInstance().clearPowerUp();
                }
                super.clicked(event, x, y);
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
        updateActors();
    }

    private void drawBackground() {
        //Color c = GameModel.getInstance().getSelectedCharacter().getColor();
        Gdx.gl.glClearColor(0.5f, 1f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    private void loadAssets(){
        Texture t = new Texture(Gdx.files.internal("banana.png"));
        powerUpSprites[0] = new SpriteDrawable(new Sprite(t));
        t = new Texture(Gdx.files.internal("misterybox.png"));
        powerUpSprites[1] = new SpriteDrawable(new Sprite(t));
    }

    private void updateActors(){
        if (GameModel.getInstance().getPowerUp() == GameModel.object_type.NULL){
            powerUp.setVisible(false);
        } else {
            powerUp.setDrawable(powerUpSprites[GameModel.getInstance().getPowerUp().ordinal() - 1]);
            powerUp.setVisible(true);
        }
    }

}

package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;

public class ButtonControlView extends ScreenAdapter {

    private Stage stage;

    public ButtonControlView(){
        this.stage = new Stage();
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

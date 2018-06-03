package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;

/**
 * Class that represents the menu view
 *
 * @see ScreenAdapter
 */
public class MenuView extends ScreenAdapter {
    private Stage stage;

    /**
     * Initializes menu view
     */
    public MenuView() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = com.lpoo1718_t1g3.mariokart.View.ViewDefaults.getDefaultButtonFont();

        TextButton playBtn = new TextButton("Play", style);

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().goToConnection();
            }
        });

        table.add(playBtn).pad(50);

        this.stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        drawBackground();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(197 / 255f, 38 / 255f, 6 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

}


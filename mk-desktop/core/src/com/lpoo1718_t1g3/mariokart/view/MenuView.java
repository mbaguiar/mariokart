package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.controller.GameController;

/**
 * Class that represents the menu view
 */
public class MenuView extends ScreenAdapter {
    private Stage stage;
    private Sprite background;

    /**
     * Initializes a menu view
     */
    public MenuView() {

        loadAssests();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture texture = MarioKart.getInstance().getAssetManager().get("menubackground.jpg");
        background = new Sprite(texture);
        background.setSize(stage.getWidth(), stage.getHeight());


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;

        Table buttonGroup = new Table();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        parameter.size = 100;

        style.font = generator.generateFont(parameter);

        generator.dispose();

        TextButton playBtn = new TextButton("Play", style);
        TextButton instructionsBtn = new TextButton("Instructions", style);
        TextButton quitBtn = new TextButton("Quit", style);

        buttonGroup.add(playBtn).pad(30);
        buttonGroup.row();
        buttonGroup.add(instructionsBtn).pad(30);
        buttonGroup.row();
        buttonGroup.add(quitBtn).pad(30);

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().startLobbyScreen();
            }
        });

        buttonGroup.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f);
        stage.addActor(buttonGroup);

    }

    private void loadAssests() {
        MarioKart.getInstance().getAssetManager().load("menubackground.jpg", Texture.class);
        MarioKart.getInstance().getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        MarioKart.getInstance().getBatch().begin();
        drawBackground();
        MarioKart.getInstance().getBatch().end();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void drawBackground() {
        background.draw(MarioKart.getInstance().getBatch());
    }

}

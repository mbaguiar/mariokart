package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.Model.Character;
import com.lpoo1718_t1g3.mariokart.Model.GameModel;
import com.lpoo1718_t1g3.mariokart.controller.GameController;

public class CharacterPickerView extends ScreenAdapter {
    private Stage stage;

    public CharacterPickerView() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        parameter.size = 100;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        Label viewTitle = new Label("Pick your character", labelStyle);
        viewTitle.setPosition(stage.getWidth() / 2f, stage.getHeight() * 0.9f, Align.center);

        parameter.borderWidth = 2;
        parameter.size = 24;
        labelStyle.font = generator.generateFont(parameter);

        Label playerTurn = new Label("Player 1's turn to pick", labelStyle);
        playerTurn.setPosition(stage.getWidth() / 2f, stage.getHeight() - stage.getHeight() / 3f, Align.center);

        Table characters = new Table();
        characters.setDebug(true);
        characters.setPosition(stage.getWidth() / 2f, stage.getHeight() / 3f, Align.center);

        parameter.borderWidth = 4;
        parameter.size = 32;
        labelStyle.font = generator.generateFont(parameter);
        int i = 0;
        for (Character c : GameModel.getInstance().getCharacters()) {
            Label characterBox = new Label(c.getName(), labelStyle);
            characterBox.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //MarioKart.getInstance().setScreen(new TrackPickerView());
                }
            });
            characters.add(characterBox).pad(75);
            if ((i + 1) % 3 == 0 && i + 1 < GameModel.getInstance().getCharacters().size()) {
                characters.row();
            }
            i++;
        }


        generator.dispose();

        stage.addActor(viewTitle);
        stage.addActor(characters);
        stage.addActor(playerTurn);

    }

    @Override
    public void render(float delta) {
        drawBackground();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(1f, 1f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

}

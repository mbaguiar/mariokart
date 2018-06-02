package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.model.Character;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class CharacterPickerView extends ScreenAdapter {
    private Stage stage;
    private ArrayList<Image> charactersImages = new ArrayList<Image>();
    private Label playerTurn;
    private Table characters;
    private Label.LabelStyle labelStyle;

    public CharacterPickerView() {
        loadCharacters();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        parameter.size = 100;

        labelStyle = new Label.LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        Label viewTitle = new Label("Pick your character", labelStyle);
        viewTitle.setPosition(stage.getWidth() / 2f, stage.getHeight() * 0.9f, Align.center);

        parameter.borderWidth = 2;
        parameter.size = 24;
        labelStyle.font = generator.generateFont(parameter);

        playerTurn = new Label("", labelStyle);
        playerTurn.setPosition(stage.getWidth() / 2f, stage.getHeight() - stage.getHeight() * 0.3f, Align.center);

        characters = new Table();
        characters.setDebug(false);
        characters.setPosition(stage.getWidth() / 2f, stage.getHeight() * 0.35f, Align.center);

        parameter.borderWidth = 4;
        parameter.size = 32;
        labelStyle.font = generator.generateFont(parameter);

        generator.dispose();

        reloadTable();

        stage.addActor(viewTitle);
        stage.addActor(characters);
        stage.addActor(playerTurn);

    }

    private void reloadTable() {
        characters.clearChildren();
        for (int i = 0; i < GameModel.getInstance().getCharacters().size(); i++) {
            characters.add(charactersImages.get(i)).width(250).height(250).padBottom(0).padLeft(50).padRight(50);

            if ((i + 1) % 3 == 0 && i + 1 < GameModel.getInstance().getCharacters().size()) {
                characters.row();
                for (int t = i - 2; t < i; t++) {
                    Label characterLabel = new Label("", labelStyle);
                    for (Player player : GameModel.getInstance().getPlayers()) {
                        if (player.getSelectedCharacter() != null) {
                            if (player.getSelectedCharacter().getName().equals(GameModel.getInstance().getCharacters().get(t))) {
                                characterLabel = new Label(player.getPlayerHandle(), labelStyle);
                            }
                        }

                    }
                    characters.add(characterLabel).padBottom(10).padLeft(50).padRight(50);
                }
                characters.row();
            }

        }
    }

    private void reloadLabel() {
        int number = GameModel.getInstance().getCurrentPickerId();
        if (number != -1) {
            String name = GameModel.getInstance().getPlayer(number).getPlayerHandle();
            playerTurn.setText(name + "'s turn to pick!");
        }
    }

    private void loadCharacters() {
        Texture texture = new Texture(Gdx.files.internal("mario_circle.png"));
        Image image = new Image(texture);
        charactersImages.add(image);
        texture = new Texture(Gdx.files.internal("luigi_circle.png"));
        image = new Image(texture);
        charactersImages.add(image);
        texture = new Texture(Gdx.files.internal("peach_circle.png"));
        image = new Image(texture);
        charactersImages.add(image);
        texture = new Texture(Gdx.files.internal("toad_circle.png"));
        image = new Image(texture);
        charactersImages.add(image);
        texture = new Texture(Gdx.files.internal("yoshi_circle.png"));
        image = new Image(texture);
        charactersImages.add(image);
        texture = new Texture(Gdx.files.internal("bowser_circle.png"));
        image = new Image(texture);
        charactersImages.add(image);
    }

    @Override
    public void render(float delta) {
        reloadLabel();
        reloadTable();
        drawBackground();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(220/255f, 57/255f, 24/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

}

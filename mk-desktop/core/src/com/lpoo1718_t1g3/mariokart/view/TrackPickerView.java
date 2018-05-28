package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.controller.GameController;

public class TrackPickerView extends ScreenAdapter {
    private Stage stage;

    public TrackPickerView(){
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        parameter.size = 100;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        Label viewTitle = new Label("Pick the track", labelStyle);
        viewTitle.setPosition(stage.getWidth()/2f, stage.getHeight() * 0.9f, Align.center);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        parameter.size = 70;
        style.font = generator.generateFont(parameter);

        TextButton next = new TextButton("Race", style);
        next.setPosition(stage.getWidth()/2f, stage.getHeight()/2f, Align.center);

        next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().startRace();
            }
        });

        stage.addActor(viewTitle);
        stage.addActor(next);

        generator.dispose();
    }

    @Override
    public void render(float delta){
        drawBackground();
        stage.act();
        stage.draw();
        GameController.getInstance().update(delta);
    }

    private void drawBackground() {
        Gdx.gl.glClearColor( 1f, 0.5f, 0f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }
}

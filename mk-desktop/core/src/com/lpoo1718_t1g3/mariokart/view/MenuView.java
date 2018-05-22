package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lpoo1718_t1g3.mariokart.MarioKart;


public class MenuView extends ScreenAdapter {
    private Stage stage;

    public MenuView(){
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        Table buttonGroup = new Table();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.font = new BitmapFont();
        TextButton playBtn = new TextButton("Play", style);
        TextButton instructionsBtn = new TextButton("Instructions", style);
        TextButton quitBtn = new TextButton("Quit", style);

        buttonGroup.setDebug(true);

        buttonGroup.add(playBtn).pad(20);
        buttonGroup.row();
        buttonGroup.add(instructionsBtn).pad(20);
        buttonGroup.row();
        buttonGroup.add(quitBtn).pad(20);

        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Clicked play");
                MarioKart.getInstance().setScreen(new LobbyView());
            }
        });

        buttonGroup.setPosition(stage.getWidth()/2f, stage.getHeight()/2f);
        stage.addActor(buttonGroup);
    }

    @Override
    public void render (float delta) {
        drawBackground();
        stage.act();
        stage.draw();
    }

    private void drawBackground(){
        Gdx.gl.glClearColor( 0f, 1f, 0f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }

}

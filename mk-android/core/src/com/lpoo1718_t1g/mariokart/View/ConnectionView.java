package com.lpoo1718_t1g.mariokart.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g.mariokart.Controller.GameController;

public class ConnectionView extends ScreenAdapter {
    private Stage stage;

    public ConnectionView(){
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = ViewDefaults.getDefaultTextFieldFont();
        style.fontColor = Color.WHITE;

        final TextField partyIp = new TextField("", style);
        partyIp.setMessageText("Enter party ip");
        partyIp.setWidth(stage.getWidth()/1.3f);
        partyIp.setPosition(stage.getWidth()/2f, stage.getHeight() * 2f/3f, Align.center);
        partyIp.setAlignment(Align.center);

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = ViewDefaults.getDefaultButtonFont();

        TextButton connectBtn = new TextButton("Connect", btnStyle);
        connectBtn.setPosition(stage.getWidth()/2f, stage.getHeight()/3f, Align.center);

        connectBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().tryConnect(partyIp.getText());
            }
        });

        this.stage.addActor(partyIp);

        this.stage.addActor(connectBtn);


    }

    @Override
    public void render(float delta) {
        drawBackground();
        stage.act();
        stage.draw();
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(1f, 0f, 1f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }
}

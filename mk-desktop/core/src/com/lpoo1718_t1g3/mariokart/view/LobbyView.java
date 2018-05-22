package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class LobbyView extends ScreenAdapter {

    private Stage stage;
    private TextField partyName;

    public LobbyView(){
        this.stage = new Stage();

        Table group = new Table();

        group.setDebug(true);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = new BitmapFont();

        textFieldStyle.font = new BitmapFont();

        textFieldStyle.fontColor = new Color(1f, 1f, 1f, 1);

        textFieldStyle.focusedFontColor = new Color(250/255f, 1f, 1f, 1);

        Table partyNameGroup = new Table();

        partyNameGroup.setDebug(true);

        Gdx.input.setInputProcessor(stage);

        Label partyNameLabel = new Label("Party name", labelStyle);

        this.partyName = new TextField("MarioKart Party", textFieldStyle);

        this.partyName.setAlignment(Align.center);

        group.setPosition(stage.getWidth()/2f,stage.getHeight()/2f);

        partyNameGroup.add(partyNameLabel).pad(20);

        partyNameGroup.add(this.partyName).pad(20);

        Label ipLabel = new Label("000.000.000.0000:8888", labelStyle);

        group.add(partyNameGroup).padBottom(250);

        group.row();

        group.add(ipLabel).padTop(250);

        this.stage.addActor(group);

    }

    @Override
    public void render (float delta) {
        drawBackground();
        stage.draw();
    }

    private void drawBackground(){
        Gdx.gl.glClearColor( 0f, 0f, 1f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }

}

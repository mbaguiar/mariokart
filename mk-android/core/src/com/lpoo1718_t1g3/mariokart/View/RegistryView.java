package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.GameModel;

public class RegistryView extends ScreenAdapter {
    private Stage stage;

    public RegistryView() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle partyLabelStyle = new Label.LabelStyle();
        partyLabelStyle.font = com.lpoo1718_t1g3.mariokart.View.ViewDefaults.getDefaultTextFieldFont();

        Label partyNameLabel = new Label(GameModel.getInstance().getPartyName(), partyLabelStyle);
        partyNameLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() * 4f / 5f, Align.center);
        //partyNameLabel.setWidth(stage.getWidth()/1.5f);

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = com.lpoo1718_t1g3.mariokart.View.ViewDefaults.getDefaultButtonFont();

        TextButton disconnectBtn = new TextButton("Disconnect", btnStyle);
        disconnectBtn.setPosition(stage.getWidth() / 2f, stage.getHeight() * 3f / 5f, Align.center);

        TextField.TextFieldStyle txtFieldStyle = new TextField.TextFieldStyle();
        txtFieldStyle.font = com.lpoo1718_t1g3.mariokart.View.ViewDefaults.getDefaultTextFieldFont();
        txtFieldStyle.fontColor = Color.WHITE;

        final TextField playerHandle = new TextField("", txtFieldStyle);
        playerHandle.setWidth(stage.getWidth() / 1.5f);
        playerHandle.setMessageText("Enter your nickname");
        playerHandle.setPosition(stage.getWidth() / 2f, stage.getHeight() * 2f / 5f, Align.center);
        playerHandle.setAlignment(Align.center);
        playerHandle.setMaxLength(10);

        TextButton readyBtn = new TextButton("Ready", btnStyle);
        readyBtn.setPosition(stage.getWidth() / 2f, stage.getHeight() / 5f, Align.center);
        readyBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().tryRegister(playerHandle.getText());
            }
        });

        this.stage.addActor(partyNameLabel);
        this.stage.addActor(disconnectBtn);
        this.stage.addActor(playerHandle);
        this.stage.addActor(readyBtn);

    }

    @Override
    public void render(float delta) {
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

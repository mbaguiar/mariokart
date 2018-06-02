package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;
import com.lpoo1718_t1g3.mariokart.MarioKart;

public class ConnectionView extends ScreenAdapter {
    private Stage stage;

    public ConnectionView() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = com.lpoo1718_t1g3.mariokart.View.ViewDefaults.getDefaultTextFieldFont();
        style.fontColor = Color.WHITE;

        final TextField partyIp = new TextField("", style);
        partyIp.setMessageText("Enter party ip");
        partyIp.setWidth(stage.getWidth() / 1.3f);
        partyIp.setPosition(stage.getWidth() / 2f, stage.getHeight() * 3f / 4f, Align.center);
        partyIp.setAlignment(Align.center);

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = com.lpoo1718_t1g3.mariokart.View.ViewDefaults.getDefaultButtonFont();

        TextureRegionDrawable qrCodeDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("qr-code.png"))));

        Button.ButtonStyle qrCodeBtnStyle = new Button.ButtonStyle(qrCodeDrawable, qrCodeDrawable, qrCodeDrawable);

        Button scanQrCode = new Button(qrCodeBtnStyle);
        scanQrCode.setSize(stage.getWidth()/3f, stage.getWidth()/3f);
        scanQrCode.setPosition(stage.getWidth()/2f, stage.getHeight()/2f, Align.center);

        scanQrCode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MarioKart.getInstance().startScan();
                super.clicked(event, x, y);
            }
        });

        TextButton connectBtn = new TextButton("Connect", btnStyle);
        connectBtn.setPosition(stage.getWidth()/2f, stage.getHeight()/4f, Align.center);

        connectBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().tryConnect(partyIp.getText());
            }
        });

        this.stage.addActor(partyIp);
        this.stage.addActor(connectBtn);
        this.stage.addActor(scanQrCode);

    }

    @Override
    public void render(float delta) {
        drawBackground();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(197/255f, 38/255f, 6/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
}

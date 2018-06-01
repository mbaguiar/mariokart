package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.Model.GameModel;
import com.lpoo1718_t1g3.mariokart.Model.Player;

public class LobbyView extends ScreenAdapter {
    private Stage stage;
    private TextField partyName;
    private Table connectedPlayers;
    private Label.LabelStyle labelStyle;

    public LobbyView() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.setDebugAll(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        parameter.size = 100;

        labelStyle = new Label.LabelStyle();

        labelStyle.font = generator.generateFont(parameter);

        Label lobbyLabel = new Label("Lobby", labelStyle);

        lobbyLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() * 0.9f, Align.center);

        TextButton.TextButtonStyle playBtnStyle = new TextButton.TextButtonStyle();

        parameter.size = 40;

        playBtnStyle.font = generator.generateFont(parameter);

        TextButton playBtn = new TextButton("Play", playBtnStyle);

        playBtn.setPosition(stage.getWidth() / 2f, stage.getHeight() * 0.1f, Align.center);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();

        parameter.size = 24;

        labelStyle.font = generator.generateFont(parameter);

        textFieldStyle.font = new BitmapFont();

        textFieldStyle.fontColor = new Color(1f, 1f, 1f, 1);

        textFieldStyle.focusedFontColor = new Color(250 / 255f, 1f, 1f, 1);

        Table partyGroup = new Table();

        partyGroup.setDebug(true);

        partyGroup.setPosition(stage.getWidth() / 2f, stage.getHeight() * 0.7f, Align.center);

        Label partyNameLabel = new Label("Party name:", labelStyle);

        partyGroup.add(partyNameLabel);

        this.partyName = new TextField("MarioKart Party", textFieldStyle);

        this.partyName.setAlignment(Align.center);

        partyGroup.add(partyName).width(300);

        Texture qrCodeTexture = new Texture(Gdx.files.internal("qrcode/qrcode.png"));

        Image qrCode = new Image(qrCodeTexture);

        qrCode.setSize(qrCodeTexture.getWidth(), qrCodeTexture.getHeight());

        Label ipLabel = new Label(GameModel.getInstance().getIpAddress() + ":" + GameModel.getInstance().getPort(), labelStyle);

        ipLabel.setPosition(stage.getWidth()/2f, stage.getHeight()/2f - qrCode.getHeight(), Align.center);

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                GameController.getInstance().startCharPick();
            }
        });
        qrCode.setPosition(stage.getWidth()/2f, stage.getHeight()/2f, Align.center);

        parameter.size = 50;

        TextField.TextFieldStyle subtitleStyle = new TextField.TextFieldStyle();

        subtitleStyle.font = generator.generateFont(parameter);

        subtitleStyle.fontColor = new Color(1f, 1f, 1f, 1);

        TextArea connectedPlayersLabel = new TextArea("Connected\nPlayers", subtitleStyle);

        connectedPlayersLabel.setPosition(stage.getWidth() / 8f, stage.getHeight() * 0.7f, Align.center);
        connectedPlayersLabel.setAlignment(Align.center);
        connectedPlayersLabel.setWidth(350);
        connectedPlayersLabel.setHeight(100);

        connectedPlayers = new Table();
        connectedPlayers.setDebug(true);

        connectedPlayers.setPosition(stage.getWidth()/8f, stage.getHeight() * 0.6f, Align.top);

        reloadTable(labelStyle);

        this.stage.addActor(qrCode);
        this.stage.addActor(lobbyLabel);
        this.stage.addActor(partyGroup);
        this.stage.addActor(ipLabel);
        this.stage.addActor(playBtn);
        this.stage.addActor(connectedPlayersLabel);
        this.stage.addActor(connectedPlayers);
        generator.dispose();
    }

    private void reloadTable(Label.LabelStyle labelStyle) {
        connectedPlayers.clearChildren();
        int t = 0;
        for (Player p: GameModel.getInstance().getPlayers()){
            Label playerLabel = new Label(p.getPlayerHandle(), labelStyle);
            connectedPlayers.add(playerLabel).pad(20);
            t++;
            if (t < GameModel.getInstance().getPlayers().size()) connectedPlayers.row();
        }
    }

    @Override
    public void render (float delta) {
        reloadTable(this.labelStyle);
        drawBackground();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(0f, 0f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

}

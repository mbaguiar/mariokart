package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;

public class LobbyView extends ScreenAdapter {
    private Stage stage;
    private TextField partyName;
    private Table connectedPlayers;
    private Label.LabelStyle labelStyle;
    private Sprite background;
    private Image qrCode;
    private Label ipLabel;
    private TextArea connectedPlayersLabel;

    public LobbyView() {

        loadAssests();

        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        parameter.size = 100;
        Texture texture = MarioKart.getInstance().getAssetManager().get("mario_background.png");
        background = new Sprite(texture);
        background.setSize(stage.getWidth(), stage.getHeight());

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

        parameter.borderWidth = 2;

        parameter.size = 24;

        labelStyle.font = generator.generateFont(parameter);

        textFieldStyle.font = new BitmapFont();

        textFieldStyle.fontColor = new Color(1f, 1f, 1f, 1);

        textFieldStyle.focusedFontColor = new Color(250 / 255f, 1f, 1f, 1);

        Table partyGroup = new Table();

        partyGroup.setPosition(stage.getWidth() / 2f, stage.getHeight() * 0.7f, Align.center);

        Label partyNameLabel = new Label("Party name:", labelStyle);

        partyGroup.add(partyNameLabel);

        this.partyName = new TextField("Mario Kart Party", textFieldStyle);

        this.partyName.setAlignment(Align.center);

        partyGroup.add(partyName).width(300);

        TextButton.TextButtonStyle starLobbyStyle = new TextButton.TextButtonStyle();

        starLobbyStyle.font = generator.generateFont(parameter);

        TextButton startLobby = new TextButton("Start lobby", starLobbyStyle);

        startLobby.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (partyName.getText().equals("")) partyName.setText("Mario Kart Party");
                GameModel.getInstance().setPartyName(partyName.getText());
                GameController.getInstance().createServer();
                initServerActors();
                super.clicked(event, x, y);
            }
        });

        partyGroup.add(startLobby);


        //QR Code image
        qrCode = new Image();
        qrCode.setAlign(Align.center);
        qrCode.setVisible(false);

        //Server ip label
        ipLabel = new Label("", labelStyle);
        ipLabel.setAlignment(Align.center);
        ipLabel.setVisible(false);


        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController.getInstance().startCharPick();
            }
        });


        parameter.size = 50;

        TextField.TextFieldStyle subtitleStyle = new TextField.TextFieldStyle();

        subtitleStyle.font = generator.generateFont(parameter);

        subtitleStyle.fontColor = new Color(1f, 1f, 1f, 1);

        connectedPlayersLabel = new TextArea("Connected\nPlayers", subtitleStyle);
        connectedPlayersLabel.setAlignment(Align.center);
        connectedPlayersLabel.setVisible(false);

        connectedPlayersLabel.setPosition(stage.getWidth() / 8f, stage.getHeight() * 0.7f, Align.center);
        connectedPlayersLabel.setAlignment(Align.center);
        connectedPlayersLabel.setWidth(350);
        connectedPlayersLabel.setHeight(100);

        connectedPlayers = new Table();

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
            connectedPlayers.add(playerLabel).pad(20).bottom();
            t++;
            if (t < GameModel.getInstance().getPlayers().size()) connectedPlayers.row();
        }
    }

    private void loadAssests() {
        MarioKart.getInstance().getAssetManager().load("mario_background.png", Texture.class);
        MarioKart.getInstance().getAssetManager().finishLoading();
    }

    @Override
    public void render (float delta) {
        reloadTable(this.labelStyle);
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

    private void initServerActors(){
        Texture qrCodeTexture = new Texture(Gdx.files.internal("qrcode/qrcode.png"));
        qrCode.setSize(qrCodeTexture.getWidth(), qrCodeTexture.getHeight());
        qrCode.setDrawable(new SpriteDrawable(new Sprite(qrCodeTexture)));
        qrCode.setPosition(stage.getWidth()/2f, stage.getHeight()/2f, Align.center);
        qrCode.setVisible(true);
        ipLabel.setText(GameModel.getInstance().getIpAddress() + ":" + GameModel.getInstance().getPort());
        ipLabel.setPosition(stage.getWidth()/2f, stage.getHeight()/2f - qrCode.getHeight(), Align.center);
        ipLabel.setVisible(true);
        connectedPlayersLabel.setVisible(true);

    }

}

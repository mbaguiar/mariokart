package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.Position;
import com.lpoo1718_t1g3.mariokart.model.Race;
import com.lpoo1718_t1g3.mariokart.model.entities.BananaModel;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.FakeMysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.view.entities.*;

import java.util.HashMap;

/**
 * Class that represents the Race view
 * @see ScreenAdapter
 */
public class RaceView extends ScreenAdapter {

    private HashMap<String, KartView> kartViews = new HashMap<String, KartView>();
    private HashMap<String, EntityView> objectViews = new HashMap<String, EntityView>();
    private MysteryBoxView mysteryBoxView;
    private TrackView trackView;
    private OrthographicCamera camera;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelStyleSmall;
    private Label.LabelStyle labelStyleBig;
    private Label raceLabel;
    private Table connectedPlayers;
    private HashMap<Integer, Image> place_numbers = new HashMap<Integer, Image>();
    private HashMap<String, Image> characters_symbols = new HashMap<String, Image>();

    public static final float PIXEL_TO_METER = 0.04f;
    public static final float VIEWPORT_WIDTH = 76.8f;
    public static final float VIEWPORT_HEIGHT = 43.2f;

    private Stage stage;

    Box2DDebugRenderer debugRenderer;

    /**
     * Initializes a race view
     */
    public RaceView() {
        loadAssets();
        trackView = new TrackView();
        mysteryBoxView = new MysteryBoxView();
        camera = createCamera();
        initKartViews();
        initObjectViews();

        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.setDebugAll(false);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        parameter.size = 100;

        labelStyle = new Label.LabelStyle();
        labelStyle.font = generator.generateFont(parameter);
        Label lobbyLabel = new Label("Players", labelStyle);

        lobbyLabel.setPosition((stage.getWidth() + stage.getHeight())/2f, stage.getHeight() * 0.9f, Align.center);
        setUpPlaceNumbers();
        setUpCharacterSymbols();

        connectedPlayers = new Table();
        connectedPlayers.setDebug(false);


        connectedPlayers.setPosition((stage.getWidth() + stage.getHeight())/2f, stage.getHeight() * 0.45f, Align.top);

        stage.addActor(connectedPlayers);
        stage.addActor(lobbyLabel);

        parameter.size = 40;
        labelStyle.font = generator.generateFont(parameter);

        parameter.size = 30;
        parameter.color = Color.GOLD;
        parameter.borderColor = Color.BROWN;
        parameter.borderWidth = 4;

        labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = generator.generateFont(parameter);

        parameter.size = 130;
        parameter.color = Color.GOLD;
        parameter.borderColor = Color.BROWN;
        parameter.borderWidth = 5;

        labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = generator.generateFont(parameter);

        raceLabel = new Label("3", labelStyleBig);
        raceLabel.setPosition(stage.getHeight() / 2, stage.getHeight() / 2, Align.center);
        raceLabel.setAlignment(Align.center);

        stage.addActor(raceLabel);

        debugRenderer = new Box2DDebugRenderer(true, true, false, true, true, true);

    }

    private void setUpCharacterSymbols() {
        Texture texture = new Texture(Gdx.files.internal("mario_symbol.png"));
        Image image = new Image(texture);
        characters_symbols.put("Mario", image);
        texture = new Texture(Gdx.files.internal("luigi_symbol.png"));
        image = new Image(texture);
        characters_symbols.put("Luigi", image);
        texture = new Texture(Gdx.files.internal("peach_symbol.png"));
        image = new Image(texture);
        characters_symbols.put("Peach", image);
        texture = new Texture(Gdx.files.internal("toad_symbol.png"));
        image = new Image(texture);
        characters_symbols.put("Toad", image);
        texture = new Texture(Gdx.files.internal("yoshi_symbol.png"));
        image = new Image(texture);
        characters_symbols.put("Yoshi", image);
        texture = new Texture(Gdx.files.internal("bowser_symbol.png"));
        image = new Image(texture);
        characters_symbols.put("Bowser", image);
    }

    private void setUpPlaceNumbers() {
        Texture texture = new Texture(Gdx.files.internal("1_place.png"));
        Image image = new Image(texture);
        place_numbers.put(1, image);
        texture = new Texture(Gdx.files.internal("2_place.png"));
        image = new Image(texture);
        place_numbers.put(2, image);
        texture = new Texture(Gdx.files.internal("3_place.png"));
        image = new Image(texture);
        place_numbers.put(3, image);
        texture = new Texture(Gdx.files.internal("4_place.png"));
        image = new Image(texture);
        place_numbers.put(4, image);
        texture = new Texture(Gdx.files.internal("5_place.png"));
        image = new Image(texture);
        place_numbers.put(5, image);
        texture = new Texture(Gdx.files.internal("6_place.png"));
        image = new Image(texture);
        place_numbers.put(6, image);

    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    private void loadAssets() {
        MarioKart.getInstance().getAssetManager().load("banana.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("luigikart.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("mysteryBox.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("mariokart.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("peachkart.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("toadkart.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("yoshikart.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("bowserkart.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("track1.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("mysteryBox2.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("fakeMysteryBox.png", Texture.class);
        MarioKart.getInstance().getAssetManager().finishLoading();
    }

    private void reloadTable(Label.LabelStyle labelStyle, Label.LabelStyle labelStyleSmall) {

        connectedPlayers.clearChildren();
        int t = 1;
        for (Position p : GameModel.getInstance().getCurrentRace().getPlayerPositions()) {
            Player player = GameModel.getInstance().getPlayer(p.playerId);
            if (player != null){
                Label playerLabel = new Label(player.getPlayerHandle(), labelStyle);
                connectedPlayers.add(place_numbers.get(t)).width(70).height(70).pad(20).padBottom(0);
                connectedPlayers.add(playerLabel).pad(20).padBottom(0);
                connectedPlayers.add(characters_symbols.get(player.getSelectedCharacter().getName())).width(70).height(70).padBottom(0);
                t++;
                connectedPlayers.row();
                playerLabel = new Label("", labelStyle);
                connectedPlayers.add(playerLabel).padBottom(0);
                playerLabel = new Label(p.description, labelStyleSmall);
                connectedPlayers.add(playerLabel).padBottom(0);
                connectedPlayers.row();
            }
        }
    }

    @Override
    public void render(float delta) {

        GameController.getInstance().getRaceController().removeFlagged();

        if (GameModel.getInstance().getCurrentRace().getState() != Race.race_state.OVER) reloadTable(this.labelStyle, this.labelStyleSmall);

        GameController.getInstance().updateRaceController(delta);
        camera.update();
        MarioKart.getInstance().getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(96 / 255f,96 / 255f, 96 / 255f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        MarioKart.getInstance().getBatch().begin();
        drawEntities();
        MarioKart.getInstance().getBatch().end();

        reloadRaceLabel();

        stage.act();
        stage.draw();

        GameController.getInstance().updateStatus();
        //debugRenderer.render(GameController.getInstance().getRaceController().getWorld(), camera.combined);
    }

    private void initKartViews() {
        kartViews.put("Mario", new KartView("mariokart.png"));
        kartViews.put("Luigi", new KartView("luigikart.png"));
        kartViews.put("Peach", new KartView("peachkart.png"));
        kartViews.put("Toad", new KartView("toadkart.png"));
        kartViews.put("Yoshi", new KartView("yoshikart.png"));
        kartViews.put("Bowser", new KartView("bowserkart.png"));

    }

    private void initObjectViews() {
        objectViews.put("Banana", new BananaView());
        objectViews.put("FakeBox", new FakeMysteryBoxView());
    }

    private void drawEntities() {
        trackView.draw(MarioKart.getInstance().getBatch());
        for (MysteryBoxModel box : GameModel.getInstance().getCurrentRace().getTrack().getBoxes()) {
            if (box.isEnable()) {
                mysteryBoxView.update(box);
                mysteryBoxView.draw(MarioKart.getInstance().getBatch());
            }
        }

        for (Player player : GameModel.getInstance().getPlayers()) {
            KartView kartView = kartViews.get(player.getSelectedCharacter().getName());
            kartView.update(player.getKartModel());
            kartView.draw(MarioKart.getInstance().getBatch());
        }

        for (EntityModel object : GameModel.getInstance().getCurrentRace().getTrack().getObjects()) {
            if (object instanceof BananaModel) {
                if (!((BananaModel) object).isToDelete()) {

                    EntityView objView = objectViews.get("Banana");
                    objView.update(object);
                    objView.draw(MarioKart.getInstance().getBatch());
                }
            }

            if (object instanceof FakeMysteryBoxModel) {
                if (!((FakeMysteryBoxModel) object).isToDelete()) {
                    EntityView objView = objectViews.get("FakeBox");
                    objView.update(object);
                    objView.draw(MarioKart.getInstance().getBatch());
                }
            }
        }
    }

    private void reloadRaceLabel() {
        switch (GameModel.getInstance().getCurrentRace().getState()) {
            case READY:
                raceLabel.setText("3");
                break;
            case SET:
                raceLabel.setText("2");
                break;
            case GO:
                raceLabel.setText("1");
                break;
            case RACE:
                raceLabel.setText("");
                break;
            case OVER:
                raceLabel.setText("Race Over!");
                GameController.getInstance().restart();
                break;
        }
    }

}

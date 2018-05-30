package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.entities.BananaModel;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.view.entities.EntityView;
import com.lpoo1718_t1g3.mariokart.view.entities.KartView;
import com.lpoo1718_t1g3.mariokart.view.entities.MysteryBoxView;
import com.lpoo1718_t1g3.mariokart.view.entities.TrackView;

import java.util.ArrayList;
import java.util.HashMap;

public class RaceView extends ScreenAdapter {

    private HashMap<String, KartView> kartViews = new HashMap<String, KartView>();
    private HashMap<String, EntityView> objectViews = new HashMap<String, EntityView>();
    private MysteryBoxView mysteryBoxView;
    private TrackView trackView;
    private OrthographicCamera camera;
    public static final float PIXEL_TO_METER = 0.04f;
    public static final float VIEWPORT_WIDTH = 76.8f;
    public static final float VIEWPORT_HEIGHT = 43.2f;
    boolean mario = false, luigi = false;

    Box2DDebugRenderer debugRenderer;

    public RaceView() {
        loadAssets();
        trackView = new TrackView();
        mysteryBoxView = new MysteryBoxView();
        camera = createCamera();
        initKartViews();
        initObjectViews();
        Box2D.init();
        debugRenderer = new Box2DDebugRenderer();

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
        MarioKart.getInstance().getAssetManager().load("track1.png", Texture.class);
        MarioKart.getInstance().getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        handleInputs(delta);

        GameController.getInstance().update(delta);

        //camera.position.set(GameModel.getInstance().getKart().getX(), GameModel.getInstance().getKart().getY(), 0);
        //camera.position.set(GameModel.getInstance().getTrack1().getX(), GameModel.getInstance().getTrack1().getY(), 0);
        camera.update();
        MarioKart.getInstance().getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        MarioKart.getInstance().getBatch().begin();
        drawEntities();
        MarioKart.getInstance().getBatch().end();

        debugRenderer.render(GameController.getInstance().getRaceController().getWorld(), camera.combined);
    }

    private void handleInputs(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            GameController.getInstance().getRaceController().setKartState(KartBody.acc_type.ACC_ACCELERATE, 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            GameController.getInstance().getRaceController().setKartState(KartBody.acc_type.ACC_BRAKE, 1);
        } else {
            GameController.getInstance().getRaceController().setKartState(KartBody.acc_type.ACC_NONE, 1);
        }


        if (Gdx.input.isKeyPressed((Input.Keys.A))) {
            GameController.getInstance().getRaceController().setKartState(KartBody.steer_type.STEER_LEFT, 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            GameController.getInstance().getRaceController().setKartState(KartBody.steer_type.STEER_HARD_RIGHT, 1);
        } else {
            GameController.getInstance().getRaceController().setKartState(KartBody.steer_type.STEER_NONE, 1);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            GameController.getInstance().getRaceController().setKartState(KartBody.acc_type.ACC_ACCELERATE, 2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            GameController.getInstance().getRaceController().setKartState(KartBody.acc_type.ACC_BRAKE, 2);
        } else {
            GameController.getInstance().getRaceController().setKartState(KartBody.acc_type.ACC_NONE, 2);
        }

        if (Gdx.input.isKeyPressed((Input.Keys.LEFT))) {
            GameController.getInstance().getRaceController().setKartState(KartBody.steer_type.STEER_LEFT, 2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            GameController.getInstance().getRaceController().setKartState(KartBody.steer_type.STEER_HARD_RIGHT, 2);
        } else {
            GameController.getInstance().getRaceController().setKartState(KartBody.steer_type.STEER_NONE, 2);
        }

        //eventualmente desaparecer

        if (Gdx.input.isKeyPressed(((Input.Keys.M)))) {
            if (!this.mario) {
                GameModel.getInstance().addPlayer(1, "mbaguiar", "Mario");
                GameController.getInstance().getRaceController().addKartBody(GameModel.getInstance().getPlayer(1));
                this.mario = true;
                System.out.println("Added 1 Mariio");
            }
        }

        if (Gdx.input.isKeyPressed(((Input.Keys.L)))) {
            if (!this.luigi) {
                GameModel.getInstance().addPlayer(2, "tfragoso", "Luigi");
                GameController.getInstance().getRaceController().addKartBody(GameModel.getInstance().getPlayer(2));
                System.out.println("Added 1 Luigi");
                this.luigi = true;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            GameController.getInstance().getRaceController().useObject(1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            GameController.getInstance().getRaceController().useObject(2);
        }

    }

    public void initKartViews() {
        kartViews.put("Mario", new KartView("mariokart.png"));
        kartViews.put("Luigi", new KartView("luigikart.png"));

    }

    public void initObjectViews() {
        objectViews.put("Banana", new KartView("banana.png"));
    }

    private void drawEntities() {
        trackView.draw(MarioKart.getInstance().getBatch());
        for (MysteryBoxModel box : GameModel.getInstance().getTrack1().getBoxes()) {
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

        for (EntityModel object : GameModel.getInstance().getTrack1().getObjects()) {
            if (object instanceof BananaModel) {
                EntityView objView = objectViews.get("Banana");
                objView.update(object);
                objView.draw(MarioKart.getInstance().getBatch());
            }
        }
    }

}

package com.lpoo1718_t1g3.mariokart.view;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.view.entities.KartView;
import com.lpoo1718_t1g3.mariokart.view.entities.TrackView;

public class GameView extends ScreenAdapter {

    private static GameView ourInstance = null;

    public static GameView getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameView();
        }
        return ourInstance;
    }
    private KartView kartView;
    private TrackView trackView;
    private OrthographicCamera camera;
    public static final float PIXEL_TO_METER = 0.04f;
    public static final float VIEWPORT_WIDTH = 40.96f;

    private GameView() {
        loadAssets();
        kartView = new KartView();
        trackView = new TrackView();
        camera = createCamera();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH/PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    private void loadAssets() {
        MarioKart.getInstance().getAssetManager().load( "mariokart.png" , Texture.class);
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

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        MarioKart.getInstance().getBatch().begin();
        drawEntities();
        MarioKart.getInstance().getBatch().end();
    }

    private void handleInputs(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            GameController.getInstance().setKartState(KartBody.acc_type.ACC_ACCELERATE);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            GameController.getInstance().setKartState(KartBody.acc_type.ACC_BRAKE);
        } else {
            GameController.getInstance().setKartState(KartBody.acc_type.ACC_NONE);
        }


        if (Gdx.input.isKeyPressed((Input.Keys.A))) {
            GameController.getInstance().setKartState(KartBody.steer_type.STEER_LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            GameController.getInstance().setKartState(KartBody.steer_type.STEER_HARD_RIGHT);
        } else {
            GameController.getInstance().setKartState(KartBody.steer_type.STEER_NONE);
        }

    }

    private void drawEntities() {
        kartView.update(GameModel.getInstance().getKart());
        trackView.draw(MarioKart.getInstance().getBatch());
        kartView.draw(MarioKart.getInstance().getBatch());
    }
}

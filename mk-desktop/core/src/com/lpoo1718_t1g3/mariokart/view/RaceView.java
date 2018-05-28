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
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.view.entities.KartView;
import com.lpoo1718_t1g3.mariokart.view.entities.MysteryBoxView;
import com.lpoo1718_t1g3.mariokart.view.entities.TrackView;

import java.util.ArrayList;

public class RaceView extends ScreenAdapter {

    private ArrayList<KartView> kartViews = new ArrayList<KartView>();
    private MysteryBoxView mysteryBoxView;
    private TrackView trackView;
    private OrthographicCamera camera;
    public static final float PIXEL_TO_METER = 0.04f;
    public static final float VIEWPORT_WIDTH = 40.96f;

    public RaceView() {
        loadAssets();
        trackView = new TrackView();
        mysteryBoxView = new MysteryBoxView();
        camera = createCamera();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH/PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    private void loadAssets() {
        MarioKart.getInstance().getAssetManager().load("luigikart.png", Texture.class);
        MarioKart.getInstance().getAssetManager().load("mysteryBox.png", Texture.class);
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

        if (Gdx.input.isKeyPressed(((Input.Keys.M)))) {
            GameModel.getInstance().addPlayer(1, "mbaguiar" , "Mario");
        }

        if (Gdx.input.isKeyPressed(((Input.Keys.L)))) {
            GameModel.getInstance().addPlayer(2, "tjfragoso", "Luigi");
        }

    }

    private void drawEntities() {
        trackView.draw(MarioKart.getInstance().getBatch());
        for (MysteryBoxModel box : GameModel.getInstance().getTrack1().getBoxes()) {
            mysteryBoxView.update(box);
            mysteryBoxView.draw(MarioKart.getInstance().getBatch());
        }

        for (int i = 0; i < kartViews.size(); i++) {
            kartViews.get(i).update(GameModel.getInstance().getPlayers().get(i).getKartModel());
            kartViews.get(i).draw(MarioKart.getInstance().getBatch());
        }
    }

    public void addKartView(Player player) {
        kartViews.add(new KartView(player.getSelectedCharacter().getFileName()));
    }
}

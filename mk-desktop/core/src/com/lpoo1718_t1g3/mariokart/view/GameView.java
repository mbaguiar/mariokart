package com.lpoo1718_t1g3.mariokart.view;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.view.entities.KartView;

public class GameView extends ScreenAdapter {

    private static GameView ourInstance = null;

    public static GameView getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameView();
        }
        return ourInstance;
    }
    private KartView kartView;
    private OrthographicCamera camera;
    public static final float PIXEL_TO_METER = 0.04f;

    private GameView() {
        loadAssets();
        kartView = new KartView();
        camera = createCamera();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(500, 500* ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    private void loadAssets() {
        MarioKart.getInstance().getAssetManager().load( "mariokart.png" , Texture.class);
        MarioKart.getInstance().getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        handleInputs(delta);

        GameController.getInstance().update(delta);

        camera.position.set(GameModel.getInstance().getKart().getX(), GameModel.getInstance().getKart().getY(), 0);
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
            GameController.getInstance().accelerate();
        }


        if (Gdx.input.isKeyPressed((Input.Keys.A))) {
            GameController.getInstance().rotateLeft();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            GameController.getInstance().rotateRight();
        }

    }

    private void drawEntities() {
        kartView.update(GameModel.getInstance().getKart());
        kartView.draw(MarioKart.getInstance().getBatch());
    }
}

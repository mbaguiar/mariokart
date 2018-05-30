package com.lpoo1718_t1g3.mariokart;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo1718_t1g3.mariokart.View.ButtonControlView;

public class MarioKart extends Game {
    private static MarioKart ourInstance = new MarioKart();
    private SpriteBatch batch;
    private AssetManager assetManager;

    public static MarioKart getInstance() {
        if (ourInstance == null) {
            ourInstance = new MarioKart();
        }
        return ourInstance;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        startGame();
    }

    private void startGame() {
        setScreen(new ButtonControlView());
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }
}

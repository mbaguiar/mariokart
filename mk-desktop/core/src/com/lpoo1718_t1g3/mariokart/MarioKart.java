package com.lpoo1718_t1g3.mariokart;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo1718_t1g3.mariokart.view.MenuView;

/**
 * Class that represents the Game
 * @see com.badlogic.gdx.Game
 */
public class MarioKart extends com.badlogic.gdx.Game {
    private static MarioKart ourInstance = null;
    private SpriteBatch batch;
    private AssetManager assetManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        startGame();
    }

    private void startGame() {
        setScreen(new MenuView());
    }

    /**
     * Gets game batch
     * @return Returns game batch
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Gets game asset manager
     * @return Returns asset manager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }


    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }

    /**
     * Gets mario kart game
     * @return Returns current instance of MarioKart
     */
    public static MarioKart getInstance() {
        if (ourInstance == null) {
            ourInstance = new MarioKart();
        }
        return ourInstance;
    }
}

package com.lpoo1718_t1g3.mariokart;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo1718_t1g3.QRCodeUtils.QRCodeIntegrator;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;
import com.lpoo1718_t1g3.mariokart.View.MenuView;

public class MarioKart extends Game {
    private static MarioKart ourInstance = new MarioKart();
    private SpriteBatch batch;
    private AssetManager assetManager;
    private QRCodeIntegrator qrCodeIntegrator;

    public static MarioKart getInstance() {
        if (ourInstance == null) {
            ourInstance = new MarioKart();
        }
        return ourInstance;
    }

    public QRCodeIntegrator getCallback() {
        return qrCodeIntegrator;
    }

    public void setCallback(QRCodeIntegrator callback) {
        this.qrCodeIntegrator = callback;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        startGame();
    }

    private void startGame() {
        setScreen(new MenuView());
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

    public void startScan(){
        this.qrCodeIntegrator.startScanner();
    }

    public void scanResult(String contents) {
        GameController.getInstance().tryConnect(contents);
    }
}

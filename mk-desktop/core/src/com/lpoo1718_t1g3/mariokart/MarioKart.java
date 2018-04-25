package com.lpoo1718_t1g3.mariokart;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo1718_t1g3.mariokart.view.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MarioKart extends com.badlogic.gdx.Game {
    private static MarioKart ourInstance = null;
    SpriteBatch batch;
    private AssetManager assetManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        startGame();
    }

    private void startGame() {
        setScreen(GameView.getInstance());


    }

/*
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ServerSocket serverSocket;
        Socket clientSocket;
        BufferedReader in;
        try {
            serverSocket = new ServerSocket(4444);
            clientSocket = serverSocket.accept();
            System.out.println("Accepted client");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    */


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

    public static MarioKart getInstance() {
        if (ourInstance == null) {
            ourInstance = new MarioKart();
        }
        return ourInstance;
    }
}

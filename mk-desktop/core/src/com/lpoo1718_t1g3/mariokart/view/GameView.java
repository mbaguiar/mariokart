package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.ScreenAdapter;

public class GameView extends ScreenAdapter {

    private static GameView ourInstance = null;

    public static GameView getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameView();
        }
        return ourInstance;
    }

}

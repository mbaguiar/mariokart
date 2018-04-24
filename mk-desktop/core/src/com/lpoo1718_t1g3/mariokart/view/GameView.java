package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.ScreenAdapter;

public class GameView extends ScreenAdapter {
    private static GameView ourInstance = new GameView();

    public static GameView getInstance() {
        return ourInstance;
    }

    private GameView() {
    }
}

package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;

public class GameModel {

    private static GameModel ourInstance = new GameModel();
    private KartModel kart;

    public GameModel() {
        kart = new KartModel(0, 0, 0);
    }

    public KartModel getKart() {
        return kart;
    }

    public static GameModel getInstance() {
        return ourInstance;

    }
}

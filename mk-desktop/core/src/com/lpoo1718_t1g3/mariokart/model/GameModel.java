package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;

public class GameModel {

    private static GameModel ourInstance = new GameModel();
    private KartModel kart;
    private TrackModel track1;

    private GameModel() {
        kart = new KartModel(0, 0, 0);
        track1 = new TrackModel(-24, -16, 0);
    }

    public KartModel getKart() {
        return kart;
    }

    public static GameModel getInstance() {
        return ourInstance;

    }

    public TrackModel getTrack1() {
        return track1;
    }
}

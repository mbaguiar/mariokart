package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;

public class Position {

    public int laps;
    public long time;
    public int playerId;

    Position(int playerId) {
        this.laps = -1;
        this.time = 0;
        this.playerId = playerId;
    }


    public void incLaps() {
        this.laps++;
        this.time = System.currentTimeMillis();
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean isFinished() {
        return laps == TrackModel.LAPS;
    }
}

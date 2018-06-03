package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;

/**
 * Class that represents a player's position in a race
 */
public class Position {

    public int laps;
    public long time;
    public int playerId;
    public String description;

    /**
     * Constructs and initializes a position for the player with the given id
     * @param playerId player's id
     */
    Position(int playerId) {
        this.laps = -1;
        this.time = 0;
        this.playerId = playerId;
        this.description = "0/" + TrackModel.LAPS;
    }


    /**
     * Increments the number of laps concluded
     */
    public void incLaps() {
        this.laps++;
        this.time = System.currentTimeMillis();
        description = laps+1 + "/" + TrackModel.LAPS;
        if (isFinished()) {
            description = "Finished!";
        }
    }

    /**
     * Gets position player id
     * @return Returns playerId
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Checks if the player has finished the race
     * @return Returns true if the player has finished the race and false otherwise
     */
    public boolean isFinished() {
        return laps == TrackModel.LAPS;
    }
}

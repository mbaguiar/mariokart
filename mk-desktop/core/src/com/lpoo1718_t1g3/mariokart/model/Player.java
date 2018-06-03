package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;

/**
 * Class that represents a player in the game
 */
public class Player {

    private int playerId;
    private String playerHandle;
    private Character selectedCharacter;
    private KartModel kartModel;
    private Position position;

    /**
     * Constructs a player with the given id and the given handle
     * @param playerId player id
     * @param playerHandle player handle
     */
    public Player(int playerId, String playerHandle) {
        this.playerId = playerId;
        this.playerHandle = playerHandle;
        this.kartModel = new KartModel(5, 5, 90, playerId);
        this.position = new com.lpoo1718_t1g3.mariokart.model.Position(playerId);
    }

    /**
     * Gets player id
     * @return Returns player id
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets player handle
     * @return Returns player handle
     */
    public String getPlayerHandle() {
        return playerHandle;
    }

    /**
     * Gets player kart
     * @return Returns player KartModel
     */
    public KartModel getKartModel() {
        return kartModel;
    }

    /**
     * Gets player's selected character
     * @return Returns player's selected Character
     */
    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    /**
     * Sets player's selected character
     * @param selectedCharacter Character to associate with the player
     */
    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    /**
     * Returns players position in the current race
     * @return Returns player's Position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Resets player's position
     */
    public void resetPosition() {
        this.position.laps = -1;
        this.position.time = 0;
    }
}

package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;

public class Player {

    private int playerId;
    private String playerHandle;
    private Character selectedCharacter;
    private KartModel kartModel;
    private Position position;

    Player(int playerId, String playerHandle) {
        this.playerId = playerId;
        this.playerHandle = playerHandle;
        this.kartModel = new KartModel(5, 5, 90, playerId);
        this.position = new com.lpoo1718_t1g3.mariokart.model.Position(playerId);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerHandle() {
        return playerHandle;
    }

    public void setPlayerHandle(String playerHandle) {
        this.playerHandle = playerHandle;
    }

    public KartModel getKartModel() {
        return kartModel;
    }

    public void setKartModel(KartModel k){
        this.kartModel = k;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public Position getPosition() {
        return position;
    }

    public void resetPosition() {
        this.position.laps = -1;
        this.position.time = 0;
    }
}

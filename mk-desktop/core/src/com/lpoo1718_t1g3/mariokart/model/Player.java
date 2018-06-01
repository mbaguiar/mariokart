package com.lpoo1718_t1g3.mariokart.Model;

import com.lpoo1718_t1g3.mariokart.Model.entities.KartModel;

public class Player {

    private int playerId;
    private String playerHandle;
    private Character selectedCharacter;
    private KartModel kartModel;

    Player(int playerId, String playerHandle) {
        this.playerId = playerId;
        this.playerHandle = playerHandle;
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


}

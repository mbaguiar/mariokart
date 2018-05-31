package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;

import java.util.Random;

public class Player {

    private int playerId;
    private String playerHandle;
    private Character selectedCharacter;
    private KartModel kartModel;

    Player(int playerId, String playerHandle) {
        this.playerId = playerId;
        this.playerHandle = playerHandle;
    }

    Player(int playerId, String playerHandle, Character selectedCharacter) {
        this(playerId, playerHandle);
        this.selectedCharacter = selectedCharacter;
        kartModel = new KartModel(5, 5, 90, playerId);
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

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }


}

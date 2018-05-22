package com.lpoo1718_t1g3.mariokart.model;

public class Player {

    private int playerId;

    private String playerHandle;

    //private Character selectedCharacter;

    Player(int playerId, String playerHandle){
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
}

package com.lpoo1718_t1g.mariokart.Model;

public class GameModel {
    private static final GameModel ourInstance = new GameModel();

    private String partyName;

    private GameModel() {
    }

    public static GameModel getInstance() {
        return ourInstance;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}

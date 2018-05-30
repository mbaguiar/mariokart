package com.lpoo1718_t1g3.mariokart.Model;

public class GameModel {

    public game_screen getNextScreen() {
        return nextScreen;
    }

    public void setNextScreen(game_screen nextScreen) {
        this.nextScreen = nextScreen;
    }

    private Boolean accelerometer = false;

    public Boolean getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(Boolean accelerometer) {
        this.accelerometer = accelerometer;
    }

    public enum game_screen{ MENU, CONNECTION, REGISTRY, CONTROL }

    private static final GameModel ourInstance = new GameModel();

    private String partyName;

    private game_screen nextScreen;

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

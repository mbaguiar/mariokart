package com.lpoo1718_t1g3.mariokart.model;


import com.badlogic.gdx.Game;
import com.lpoo1718_t1g3.mariokart.networking.Message;

import java.util.ArrayList;

public class GameModel {

    private static final GameModel ourInstance = new GameModel();
    private ArrayList<Character> characters = new ArrayList<>();
    private Boolean accelerometer = false;
    private String partyName;
    private game_screen nextScreen;
    private Message.char_pick_state pickState;
    private Character selectedCharacter;
    private String playerHandle;
    private int selectedCharacterIndex;
    private object_type powerUp = object_type.NULL;

    private GameModel() {
    }

    public static GameModel getInstance() {
        return ourInstance;
    }

    public game_screen getNextScreen() {
        return nextScreen;
    }

    public void setNextScreen(game_screen nextScreen) {
        this.nextScreen = nextScreen;
    }

    public Boolean getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(Boolean accelerometer) {
        this.accelerometer = accelerometer;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPlayerHandle() {
        return playerHandle;
    }

    public void setPlayerHandle(String playerHandle) {
        this.playerHandle = playerHandle;
    }

    public Message.char_pick_state getPickState() {
        return pickState;
    }

    public void setPickState(Message.char_pick_state pickState) {
        this.pickState = pickState;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public void setUnavailable(int index){
        this.characters.get(index).setAvailable(false);
    }

    public int getSelectedCharacterIndex() {
        return selectedCharacterIndex;
    }

    public void setSelectedCharacterIndex(int selectedCharacterIndex) {
        this.selectedCharacterIndex = selectedCharacterIndex;
    }

    public void setPowerUp(object_type powerUp) {
        this.powerUp = powerUp;
    }

    public void clearPowerUp() {
        this.powerUp = object_type.NULL;
    }

    public object_type getPowerUp() {
        return powerUp;
    }

    public enum game_screen{ MENU, CONNECTION, REGISTRY, CONTROL, CHAR_PICK }

    public enum object_type {NULL, BANANA, FAKE_MYSTERY_BOX}

}

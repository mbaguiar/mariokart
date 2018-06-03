package com.lpoo1718_t1g3.mariokart.model;


import com.lpoo1718_t1g3.mariokart.networking.Message;

import java.util.ArrayList;

/**
 * Class that represents the model of the game, stores all its data
 */
public class GameModel {

    private static final GameModel ourInstance = new GameModel();
    private ArrayList<Character> characters = new ArrayList<>();
    private Boolean accelerometer = true;
    private String partyName;
    private game_screen nextScreen;
    private Message.char_pick_state pickState;
    private Character selectedCharacter;
    private String playerHandle;
    private int selectedCharacterIndex;
    private object_type powerUp = object_type.NULL;

    private GameModel() {
    }

    /**
     * Gets game model
     *
     * @return Returns current instance of GameModel
     */
    public static GameModel getInstance() {
        return ourInstance;
    }

    /**
     * Gets game next screen
     *
     * @return Returns game next screen
     */
    public game_screen getNextScreen() {
        return nextScreen;
    }

    /**
     * Sets game next screen
     *
     * @param nextScreen game next screen
     */
    public void setNextScreen(game_screen nextScreen) {
        this.nextScreen = nextScreen;
    }

    /**
     * Gets accelerometer values
     *
     * @return Returns accelerometer values
     */
    public Boolean getAccelerometer() {
        return accelerometer;
    }

    /**
     * Sets accelerometer value
     *
     * @param accelerometer Current accelerometer value
     */
    public void setAccelerometer(Boolean accelerometer) {
        this.accelerometer = accelerometer;
    }

    /**
     * Gets game characters
     *
     * @return Returns game characters
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Sets game characters
     *
     * @param characters new game characters
     */
    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    /**
     * Gets party name
     *
     * @return Returns party name
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets party name
     *
     * @param partyName new party name
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Sets player handle
     *
     * @param playerHandle new player handle
     */
    public void setPlayerHandle(String playerHandle) {
        this.playerHandle = playerHandle;
    }

    /**
     * Gets pick state
     *
     * @return Returns pick state
     */
    public Message.char_pick_state getPickState() {
        return pickState;
    }

    /**
     * Sets pick state
     *
     * @param pickState new pick state
     */
    public void setPickState(Message.char_pick_state pickState) {
        this.pickState = pickState;
    }

    /**
     * Sets selected Character
     *
     * @param selectedCharacter selected character
     */
    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    /**
     * Sets character with the given index to unavailable
     *
     * @param index character index
     */
    public void setUnavailable(int index) {
        this.characters.get(index).setAvailable(false);
    }

    /**
     * Gets selected character index
     *
     * @return Returns players selected character index
     */
    public int getSelectedCharacterIndex() {
        return selectedCharacterIndex;
    }

    /**
     * Set selected character index
     *
     * @param selectedCharacterIndex new character selected character
     */
    public void setSelectedCharacterIndex(int selectedCharacterIndex) {
        this.selectedCharacterIndex = selectedCharacterIndex;
    }

    /**
     * Sets power up type
     *
     * @param powerUp power up type
     */
    public void setPowerUp(object_type powerUp) {
        this.powerUp = powerUp;
    }

    /**
     * Clears power up
     */
    public void clearPowerUp() {
        this.powerUp = object_type.NULL;
    }

    /**
     * Gets player power up
     *
     * @return Returns player power up
     */
    public object_type getPowerUp() {
        return powerUp;
    }

    /**
     * Game screen state
     */
    public enum game_screen {
        MENU, CONNECTION, REGISTRY, CONTROL, CHAR_PICK
    }

    /**
     * Object type
     */
    public enum object_type {
        NULL, BANANA, FAKE_MYSTERY_BOX
    }

}

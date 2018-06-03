package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.Game;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.Character;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.Race;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.view.CharacterPickerView;
import com.lpoo1718_t1g3.mariokart.view.InstructionsView;
import com.lpoo1718_t1g3.mariokart.view.LobbyView;
import com.lpoo1718_t1g3.mariokart.view.MenuView;

import java.util.ArrayList;

import static com.lpoo1718_t1g3.mariokart.model.GameModel.game_screen.*;

/**
 * Class that controls the logic of the game based on the user inputs and the data on the GameModel
 */
public class GameController {

    private static GameController ourInstance = new GameController();
    private RaceController raceController;

    private GameController() {
    }

    /**
     * Returns the GameController
     * @return instance of GameController
     */
    public static GameController getInstance() {
        return ourInstance;
    }

    /**
     * Updates game screen based on game state
     */
    public void updateStatus() {
        if (GameModel.getInstance().getNextScreen() == null) return;
        switch (GameModel.getInstance().getNextScreen()) {
            case MENU:
                MarioKart.getInstance().setScreen(new MenuView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case LOBBY:
                if (GameModel.getInstance().getServer() != null)
                    GameModel.getInstance().stopServer();
                GameModel.getInstance().clearData();
                MarioKart.getInstance().setScreen(new LobbyView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case CHAR_PICK:
                MarioKart.getInstance().setScreen(new CharacterPickerView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case RACE:
                GameModel.getInstance().setCurrentRace(new Race(GameModel.getInstance().getChoosenTrack()));
                raceController = new RaceController();
                MarioKart.getInstance().setScreen(raceController.getRaceView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case INSTRUCTIONS:
                MarioKart.getInstance().setScreen(new InstructionsView());
                GameModel.getInstance().setNextScreen(null);
                break;
        }

    }

    /**
     * Returns raceController
     * @return returns current instance of race controller
     */
    public RaceController getRaceController() {
        return raceController;
    }

    /**
     * Updates raceController based on the passed time
     * @param delta time passed
     */
    public void updateRaceController(float delta) {
        raceController.update(delta);
    }

    /**
     * Handles a message from the client
     * @param m message to be handled
     */
    public void handleInput(Message m) {
        if (this.raceController != null) this.raceController.handleMovement(m);
    }

    private boolean registerPlayer(int playerId, String playerHandle) {

        return (GameModel.getInstance().addPlayer(playerId, playerHandle));
    }

    /**
     * Starts lobby screen
     */
    public void startLobbyScreen() {
        GameModel.getInstance().setNextScreen(LOBBY);
    }
    
    /**
     * creates server
     */
    public void createNewServer(){
        if (GameModel.getInstance().getServer() != null)
            GameModel.getInstance().stopServer();
        GameModel.getInstance().clearData();
     GameModel.getInstance().startServer();
    }

    /**
     * Sends a successful collection message
     * @param m Message to be sent
     */
    public void newConnection(Message m) {
        Message returnMessage = new Message(Message.MESSAGE_TYPE.CONNECTION, Message.SENDER.SERVER);
        returnMessage.addOption("connectionSuccessful", true); // or false
        returnMessage.addOption("partyName", GameModel.getInstance().getPartyName());
        writeToClient(returnMessage, m.getSenderId());

    }

    /**
     * Write message to client with the given id
     * @param m Message to be written
     * @param id client id
     */
    public void writeToClient(Message m, int id) {
        if (GameModel.getInstance().getServer() != null) GameModel.getInstance().getServer().writeToClient(m, id);

    }

    /**
     * Writes response to client registry
     * @param m client registry message
     */
    public void newPlayer(Message m) {
        Message returnMessage = new Message(Message.MESSAGE_TYPE.PLAYER_REGISTRY, Message.SENDER.SERVER);
        if (GameModel.getInstance().getPlayers().size() < GameModel.getInstance().MAX_PLAYERS){
            String name = (String) m.getOptions().get("playerHandle");
            if (name.equals("")) name = "Player " + GameModel.getInstance().getPlayers().size() + 1;
            if (registerPlayer(m.getSenderId(), name)) {
                returnMessage.addOption("registrySuccessful", true);
            } else {
                returnMessage.addOption("registrySuccessful", false);
            }
        }
        writeToClient(returnMessage, m.getSenderId());
    }

    /**
     * Starts character picking process
     */
    public void startCharPick() {
        if (GameModel.getInstance().getPlayers().size() >= 1){
            GameModel.getInstance().stopNewConnections();
            GameModel.getInstance().setNextScreen(CHAR_PICK);
            pickerAsync();
        }
    }

    private void startRace() {
        Message m = new Message(Message.MESSAGE_TYPE.CONTROLLER_ACTIVITY, Message.SENDER.SERVER);
        GameController.getInstance().broadcastToAll(m);
        GameModel.getInstance().setNextScreen(RACE);
    }

    private void broadcastToAll(Message m) {
        for (Player p : GameModel.getInstance().getPlayers()) {
            GameModel.getInstance().getServer().writeToClient(m, p.getPlayerId());
        }
    }

    private void pickerAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Player p : GameModel.getInstance().getPlayers()) {
                    GameModel.getInstance().setCurrentPickerId(p.getPlayerId());
                    GameController.getInstance().writeToClient(newPickerMessage(Message.char_pick_state.PICK), p.getPlayerId());
                    broadcastToNotPicked(newPickerMessage(Message.char_pick_state.WAIT), p.getPlayerId());
                    boolean timedOut = true;
                    long t = System.currentTimeMillis();
                    while (System.currentTimeMillis() - t <= 30000) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (GameModel.getInstance().getPickMessage() != null) {
                            if (testPick(GameModel.getInstance().getPickMessage(), p)) {
                                timedOut = false;
                                break;
                            }
                        }
                    }
                    if (timedOut) {
                        GameController.getInstance().restart();
                        return;
                    }

                }
                GameController.getInstance().startRace();
            }

            private boolean testPick(Message receivedMessage, Player p) {
                GameModel.getInstance().setPickMessage(null);

                if (receivedMessage.getSenderId() == p.getPlayerId()) {

                    int index = (Integer) receivedMessage.getOptions().get("selectedIndex");
                    ArrayList<Character> characters = GameModel.getInstance().getCharacters();
                    Character selectedCharacter = characters.get(index);

                    if (selectedCharacter.isAvailable()) {
                        p.setSelectedCharacter(selectedCharacter);

                        GameModel.getInstance().setCharacterUnavailable(index);

                        Message returnMessage = newPickerMessage(Message.char_pick_state.PICKED);

                        returnMessage.addOption("character", selectedCharacter);

                        GameController.getInstance().writeToClient(returnMessage, p.getPlayerId());
                        return true;
                    } else GameController.getInstance().writeToClient(newPickerMessage(Message.char_pick_state.PICK_ERROR), p.getPlayerId());
                }
                return false;
            }
        }).start();

    }

    private Message newPickerMessage(Message.char_pick_state s) {
        Message m = new Message(Message.MESSAGE_TYPE.CHAR_PICK, Message.SENDER.SERVER);
        m.addOption("charPickState", s);
        m.addOption("characters", GameModel.getInstance().getCharacters());
        return m;
    }

    private void broadcastToNotPicked(Message m, int playerId) {
        for (Player p : GameModel.getInstance().getPlayers()) {
            if (p.getSelectedCharacter() == null && p.getPlayerId() != playerId)
                GameController.getInstance().writeToClient(m, p.getPlayerId());
        }
    }

    /**
     * Sets pick message
     * @param m pick message
     */
    public void pickMessage(Message m) {
        GameModel.getInstance().setPickMessage(m);
    }

    /**
     * Notify raceController about object use
     * @param m
     */
    public void usePowerUp(Message m) {
        GameController.getInstance().getRaceController().useObject(m.getSenderId());
    }

    /**
     * Disconnects player with the given playerId
     * @param playerId id of the player to be disconnected
     */
    public void playerDisconnected(int playerId) {
        GameModel.getInstance().kickPlayer(playerId);
    }

    /**
     * Restarts lobby after race
     */
    public void restart(){
        GameModel.getInstance().setNextScreen(LOBBY);
    }
}
package com.lpoo1718_t1g3.mariokart.controller;

import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.Character;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.Race;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.view.CharacterPickerView;
import com.lpoo1718_t1g3.mariokart.view.LobbyView;
import com.lpoo1718_t1g3.mariokart.view.MenuView;

import java.util.ArrayList;

import static com.lpoo1718_t1g3.mariokart.model.GameModel.game_screen.*;

public class GameController {

    private static GameController ourInstance = new GameController();
    private RaceController raceController;

    private GameController() {
    }

    public static GameController getInstance() {
        return ourInstance;
    }

    public void updateStatus() {
        if (GameModel.getInstance().getNextScreen() == null) return;
        switch (GameModel.getInstance().getNextScreen()) {
            case MENU:
                MarioKart.getInstance().setScreen(new MenuView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case LOBBY:
                MarioKart.getInstance().setScreen(new LobbyView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case CHAR_PICK:
                MarioKart.getInstance().setScreen(new CharacterPickerView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case TRACK_VOTE:
                //MarioKart.getInstance().setScreen(new CharacterPickerView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case RACE:
                GameModel.getInstance().setCurrentRace(new Race(GameModel.getInstance().getChoosenTrack()));
                raceController = new RaceController();
                MarioKart.getInstance().setScreen(raceController.getRaceView());
                GameModel.getInstance().setNextScreen(null);
                break;
        }

    }

    public RaceController getRaceController() {
        return raceController;
    }

    public void updateRaceController(float delta) {
        raceController.update(delta);
    }

    public void handleInput(Message m) {
        if (this.raceController != null) this.raceController.handleMovement(m);
    }

    public boolean registerPlayer(int playerId, String playerHandle) {

        if (GameModel.getInstance().addPlayer(playerId, playerHandle)) {
            //raceController.addKartBody(GameModel.getInstance().getPlayers().get(playerId));
            return true;
        }

        return false;
    }

    public void startLobby() {
        GameModel.getInstance().startServer();
        GameModel.getInstance().setNextScreen(LOBBY);
    }

    public void newConnection(Message m) {
        //test if connection is possible (max players, state of game, etc)
        Message returnMessage = new Message(Message.MESSAGE_TYPE.CONNECTION, Message.SENDER.SERVER);
        returnMessage.addOption("connectionSuccessful", true); // or false
        returnMessage.addOption("partyName", GameModel.getInstance().getPartyName());
        writeToClient(returnMessage, m.getSenderId());

    }

    public void writeToClient(Message m, int id) {
        GameModel.getInstance().getServer().writeToClient(m, id);

    }

    public void newPlayer(Message m) {
        Message returnMessage = new Message(Message.MESSAGE_TYPE.PLAYER_REGISTRY, Message.SENDER.SERVER);
        if (registerPlayer(m.getSenderId(), (String) m.getOptions().get("playerHandle"))) {
            returnMessage.addOption("registrySuccessful", true);
        } else {
            returnMessage.addOption("registrySuccessful", false);
            returnMessage.addOption("error", "Player name already in use");
        }
        writeToClient(returnMessage, m.getSenderId());
        System.out.println(m);
    }

    public void startCharPick() {
        GameModel.getInstance().setNextScreen(CHAR_PICK);
        pickerAsync();
    }

    public void startRace() {
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

                    long t = System.currentTimeMillis();
                    while (System.currentTimeMillis() - t <= 30000) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (GameModel.getInstance().getPickMessage() != null) {
                            if (testPick(GameModel.getInstance().getPickMessage(), p)) break;
                        }
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

    public void pickMessage(Message m) {
        GameModel.getInstance().setPickMessage(m);
    }

    public void usePowerUp(Message m) {
        GameController.getInstance().getRaceController().useObject(m.getSenderId());
    }
}
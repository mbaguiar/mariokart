package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.ScreenAdapter;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.view.LobbyView;

public class GameController {

    private static GameController ourInstance = new GameController();


    public static GameController getInstance() {
        return ourInstance;
    }


    public void update (float delta) {

    }

    public void handleInput(Message m){
    }

    public ScreenAdapter getView() {
        return null;
    }


    public boolean registerPlayer(int playerId, String playerHandle){
        return GameModel.getInstance().addPlayer(playerId, playerHandle);
    }

    public void startLobby() {
        GameModel.getInstance().startServer();
        MarioKart.getInstance().setScreen(new LobbyView());
    }

    public void newConnection(Message m) {
        //test if connection is possible (max players, state of game, etc)
        Message returnMessage = new Message(Message.MESSAGE_TYPE.CONNECTION, Message.SENDER.SERVER);
        returnMessage.addOption("connectionSuccessful", true); // or false
        writeToClient(returnMessage, m.getSenderId());
    }

    public void writeToClient(Message m, int id){
        GameModel.getInstance().getServer().writeToClient(m, id);
    }

    public void newPlayer(Message m) {
        Message returnMessage = new Message(Message.MESSAGE_TYPE.PLAYER_REGISTRY, Message.SENDER.SERVER);
        if (registerPlayer(m.getSenderId(), (String)m.getOptions().get("playerHandle"))){
            returnMessage.addOption("registrySuccessful", true);
        } else {
            returnMessage.addOption("registrySuccessful", false);
            returnMessage.addOption("error", "Player name already in use");
        }
        writeToClient(m, m.getSenderId());
    }
}
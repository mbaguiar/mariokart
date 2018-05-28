package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.MysteryBoxBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.TireBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.TrackBody;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.entities.EntityModel;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;
import com.lpoo1718_t1g3.mariokart.view.CharacterPickerView;
import com.lpoo1718_t1g3.mariokart.view.LobbyView;
import com.lpoo1718_t1g3.mariokart.view.RaceView;
import java.util.HashMap;

public class GameController {


    private RaceController raceController;
    private static GameController ourInstance = null;
    private ServerManager server;

    private GameController() {
        server = new ServerManager();
        raceController = new RaceController();
    }

    public static GameController getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameController();
            return ourInstance;
        }

        return ourInstance;
    }

    public RaceController getRaceController() {
        return raceController;
    }

    public void update(float delta) {
        if (MarioKart.getInstance().getScreen().getClass() == RaceView.class) {
            raceController.update(delta);
        }
    }

    public void handleInput(Message m) {

    }

    public boolean registerPlayer(int playerId, String playerHandle) {
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
        writeToClient(m, m.getSenderId());

    }

    public void startGame() {
        MarioKart.getInstance().setScreen(new CharacterPickerView());
    }

    public void startRace() {
        raceController = new RaceController();
        MarioKart.getInstance().setScreen(raceController.getRaceView());
    }
}
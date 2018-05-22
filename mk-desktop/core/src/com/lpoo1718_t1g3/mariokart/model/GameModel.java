package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;

import java.util.ArrayList;

public class GameModel {

    private static GameModel ourInstance = new GameModel();

    private ArrayList<Player> players = new ArrayList<Player>();

    private KartModel kart;

    private GameModel() {
        kart = new KartModel(0, 0, 0);
    }

    public KartModel getKart() {
        return kart;
    }

    public static GameModel getInstance() {
        return ourInstance;
    }

    public boolean addPlayer(int playerId, String playerHandle){

        for (Player p: players){
            if (p.getPlayerHandle().equals(playerHandle)) return false;
        }
        players.add(new Player(playerId, playerHandle));
        return true;
    }
}

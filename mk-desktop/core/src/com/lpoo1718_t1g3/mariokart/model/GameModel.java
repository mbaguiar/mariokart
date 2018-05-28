package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;

import java.util.ArrayList;

public class GameModel {

    private static GameModel ourInstance = new GameModel();
    private final ArrayList<Character> characters = new ArrayList<Character>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private ServerManager server;
    private KartModel kart;
    private TrackModel track1;
    private String partyName = "MarioKart Party";
    private String ipAddress;
    private int port;
    private ArrayList<String> connectedPlayers = new ArrayList<String>();
    private int MAX_PLAYERS;
    private boolean qrCode = false;

    private GameModel() {
        initCharacters();
        kart = new KartModel(0, 0, 0);
        track1 = new TrackModel(-24, -16, 0);
    }

    public static GameModel getInstance() {
        return ourInstance;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public KartModel getKart() {
        return kart;
    }

    public boolean addPlayer(int playerId, String playerHandle) {

        for (Player p : players) {
            if (p.getPlayerHandle().equals(playerHandle)) return false;
        }
        players.add(new Player(playerId, playerHandle));
        return true;
    }

    public TrackModel getTrack1() {
        return track1;
    }

    public void startServer() {
        this.server = new ServerManager();
        updateServerData();
    }

    public ServerManager getServer() {
        return server;
    }

    private void updateServerData() {
        ipAddress = GameModel.getInstance().getServer().getLocalIp();
        port = GameModel.getInstance().getServer().getPort();
    }

    public void setQrCode(boolean qrCode) {
        this.qrCode = qrCode;
    }

    private void initCharacters() {
        characters.add(new Character("Mario"));
        characters.add(new Character("Luigi"));
        characters.add(new Character("Peach"));
        characters.add(new Character("Toad"));
        characters.add(new Character("Yoshi"));
        characters.add(new Character("Bowser"));
    }
}

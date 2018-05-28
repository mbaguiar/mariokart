package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;
import com.lpoo1718_t1g3.mariokart.view.RaceView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameModel {

    private static GameModel ourInstance = new GameModel();
    private ServerManager server;
    private KartModel kart;
    private TrackModel track1;
    private String partyName = "MarioKart Party";
    private String ipAddress;
    private int port;
    private ArrayList<String> connectedPlayers = new ArrayList<String>();
    private int MAX_PLAYERS;
    private boolean qrCode = false;
    private ArrayList<Player> players = new ArrayList<Player>();
    private final HashMap<String, Character> characters = new HashMap<String, Character>();

    private GameModel() {
        kart = new KartModel(0, 0, 0);
        track1 = new TrackModel(-24, -16, 0);
        setUpTrack1();
        initCharacters();
    }

    public static GameModel getInstance() {
        return ourInstance;
    }

    public HashMap<String, Character> getCharacters() {
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

    private void setUpTrack1() {
        MysteryBoxModel box = new MysteryBoxModel(10, 10, 0);
        track1.addBox(box);
    }

    public boolean addPlayer(int playerId, String playerHandle, String selectedCharacter) {

        for (Player p : players) {
            if (p.getPlayerHandle().equals(playerHandle)) return false;
        }

        Character character = characters.get(selectedCharacter);
        Player player = new Player(playerId, playerHandle, character);
        players.add(player);
        //RaceView.getInstance().addKartView(player);
        //GameController.getInstance().addKartBody(player);

        return true;
    }

    private void initCharacters() {
        characters.put("Mario", new Character("Mario", "mariokart.png"));
        characters.put("Luigi", new Character("Luigi", "luigikart.png"));
        characters.put("Peach", new Character("Peach"));
        characters.put("Toad", new Character("Toad"));
        characters.put("Yoshi", new Character("Yoshi"));
        characters.put("Bowser", new Character("Bowser"));
    }

    public ArrayList<Player> getPlayers() {
        return players;
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
}

package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;

import java.util.ArrayList;
import java.util.HashMap;

public class GameModel {

    public static enum object_type {BANANA, MUSHROOM};

    private static GameModel ourInstance = new GameModel();
    private ServerManager server;
    private String partyName = "MarioKart Party";
    private String ipAddress;
    private int port;
    private int MAX_PLAYERS;
    private boolean qrCode = false;
    private ArrayList<Player> players = new ArrayList<Player>();
    private final HashMap<String, Character> characters = new HashMap<String, Character>();
    private Race currentRace;
    private TrackModel choosenTrack;

    private GameModel() {
        TrackModel track1 = new TrackModel(-24, -16, 0);
        choosenTrack = track1;
        setUpTrack1(track1);
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


    private void setUpTrack1(TrackModel track1) {
        track1.addBox(new MysteryBoxModel(458, 107, 0));
        track1.addBox(new MysteryBoxModel(414, 107, 0));
        track1.addBox(new MysteryBoxModel(370, 107, 0));
        track1.addBox(new MysteryBoxModel(458, 76, 0));
        track1.addBox(new MysteryBoxModel(417, 76, 0));
        track1.addBox(new MysteryBoxModel(370, 76, 0));
        track1.addBox(new MysteryBoxModel(72, 807, 0));
        track1.addBox(new MysteryBoxModel(103, 821, 0));
        track1.addBox(new MysteryBoxModel(136, 844, 0));
        track1.addBox(new MysteryBoxModel(503, 529, 0));
        track1.addBox(new MysteryBoxModel(459, 629, 0));
        track1.addBox(new MysteryBoxModel(505, 763, 0));
        track1.addBox(new MysteryBoxModel(473, 852, 0));
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
        characters.put("Peach", new Character("Peach", "peachkart.png"));
        characters.put("Toad", new Character("Toad", "toadkart.png"));
        characters.put("Yoshi", new Character("Yoshi", "yoshikart.png"));
        characters.put("Bowser", new Character("Bowser", "bowserkart.png"));
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

    public Player getPlayer(int id) {
        for (Player player : players) {
            if (player.getPlayerId() == id)
                return player;
        }

        return null;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public TrackModel getChoosenTrack() {
        return choosenTrack;
    }

    public Race getCurrentRace() {
        return currentRace;
    }

    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }
}

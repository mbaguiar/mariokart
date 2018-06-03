package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;

import java.util.ArrayList;

public class GameModel {

    private static GameModel ourInstance = new GameModel();
    private ArrayList<Character> characters = new ArrayList<Character>();
    private Message pickMessage;
    private ServerManager server;
    private String partyName = "MarioKart Party";
    private String ipAddress;
    private int port;
    public final int MAX_PLAYERS = 6;
    private ArrayList<Player> players = new ArrayList<Player>();
    private game_screen nextScreen;
    private Race currentRace;
    private TrackModel choosenTrack;
    private int currentPickerId = -1;

    private GameModel() {
        TrackModel track1 = new TrackModel(-24, -16, 0);
        choosenTrack = track1;
        setUpTrack1(track1);
        initCharacters();
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

    public Message getPickMessage() {
        return pickMessage;
    }

    public void setPickMessage(Message pickMessage) {
        this.pickMessage = pickMessage;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
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

    public boolean addPlayer(int playerId, String playerHandle) {

        for (Player p : players) if (p.getPlayerHandle().equals(playerHandle)) return false;

        Player player = new Player(playerId, playerHandle);
        players.add(player);
        return true;
    }

    private void initCharacters() {
        /*characters.add(new Character("Mario", new Color(247/255f, 45/255f, 45/255f, 1)));
        characters.add(new Character("Luigi", new Color(91/255f, 239/255f, 91/255f, 1)));
        characters.add(new Character("Peach", new Color(249/255f, 188/255f, 188/255f, 1)));
        characters.add(new Character("Toad", new Color(65/255f, 156/255f, 242/255f, 1)));
        characters.add(new Character("Yoshi", new Color(155/255f, 246/255f, 155/255f, 1)));
        characters.add(new Character("Bowser", new Color(238/255f, 185/255f, 37/255f, 1)));*/
        characters.add(new Character("Mario"));
        characters.add(new Character("Luigi"));
        characters.add(new Character("Peach"));
        characters.add(new Character("Toad"));
        characters.add(new Character("Yoshi"));
        characters.add(new Character("Bowser"));
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

    public Player getPlayer(int id) {
        for (Player player : players) {
            if (player.getPlayerId() == id)
                return player;
        }

        return null;
    }

    public void setCharacterUnavailable(int index){
        this.characters.get(index).setAvailable(false);
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

    public void kickPlayer(int playerId) {
        if (getPlayer(playerId) != null) players.remove(getPlayer(playerId));
    }

    public void stopNewConnections() {
        this.server.setFinished(true);
    }

    public void stopServer() {
        this.server.stop();
    }

    public void clearData() {
        this.server = null;
        this.currentPickerId = -1;
        this.players.clear();
    }

    public enum object_type { NULL, BANANA, FAKE_MYSTERY_BOX }

    public enum game_screen { MENU, LOBBY, CHAR_PICK, RACE }

    public int getCurrentPickerId() {
        return currentPickerId;
    }

    public void setCurrentPickerId(int currentPickerId) {
        this.currentPickerId = currentPickerId;
    }
}

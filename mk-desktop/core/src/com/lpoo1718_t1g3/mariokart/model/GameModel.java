package com.lpoo1718_t1g3.mariokart.Model;

import com.lpoo1718_t1g3.mariokart.Model.entities.KartModel;
import com.lpoo1718_t1g3.mariokart.Model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.Model.entities.TrackModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;

import java.util.ArrayList;

public class GameModel {

    private static GameModel ourInstance = new GameModel();;
    private ArrayList<Character> characters = new ArrayList<Character>();
    private Message pickMessage;
    private ServerManager server;
    private KartModel kart;
    private TrackModel track1;
    private String partyName = "MarioKart Party";
    private String ipAddress;
    private int port;
    private int MAX_PLAYERS;
    private boolean qrCode = false;
    private ArrayList<Player> players = new ArrayList<Player>();
    private game_screen nextScreen;
    private GameModel() {
        kart = new KartModel(0, 0, 0);
        track1 = new TrackModel(-24, -16, 0);
        setUpTrack1();
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

    public KartModel getKart() {
        return kart;
    }

    public TrackModel getTrack1() {
        return track1;
    }

    private void setUpTrack1() {
        MysteryBoxModel box = new MysteryBoxModel(10, 10, 0);
        MysteryBoxModel box1 = new MysteryBoxModel(20, 10, 0);
        MysteryBoxModel box2 = new MysteryBoxModel(20, 20, 0);
        MysteryBoxModel box3 = new MysteryBoxModel(50, 40, 0);
        MysteryBoxModel box4 = new MysteryBoxModel(30, 40, 0);
        track1.addBox(box);
        track1.addBox(box1);
        track1.addBox(box2);
        track1.addBox(box3);
        track1.addBox(box4);
    }

    public boolean addPlayer(int playerId, String playerHandle) {

        for (Player p : players) {
            if (p.getPlayerHandle().equals(playerHandle)) return false;
        }

        Player player = new Player(playerId, playerHandle);
        players.add(player);
        //RaceView.getInstance().addKartView(player);
        //GameController.getInstance().addKartBody(player);

        return true;
    }

    private void initCharacters() {
        characters.add(new Character("Mario", "mariokart.png"));
        characters.add(new Character("Luigi", "luigikart.png"));
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

    public void setCharacterUnavailable(int index){
        this.characters.get(index).setAvailable(false);
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public enum object_type { BANANA, MUSHROOM }

    public enum char_pick_state { WAIT, PICK, PICKED }

    public enum game_screen { MENU, LOBBY, CHAR_PICK, TRACK_VOTE, RACE, RESULTS }
}

package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.MysteryBoxModel;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;

import java.util.ArrayList;

/**
 * Class that represents the model of the game, stores all its data
 */
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

    /**
     * Constructs a new GameModel
     */
    private GameModel() {
        TrackModel track1 = new TrackModel(-24, -16, 0);
        choosenTrack = track1;
        setUpTrack1(track1);
        initCharacters();
    }

    /**
     * Gets current instance of GameModel
     * @return Returns game model instance
     */
    public static GameModel getInstance() {
        return ourInstance;
    }

    /**
     * Gets game next screen
     * @return Returns next screen
     */
    public game_screen getNextScreen() {
        return nextScreen;
    }

    /**
     * Sets game next screen
     * @param nextScreen game next screen
     */
    public void setNextScreen(game_screen nextScreen) {
        this.nextScreen = nextScreen;
    }

    /**
     * Gets pick message
     * @return Returns pick message
     */
    public Message getPickMessage() {
        return pickMessage;
    }

    /**
     * Sets pick message to the given message
     * @param pickMessage pick Message
     */
    public void setPickMessage(Message pickMessage) {
        this.pickMessage = pickMessage;
    }

    /**
     * Gets game characters
     * @return Returns all game characters
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Gets ip address
     * @return Returns ip address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Gets port
     * @return Returns port
     */
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

    /**
     * Adds player to the game, with the given playerId and playerHandle
     * @param playerId player's id
     * @param playerHandle player's handle
     * @return Returns true if success and false otherwise
     */
    public boolean addPlayer(int playerId, String playerHandle) {

        for (Player p : players) if (p.getPlayerHandle().equals(playerHandle)) return false;

        Player player = new Player(playerId, playerHandle);
        players.add(player);
        return true;
    }

    private void initCharacters() {
        characters.add(new Character("Mario"));
        characters.add(new Character("Luigi"));
        characters.add(new Character("Peach"));
        characters.add(new Character("Toad"));
        characters.add(new Character("Yoshi"));
        characters.add(new Character("Bowser"));
    }

    /**
     * Gets all the game players
     * @return Returns players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Starts server
     */
    public void startServer() {
        this.server = new ServerManager();
        updateServerData();
    }

    /**
     * Gets server
     * @return Returns serverManager
     */
    public ServerManager getServer() {
        return server;
    }

    private void updateServerData() {
        ipAddress = GameModel.getInstance().getServer().getLocalIp();
        port = GameModel.getInstance().getServer().getPort();
    }


    /**
     * Gets player with the given id
     * @param id player's id
     * @return Returns the player if it exists and null otherwise
     */
    public Player getPlayer(int id) {
        for (Player player : players) {
            if (player.getPlayerId() == id)
                return player;
        }

        return null;
    }

    /**
     * Sets the character with the given index to unavailable
     * @param index character's index
     */
    public void setCharacterUnavailable(int index){
        this.characters.get(index).setAvailable(false);
    }

    /**
     * Gets party name
     * @return Returns party name
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets party name to the given name
     * @param partyName Party name
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Gets current chosen track
     * @return Returns current TrackModel
     */
    public TrackModel getChoosenTrack() {
        return choosenTrack;
    }

    /**
     * Gets current race
     * @return Returns current Race
     */
    public Race getCurrentRace() {
        return currentRace;
    }

    /**
     * Sets current race
     * @param currentRace game race
     */
    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    /**
     * Kicks player with the given id
     * @param playerId player's id to be kicked
     */
    public void kickPlayer(int playerId) {
        if (getPlayer(playerId) != null) players.remove(getPlayer(playerId));
    }

    /**
     * Stops accepting new connections
     */
    public void stopNewConnections() {
        this.server.setFinished(true);
    }

    /**
     * Disconnects all clients, closes sockets and streams
     */
    public void stopServer() {
        this.server.stop();
    }

    /**
     * Clear model data
     */
    public void clearData() {
        this.server = null;
        this.currentPickerId = -1;
        this.players.clear();
    }

    /**
     * Object type
     */
    public enum object_type { NULL, BANANA, FAKE_MYSTERY_BOX }


    /**
     * Game state
     */
    public enum game_screen {MENU, LOBBY, CHAR_PICK, TRACK_VOTE, RACE}

    /**
     * Gets id of player currently choosing its track
     * @return Returns id player
     */
    public int getCurrentPickerId() {
        return currentPickerId;
    }

    /**
     * Sets current player picker id
     * @param currentPickerId player id
     */
    public void setCurrentPickerId(int currentPickerId) {
        this.currentPickerId = currentPickerId;
    }
}

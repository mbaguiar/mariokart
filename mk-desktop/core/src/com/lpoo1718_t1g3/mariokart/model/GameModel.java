package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.KartModel;
import com.lpoo1718_t1g3.mariokart.networking.ServerManager;

import java.util.ArrayList;

public class GameModel {

    private static GameModel ourInstance = new GameModel();

    private ArrayList<Player> players = new ArrayList<Player>();

    private ServerManager server;

    private KartModel kart;
    private String partyName = "MarioKart Party";
    private String ipAddress;
    private int port;
    private ArrayList<String> connectedPlayers = new ArrayList<String>();
    private int MAX_PLAYERS;
    private GameModel() {
        kart = new KartModel(0, 0, 0);
    }

    private boolean qrCode = false;

    public static GameModel getInstance() {
        return ourInstance;
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

    //private Image qrCode; Image?

    public boolean addPlayer(int playerId, String playerHandle){

        for (Player p: players){
            if (p.getPlayerHandle().equals(playerHandle)) return false;
        }
        players.add(new Player(playerId, playerHandle));
        return true;
    }

    public void startServer() {
        this.server = new ServerManager();
        updateServerData();
    }

    public ServerManager getServer(){
        return server;
    }

    private void updateServerData(){
        ipAddress = GameModel.getInstance().getServer().getLocalIp();
        port = GameModel.getInstance().getServer().getPort();
    }

    public void setQrCode(boolean qrCode) {
        this.qrCode = qrCode;
    }
}

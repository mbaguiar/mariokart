package com.lpoo1718_t1g3.mariokart.model;


import java.util.ArrayList;

public class LobbyModel {

    private String partyName = "MarioKart Party";

    private String ipAddress;

    //private Image qrCode; Image?

    private int port;

    private ArrayList<String> connectedPlayers = new ArrayList<String>();

    private int MAX_PLAYERS;

    public LobbyModel(){

    }

}

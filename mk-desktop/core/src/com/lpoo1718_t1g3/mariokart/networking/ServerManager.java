package com.lpoo1718_t1g3.mariokart.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerManager {

    private ServerSocket socket;
    private ArrayList<ClientManager> clients;

    public ServerManager(int port){
        try {
            socket = new ServerSocket(port);
            while (true){
                Socket clientSocket = socket.accept();
                clients.add(new ClientManager(clientSocket));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}

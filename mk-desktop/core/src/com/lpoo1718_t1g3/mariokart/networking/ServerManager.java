package com.lpoo1718_t1g3.mariokart.networking;

import sun.java2d.pipe.ShapeSpanIterator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerManager implements Runnable {

    private ServerSocket socket;

    public ServerManager(int port){
        try {
            socket = new ServerSocket(port);
            System.out.println("Server opened");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true){
            Socket clientSocket = null;
            try {
                clientSocket = socket.accept();
                System.out.println("Connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
            new ClientManager(clientSocket).start();
        }
    }
}

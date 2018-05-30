package com.lpoo1718_t1g3.mariokart.networking;

import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.GameModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientManager extends Thread {

    private Socket socket;

    public int getPlayerId() {
        return playerId;
    }

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final int playerId;

    ClientManager(Socket client, int playerId) {
        socket = client;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message input;
        try {
            while ((input = (Message) inputStream.readObject()) != null) {
                handleMessage(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void handleMessage(Message m) {
        m.setSenderId(playerId);
        switch (m.getType()) {
            case CONNECTION:
                GameController.getInstance().newConnection(m);
                break;
            case PLAYER_REGISTRY:
                GameController.getInstance().newPlayer(m);
                break;
            case CONTROLLER_ACTIVITY:
                GameController.getInstance().handleInput(m);
                break;
        }
    }

    void write(Message m){
        System.out.println(m);
        final Message obj = m;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (outputStream != null) {
                    try {
                        outputStream.writeObject(obj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

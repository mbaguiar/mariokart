package com.lpoo1718_t1g3.mariokart.networking;

import com.lpoo1718_t1g3.mariokart.controller.GameController;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientManager extends Thread {

    private final int playerId;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    ClientManager(Socket client, int playerId) {
        socket = client;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
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
            while (!socket.isClosed() && (input = (Message) inputStream.readObject()) != null) {
                handleMessage(input);
            }
        } catch (SocketException e) {
            return;
        } catch (EOFException e) {
            GameController.getInstance().playerDisconnected(this.playerId);
            try {
                this.socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
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
            case CHAR_PICK:
                GameController.getInstance().pickMessage(m);
                break;
            case POWER_UP:
                GameController.getInstance().usePowerUp(m);
                break;
            case DISCONNECTION:
                GameController.getInstance().playerDisconnected(m.getSenderId());
                this.close();
                break;
        }
    }

    void write(Message m) {
        if (!socket.isClosed() && outputStream != null) {
            try {
                outputStream.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

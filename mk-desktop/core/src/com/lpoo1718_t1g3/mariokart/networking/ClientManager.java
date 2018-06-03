package com.lpoo1718_t1g3.mariokart.networking;

import com.lpoo1718_t1g3.mariokart.controller.GameController;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Class that represents and controls a client that is connected to the server
 * @see Thread
 */
public class ClientManager extends Thread {

    private final int playerId;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    /**
     * Constructs and initializes a client manager for the player with the given id
     * @param client client socket
     * @param playerId player's id
     */
    ClientManager(Socket client, int playerId) {
        socket = client;
        this.playerId = playerId;
    }

    /**
     * Gets player id
     * @return Returns player's id
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Receives client messages
     */
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
        System.out.println(m.toString());
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

    /**
     * Writes a message to the client
     * @param m Message to be written
     */
    void write(Message m) {
        if (!socket.isClosed() && outputStream != null) {
            try {
                outputStream.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes client's socket
     */
    void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

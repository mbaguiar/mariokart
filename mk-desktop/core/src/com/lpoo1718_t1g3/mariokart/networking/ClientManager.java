package com.lpoo1718_t1g3.mariokart.networking;

import com.lpoo1718_t1g3.mariokart.controller.GameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class ClientManager extends Thread {

    private final int playerId;
    private final LinkedList<Message> messageQueue = new LinkedList<Message>();
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Thread writerThread;
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
/*            writerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (outputStream != null) {
                        while(true){
                            Message m;
                            if ((m = messageQueue.pollFirst()) != null){
                                try {
                                    outputStream.writeObject(m);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });
            writerThread.start();*/
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
        }
    }

    void write(Message m) {
        if (outputStream != null) {
            try {
                outputStream.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

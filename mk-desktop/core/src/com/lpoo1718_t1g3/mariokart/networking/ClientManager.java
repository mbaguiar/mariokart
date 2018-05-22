package com.lpoo1718_t1g3.mariokart.networking;

import com.lpoo1718_t1g3.mariokart.controller.GameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
public class ClientManager extends Thread {

    private Socket socket;
    ObjectInputStream inputStream;
    private final int playerId;

    ClientManager(Socket client, int playerId){
        socket = client;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message input;
        try {
            while ((input = (Message) inputStream.readObject()) != null){
                input.setSenderId(playerId);
                if (input.getType() == Message.MESSAGE_TYPE.CONTROLLER_ACTIVITY) GameController.getInstance().getControllerInput(input);
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

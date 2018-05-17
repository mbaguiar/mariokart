package com.lpoo1718_t1g3.mariokart.networking;

import com.lpoo1718_t1g3.mariokart.controller.GameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
public class ClientManager extends Thread {

    private Socket socket;
    ObjectInputStream inputStream;

    ClientManager(Socket client){
        socket = client;
    }

    @Override
    public void run() {
        System.out.println("Input stream opened");
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message input;
        try {
            while ((input = (Message) inputStream.readObject()) != null){
                if (input.getType() == Message.MESSAGE_TYPE.CONTROLLER_ACTIVITY) GameController.getInstance().getControllerInput(input);
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

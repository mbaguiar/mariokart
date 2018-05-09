package com.lpoo1718_t1g3.mariokart.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
public class ClientManager implements Runnable {

    private Socket socket;
    ObjectInputStream inputStream;

    ClientManager(Socket client){
        socket = client;
        this.run();
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
                MessageQueue.getInstance().add(input);
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

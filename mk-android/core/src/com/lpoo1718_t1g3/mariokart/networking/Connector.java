package com.lpoo1718_t1g3.mariokart.networking;

import com.badlogic.gdx.Game;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Connector {

    private static Connector ourInstance = new Connector();

    private Socket socket;
    private ObjectOutputStream ostream;
    private ObjectInputStream istream;

    private Connector() {}

    public static Connector getInstance(){
        return ourInstance;
    }

    public Socket connect(String addr, int port) {
        final String cAddress = addr;
        final int cPort = port;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(cAddress, cPort);
                    socket.setTcpNoDelay(true);
                    ostream = new ObjectOutputStream(socket.getOutputStream());
                    startReceiver();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return socket;
    }

    private void startReceiver(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    istream = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message input;
                try {
                    while ((input = (Message) istream.readObject()) != null && socket.isConnected()){
                        handleMessage(input);
                    }
                } catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void write(Message o){
        System.out.println(o.toString());
        final Message obj = o;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (socket.isClosed()) {
                    GameController.getInstance().handleDisconnectMessage(null);
                    return;
                }
                if (ostream != null) {
                    try {
                        ostream.writeObject(obj);
                        ostream.reset();
                    } catch (SocketException e){
                        disconnect();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        GameController.getInstance().handleDisconnectMessage(null);
        try {
            this.socket.close();
            istream.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message m){
        switch (m.getType()){
            case CONNECTION:
                GameController.getInstance().handleConnectionMessage(m);
                break;
            case PLAYER_REGISTRY:
                GameController.getInstance().handleRegistryMessage(m);
                break;
            case CHAR_PICK:
                GameController.getInstance().handleCharPickMessage(m);
                break;
            case CONTROLLER_ACTIVITY:
                GameController.getInstance().handleControlMessage(m);
                break;
            case POWER_UP:
                GameController.getInstance().handlePowerUpMessage(m);
                break;
            case DISCONNECTION:
                this.disconnect();
                break;
        }
    }
}

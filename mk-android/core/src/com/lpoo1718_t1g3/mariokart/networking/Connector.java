package com.lpoo1718_t1g3.mariokart.networking;

import com.lpoo1718_t1g3.mariokart.Controller.GameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Class that writes and reads from server
 */
public class Connector {

    private static Connector ourInstance = new Connector();

    private Socket socket;
    private ObjectOutputStream ostream;
    private ObjectInputStream istream;

    private Connector() {
    }

    /**
     * Gets connector
     *
     * @return Returns current instance of Connector
     */
    public static Connector getInstance() {
        return ourInstance;
    }

    /**
     * Connects to server socket and initializes i/o streams
     *
     * @param addr server ip
     * @param port server port
     * @return Returns true on success and false otherwise
     */
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

    private void startReceiver() {
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
                    while ((input = (Message) istream.readObject()) != null && socket.isConnected()) {
                        handleMessage(input);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Writes message to server
     *
     * @param o Message to be written to server
     */
    public void write(Message o) {
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
                    } catch (SocketException e) {
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

    /**
     * Disconnects client
     */
    public void disconnect() {
        GameController.getInstance().handleDisconnectMessage(null);
        try {
            this.socket.close();
            istream.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message m) {
        switch (m.getType()) {
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

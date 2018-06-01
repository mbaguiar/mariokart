package com.lpoo1718_t1g3.mariokart.networking;

import com.lpoo1718_t1g3.mariokart.model.GameModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ServerManager implements Runnable {

    private ServerSocket socket;

    private int playerId = 1;

    private static final int serverId = -1;

    private static final String GOOGLE_URL = "google.pt";

    private static final int TEST_PORT = 80;

    private String localIp = "default_ip";

    private int port = 4444;

    public String getLocalIp() {
        return localIp;
    }

    public int getPort() {
        return port;
    }

    private ArrayList<ClientManager> clients = new ArrayList<ClientManager>();

    public ServerManager() {
        try {
            Socket testSocket = new Socket(GOOGLE_URL, TEST_PORT);
            localIp = testSocket.getLocalAddress().getHostAddress();
            port = testSocket.getLocalPort();
            testSocket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            socket = new ServerSocket(port);
            GameModel.getInstance().setQrCode(QRCodeUtilities.generateQRCode(localIp, port));
            System.out.println("Server opened: " + localIp + ":" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(this).start();

    }


    @Override
    public void run() {
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = socket.accept();
                clientSocket.setTcpNoDelay(true);
                System.out.println("Player " + playerId + " connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClientManager c = new ClientManager(clientSocket, playerId++);
            clients.add(c);
            c.start();
        }
    }

    public void writeToClient(Message m, int id) {
        m.setSenderId(serverId);
        ClientManager c;
        if ((c = findClientWithId(id)) != null) {
            c.write(m);
        }
    }

    private ClientManager findClientWithId(int id) {
        for (ClientManager c : clients) {
            if (c.getPlayerId() == id) return c;
        }
        return null;
    }
}

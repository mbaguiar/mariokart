package com.lpoo1718_t1g3.mariokart.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerManager implements Runnable {

    private ServerSocket socket;

    private static final String GOOGLE_URL = "google.pt";

    private static final int TEST_PORT = 80;

    public ServerManager(){
        String localIP = "default_ip";
        int port = 4444;
        try {
            Socket testSocket = new Socket(GOOGLE_URL, TEST_PORT);
            localIP = testSocket.getLocalAddress().getHostAddress();
            port = testSocket.getLocalPort();
            testSocket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            socket = new ServerSocket(port);
            System.out.println("Server opened: " + localIP + ":" + port);
        } catch (IOException e){
            e.printStackTrace();
        }

        new Thread(this).start();

    }


    @Override
    public void run() {
        while (true){
            Socket clientSocket = null;
            try {
                clientSocket = socket.accept();
                //clientSocket.setTcpNoDelay(true);
                System.out.println("Connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
            new ClientManager(clientSocket).start();
        }
    }
}

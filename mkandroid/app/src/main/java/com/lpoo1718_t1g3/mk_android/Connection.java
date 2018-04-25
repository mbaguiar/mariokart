package com.lpoo1718_t1g3.mk_android;

import java.io.IOException;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private String address;
    private int port;

    Connection(String addr, int port) {
        this.address = addr;
        this.port = port;
    }

    public Socket connect() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(address, port);
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
}

package com.lpoo1718_t1g3.mk_android;

import java.io.IOException;
import java.net.Socket;

public class Connection {

    private Socket socket;

    Connection(String addr, int port) throws IOException {
        socket = new Socket(addr, port);
    }

    public Socket getSocket() {
        return socket;
    }
}

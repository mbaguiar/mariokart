package com.lpoo1718_t1g3.mariokart.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connector {

    private static Connector ourInstance = new Connector();

    private Socket socket;
    private ObjectOutputStream ostream;

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
                    //socket.setTcpNoDelay(true);
                    ostream = new ObjectOutputStream(socket.getOutputStream());
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

    public void write(Message o){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                if (ostream != null) {
                    try {
                        ostream.writeObject(obj);
                        ostream.reset();
                        //System.out.println("wrote msg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).run();*/

        if (ostream != null) {
            try {
                ostream.writeObject(o);
                ostream.flush();
                //ostream.reset();
                //System.out.println("wrote msg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

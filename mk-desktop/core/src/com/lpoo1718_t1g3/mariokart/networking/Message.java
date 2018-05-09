package com.lpoo1718_t1g3.mariokart.networking;

import java.io.Serializable;
import java.util.Hashtable;

public class Message implements Serializable {

    private MESSAGE_TYPE type;

    private SENDER sender;

    private String sender_handler = "server";

    private Hashtable<String, Object> options;

    enum MESSAGE_TYPE {CONNECTION, CONTROLLER_ACTIVITY, CHAR_PICK, TRACK_VOTE}

    enum SENDER {SERVER, CLIENT}

    Message(MESSAGE_TYPE t, SENDER s){
        type = t;
        sender = s;
        options = new Hashtable<String, Object>();
    }

    void addOption(String s, Object o){
        options.put(s, o);
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", options=" + options +
                '}';
    }
}

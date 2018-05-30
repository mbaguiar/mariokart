package com.lpoo1718_t1g3.mariokart.networking;

import java.io.Serializable;
import java.util.Hashtable;

public class Message implements Serializable {

    private MESSAGE_TYPE type;

    private SENDER sender;

    private int senderId;

    private Hashtable<String, Object> options;

    public enum MESSAGE_TYPE {CONNECTION, PLAYER_REGISTRY, DISCONNECTION, CONTROLLER_ACTIVITY, CHAR_PICK, TRACK_VOTE, STATE_UPDATE}

    public enum SENDER {SERVER, CLIENT}

    public Message(MESSAGE_TYPE t, SENDER s) {
        type = t;
        sender = s;
        options = new Hashtable<String, Object>();
    }

    public MESSAGE_TYPE getType() {
        return type;
    }

    public void addOption(String s, Object o) {
        options.put(s, o);
    }

    public Hashtable<String, Object> getOptions() {
        return options;
    }

    public int getSenderId() {
        return this.senderId;
    }

    public void setSenderId(int id) {
        this.senderId = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", sender=" + sender +
                ", senderId=" + senderId +
                ", options=" + options +
                '}';
    }
}

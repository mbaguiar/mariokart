package com.lpoo1718_t1g3.mariokart.networking;

import java.io.Serializable;
import java.util.Hashtable;

public class Message implements Serializable {

    private MESSAGE_TYPE type;

    private SENDER sender;

    private Hashtable<String, Object> options;

    public enum MESSAGE_TYPE {CONNECTION, CONTROLLER_ACTIVITY, CHAR_PICK, TRACK_VOTE}

    public enum SENDER {SERVER, CLIENT}

    public Message(MESSAGE_TYPE t, SENDER s){
        type = t;
        sender = s;
        options = new Hashtable<>();
    }

    public MESSAGE_TYPE getType(){
        return type;
    }

    public void addOption(String s, Object o){
        options.put(s, o);
    }

    public Hashtable<String, Object> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", options=" + options +
                '}';
    }
}

package com.lpoo1718_t1g3.mariokart.networking;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Class that represents a message exchanged by the server and the clients
 */
public class Message implements Serializable {

    private MESSAGE_TYPE type;

    private SENDER sender;

    private int senderId;

    private Hashtable<String, Object> options;

    /**
     * Message type
     */
    public enum MESSAGE_TYPE {
        CONNECTION, PLAYER_REGISTRY, DISCONNECTION, CONTROLLER_ACTIVITY, CHAR_PICK, POWER_UP
    }

    /**
     * Sender type
     */
    public enum SENDER {
        SERVER, CLIENT
    }

    /**
     * Constructs a message with the given type
     *
     * @param t Message type
     * @param s Message sender
     */
    public Message(MESSAGE_TYPE t, SENDER s) {
        type = t;
        sender = s;
        options = new Hashtable<String, Object>();
    }

    /**
     * Gets message type
     *
     * @return Returns type of Message
     */
    public MESSAGE_TYPE getType() {
        return type;
    }

    /**
     * Adds extra information to the message
     *
     * @param s information identifier
     * @param o extra information
     */
    public void addOption(String s, Object o) {
        options.put(s, o);
    }

    /**
     * Gets all message options
     *
     * @return Returns options from message
     */
    public Hashtable<String, Object> getOptions() {
        return options;
    }

    /**
     * Gets sender id
     *
     * @return Returns sender id
     */
    public int getSenderId() {
        return this.senderId;
    }

    /**
     * Sets message sender id
     *
     * @param id sender id
     */
    void setSenderId(int id) {
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

    /**
     * Character pick state
     */
    public enum char_pick_state {
        WAIT, PICK, PICKED, PICK_ERROR
    }
}

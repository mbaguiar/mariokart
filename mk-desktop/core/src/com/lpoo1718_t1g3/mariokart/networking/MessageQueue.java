package com.lpoo1718_t1g3.mariokart.networking;

import java.util.*;

public class MessageQueue {

    private Queue<Message> mQueue;

    private static MessageQueue ourInstance = new MessageQueue();

    public static MessageQueue getInstance() {
        return ourInstance;
    }

    private MessageQueue() {
        mQueue = new LinkedList<Message>();
    }

    void add(Message m){
        mQueue.add(m);
    }

    public Queue<Message> getmQueue() {
        return mQueue;
    }
}

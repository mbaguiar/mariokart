package com.lpoo1718_t1g3.mariokart.controller;

import com.lpoo1718_t1g3.mariokart.model.LobbyModel;
import com.lpoo1718_t1g3.mariokart.view.LobbyView;

public class LobbyController {

    private LobbyModel model;
    private LobbyView view;

    public LobbyController(){
        this.model = new LobbyModel();
        this.view = new LobbyView();
    }

}

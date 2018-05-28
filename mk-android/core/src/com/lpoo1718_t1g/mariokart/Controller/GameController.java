package com.lpoo1718_t1g.mariokart.Controller;

import com.lpoo1718_t1g.mariokart.MarioKart;
import com.lpoo1718_t1g.mariokart.Model.GameModel;
import com.lpoo1718_t1g.mariokart.View.ConnectionView;
import com.lpoo1718_t1g.mariokart.View.RegistryView;

public class GameController {

    private static GameController ourInstance = new GameController();
    public static GameController getInstance() {
        return ourInstance;
    }


    public void goToConnection() {
        MarioKart.getInstance().setScreen(new ConnectionView());
    }

    public void tryConnect(String ipAddress) {
        //parse ip
        //get response
        //if success -> set partyName and go to registry
        //else -> show error dialog

        //For the sake of testing:
        GameModel.getInstance().setPartyName("Road's MK Party");
        MarioKart.getInstance().setScreen(new RegistryView());

    }
}

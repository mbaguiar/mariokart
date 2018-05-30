package com.lpoo1718_t1g3.mariokart.Controller;

import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.Model.GameModel;
import com.lpoo1718_t1g3.mariokart.View.AccelerometerControlView;
import com.lpoo1718_t1g3.mariokart.View.ButtonControlView;
import com.lpoo1718_t1g3.mariokart.View.ConnectionView;
import com.lpoo1718_t1g3.mariokart.View.MenuView;
import com.lpoo1718_t1g3.mariokart.View.RegistryView;
import com.lpoo1718_t1g3.mariokart.networking.Connector;
import com.lpoo1718_t1g3.mariokart.networking.Message;

public class GameController {

    private static GameController ourInstance = new GameController();
    public static GameController getInstance() {
        return ourInstance;
    }

    public void goToConnection() {
        GameModel.getInstance().setNextScreen(GameModel.game_screen.CONNECTION);
    }

    public void updateStatus(){
        if (GameModel.getInstance().getNextScreen() == null) return;
        switch (GameModel.getInstance().getNextScreen()){
            case MENU:
                MarioKart.getInstance().setScreen(new MenuView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case CONNECTION:
                MarioKart.getInstance().setScreen(new ConnectionView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case REGISTRY:
                MarioKart.getInstance().setScreen(new RegistryView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case CONTROL:
                if (GameModel.getInstance().getAccelerometer()) MarioKart.getInstance().setScreen(new AccelerometerControlView());
                else MarioKart.getInstance().setScreen(new ButtonControlView());
                GameModel.getInstance().setNextScreen(null);
                break;
        }

    }

    public void tryConnect(String ipAddress) {
        String[] fullIp = ipAddress.split(":");
        String ip = fullIp[0];
        int port = Integer.parseInt(fullIp[1]);
        System.out.println("Ip: " + ip + "; port: " + port);
        if (Connector.getInstance().connect(ip, port) == null){
            //Error
            return;
        }

        System.out.println("Connection successful");
        this.connectionMessage();

        //set loading
        //get response
        //if success -> set partyName and go to registry
        //else -> show error dialog

    }

    public void controllerMessage(boolean t, boolean b, float d){
        Message m = new Message(Message.MESSAGE_TYPE.CONTROLLER_ACTIVITY, Message.SENDER.CLIENT);
        m.addOption("throttle", t);
        m.addOption("brake", b);
        m.addOption("direction", d);
        Connector.getInstance().write(m);
    }

    private void connectionMessage(){
        Message m = new Message(Message.MESSAGE_TYPE.CONNECTION, Message.SENDER.CLIENT);
        Connector.getInstance().write(m);
    }

    public void getConnectionAnswer(Message m) {
        if ((Boolean) m.getOptions().get("connectionSuccessful")){
            GameModel.getInstance().setPartyName((String) m.getOptions().get("partyName"));
            GameModel.getInstance().setNextScreen(GameModel.game_screen.REGISTRY);
        } else {
            GameModel.getInstance().setNextScreen(GameModel.game_screen.CONNECTION);
        }
    }

    public void getRegistryAnswer(Message m){
        if ((Boolean) m.getOptions().get("registrySuccessful")){
            GameModel.getInstance().setNextScreen(GameModel.game_screen.CONTROL);
        } else {
            GameModel.getInstance().setNextScreen(GameModel.game_screen.CONNECTION);
        }
    }

    public void tryRegister(String handle) {
        Message m = new Message(Message.MESSAGE_TYPE.PLAYER_REGISTRY, Message.SENDER.CLIENT);
        m.addOption("playerHandle", handle);
        Connector.getInstance().write(m);
    }
}

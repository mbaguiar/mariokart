package com.lpoo1718_t1g3.mariokart.Controller;


import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.model.Character;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.View.AccelerometerControlView;
import com.lpoo1718_t1g3.mariokart.View.ButtonControlView;
import com.lpoo1718_t1g3.mariokart.View.CharacterPickerView;
import com.lpoo1718_t1g3.mariokart.View.ConnectionView;
import com.lpoo1718_t1g3.mariokart.View.MenuView;
import com.lpoo1718_t1g3.mariokart.View.RegistryView;
import com.lpoo1718_t1g3.mariokart.networking.Connector;
import com.lpoo1718_t1g3.mariokart.networking.Message;

import java.util.ArrayList;

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
            case CHAR_PICK:
                MarioKart.getInstance().setScreen(new CharacterPickerView());
                GameModel.getInstance().setNextScreen(null);
                break;
            case CONTROL:
                GameModel.getInstance().setPowerUp(GameModel.object_type.NULL);
                if (GameModel.getInstance().getAccelerometer()) MarioKart.getInstance().setScreen(new AccelerometerControlView());
                else MarioKart.getInstance().setScreen(new ButtonControlView());
                GameModel.getInstance().setNextScreen(null);
                break;
        }

    }

    public void tryConnect(String ipAddress) {
        String[] fullIp = ipAddress.split(":");
        if (fullIp.length == 2) {
            String ip = fullIp[0];
            try {
                int port = Integer.parseInt(fullIp[1]);
                System.out.println("Ip: " + ip + "; port: " + port);
                if (Connector.getInstance().connect(ip, port) == null) {
                    return;
                }
                this.connectionMessage();
                /*if (!waitForResponse(GameModel.timeoutSecs)) GameModel.getInstance().setNextScreen(GameModel.game_screen.CONNECTION);
                GameModel.getInstance().setServerResponse(null);*/
            } catch (NumberFormatException e) {
                return;
            }
        }
    }

    public void controllerMessage(boolean t, boolean b, float d){
        Message m = new Message(Message.MESSAGE_TYPE.CONTROLLER_ACTIVITY, Message.SENDER.CLIENT);
        m.addOption("throttle", t);
        m.addOption("brake", b);
        m.addOption("direction", d);
        System.out.println(m.toString());
        Connector.getInstance().write(m);
    }

    private void connectionMessage(){
        Message m = new Message(Message.MESSAGE_TYPE.CONNECTION, Message.SENDER.CLIENT);
        Connector.getInstance().write(m);
    }

    public void handleConnectionMessage(Message m) {
        if ((Boolean) m.getOptions().get("connectionSuccessful")){
            GameModel.getInstance().setPartyName((String) m.getOptions().get("partyName"));
            GameModel.getInstance().setNextScreen(GameModel.game_screen.REGISTRY);
        } else {
            GameModel.getInstance().setNextScreen(GameModel.game_screen.CONNECTION);
        }
    }

    public void handleRegistryMessage(Message m){
        if ((Boolean) m.getOptions().get("registrySuccessful")){
            //Wait
            GameModel.getInstance().setPickState(null);
        } else {
            GameModel.getInstance().setNextScreen(GameModel.game_screen.CONNECTION);
        }
    }

    public void tryRegister(String handle) {
        Message m = new Message(Message.MESSAGE_TYPE.PLAYER_REGISTRY, Message.SENDER.CLIENT);
        m.addOption("playerHandle", handle);
        GameModel.getInstance().setPlayerHandle(handle);
        Connector.getInstance().write(m);
        /*if (!waitForResponse(GameModel.timeoutSecs)) GameModel.getInstance().setNextScreen(GameModel.game_screen.CONNECTION);
        GameModel.getInstance().setServerResponse(null);*/
    }

    public void handleCharPickMessage(Message m){
        if (GameModel.getInstance().getPickState() == null) {
            GameModel.getInstance().setNextScreen(GameModel.game_screen.CHAR_PICK);
            GameModel.getInstance().setCharacters((ArrayList<Character>) m.getOptions().get("characters"));
        }
        GameModel.getInstance().setPickState((Message.char_pick_state) m.getOptions().get("charPickState"));
        if (m.getOptions().get("charPickState") == Message.char_pick_state.PICKED){
            GameModel.getInstance().setSelectedCharacter(GameModel.getInstance().getCharacters().get(GameModel.getInstance().getSelectedCharacterIndex()));
        } else if (m.getOptions().get("charPickState") == Message.char_pick_state.PICK_ERROR){
            GameModel.getInstance().setPickState(Message.char_pick_state.PICK);
            GameModel.getInstance().setSelectedCharacter(null);
            GameModel.getInstance().setUnavailable(GameModel.getInstance().getSelectedCharacterIndex());
        }
    }

    public void pickMessage(int selectedIndex) {
        Message m = new Message(Message.MESSAGE_TYPE.CHAR_PICK, Message.SENDER.CLIENT);
        m.addOption("selectedIndex", selectedIndex);
        Connector.getInstance().write(m);
    }

    public void handleControlMessage(Message m){
        GameModel.getInstance().setNextScreen(GameModel.game_screen.CONTROL);
    }

    public void handlePowerUpMessage(Message m) {
        GameModel.getInstance().setPowerUp((GameModel.object_type) m.getOptions().get("powerUp"));
    }

    public void usePowerUp() {
        Message m = new Message(Message.MESSAGE_TYPE.POWER_UP, Message.SENDER.CLIENT);
        Connector.getInstance().write(m);
    }

    public void handleDisconnectMessage(Message m) {
        GameModel.getInstance().setNextScreen(GameModel.game_screen.CONNECTION);
    }

    public void disconnectPlayer() {
        Connector.getInstance().disconnect();
    }

    private boolean waitForResponse(long timeout){
        long t = System.currentTimeMillis();
        while (System.currentTimeMillis() - t <= timeout * 1000){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (GameModel.getInstance().getServerResponse() != null) return true;
        }
        return false;
    }
}

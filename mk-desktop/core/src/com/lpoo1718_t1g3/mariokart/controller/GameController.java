package com.lpoo1718_t1g3.mariokart.controller;

import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.TireBody;
import com.lpoo1718_t1g3.mariokart.controller.entities.TrackBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mariokart.view.CharacterPickerView;
import com.lpoo1718_t1g3.mariokart.view.LobbyView;
import com.lpoo1718_t1g3.mariokart.view.RaceView;

public class GameController {

    private static GameController ourInstance = new GameController();
    private RaceController raceController;
    private final World world;
    private final KartBody kartBody;
    private final TrackBody trackBody;
    private float accumulator;
    private boolean gas = false;
    private boolean left = false;
    private boolean right = false;

    private GameController() {
        world = new World(new Vector2(0, 0), true);
        world.clearForces();
        kartBody = new KartBody(world, GameModel.getInstance().getKart(), 1, 2, 10, 15, 20, 20,(float) Math.PI);
        trackBody = new TrackBody(world, GameModel.getInstance().getTrack1());
    }

    public static GameController getInstance() {
        return ourInstance;
    }


    public void update (float delta) {
        if (MarioKart.getInstance().getScreen().getClass() == RaceView.class){
            raceController.update(delta);
        }
    }

    public void handleInput(Message m){
        kartBody.update(delta);

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);


        for (Body body : bodies) {

            verifyBounds(body);

            if (!body.getUserData().equals(new Integer(0))) {
                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
                ((EntityModel) body.getUserData()).setRotation(MathUtils.radiansToDegrees * kartBody.getAngle() + 180);
            }


        }


    }

    public boolean registerPlayer(int playerId, String playerHandle){
        return GameModel.getInstance().addPlayer(playerId, playerHandle);
    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, 0, body.getAngle());

        if (body.getPosition().x > GameView.VIEWPORT_WIDTH)
            body.setTransform(GameView.VIEWPORT_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > GameView.VIEWPORT_WIDTH)
            body.setTransform(body.getPosition().x, GameView.VIEWPORT_WIDTH, body.getAngle());
    }

    private void handleMovement() {
        if (gas) setKartState(KartBody.acc_type.ACC_ACCELERATE);
        if (left) setKartState(KartBody.steer_type.STEER_LEFT);
        if (right) setKartState(KartBody.steer_type.STEER_RIGHT);
    }

    public void startLobby() {
        GameModel.getInstance().startServer();
        MarioKart.getInstance().setScreen(new LobbyView());
    }

    public void newConnection(Message m) {
        //test if connection is possible (max players, state of game, etc)
        Message returnMessage = new Message(Message.MESSAGE_TYPE.CONNECTION, Message.SENDER.SERVER);
        returnMessage.addOption("connectionSuccessful", true); // or false
        writeToClient(returnMessage, m.getSenderId());
    }

    public void writeToClient(Message m, int id){
        GameModel.getInstance().getServer().writeToClient(m, id);
    public void setKartState(KartBody.steer_type value) {
        kartBody.setSteer(value);
    }

    public void newPlayer(Message m) {
        Message returnMessage = new Message(Message.MESSAGE_TYPE.PLAYER_REGISTRY, Message.SENDER.SERVER);
        if (registerPlayer(m.getSenderId(), (String)m.getOptions().get("playerHandle"))){
            returnMessage.addOption("registrySuccessful", true);
        } else {
            returnMessage.addOption("registrySuccessful", false);
            returnMessage.addOption("error", "Player name already in use");
        }
        writeToClient(m, m.getSenderId());
    public void setKartState(KartBody.acc_type value) {
        kartBody.setAccelerate(value);
    }

    public void startGame(){
        MarioKart.getInstance().setScreen(new CharacterPickerView());
    }

    public void startRace() {
        raceController = new RaceController();
        MarioKart.getInstance().setScreen(new RaceView());
    }
}
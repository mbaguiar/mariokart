package com.lpoo1718_t1g3.mariokart.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo1718_t1g3.mariokart.controller.entities.KartBody;
import com.lpoo1718_t1g3.mariokart.model.GameModel;

public class GameController {

    private static GameController ourInstance = new GameController();
    private final World world;
    private final KartBody kartBody;

    private GameController() {
        world = new World(new Vector2(0, 0), true);
        kartBody = new KartBody(world, GameModel.getInstance().getKart());
    }

    public static GameController getInstance() {
        return ourInstance;
    }
}
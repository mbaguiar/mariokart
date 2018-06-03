package com.lpoo1718_t1g3.mariokart.tests;
import com.lpoo1718_t1g3.mariokart.model.GameModel;
import com.lpoo1718_t1g3.mariokart.model.Player;
import com.lpoo1718_t1g3.mariokart.model.Position;
import com.lpoo1718_t1g3.mariokart.model.Race;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;
import org.junit.Test;
import static com.lpoo1718_t1g3.mariokart.model.GameModel.object_type.NULL;
import static com.lpoo1718_t1g3.mariokart.model.Race.race_state.READY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameLogicTest {

    @Test
    public void TestAddPlayers() {
        GameModel.getInstance().addPlayer(1, "joao");
        GameModel.getInstance().addPlayer(2, "maria");
        assertEquals(2, GameModel.getInstance().getPlayers().size());
        assertEquals("joao", GameModel.getInstance().getPlayer(1).getPlayerHandle());
        assertEquals("maria", GameModel.getInstance().getPlayer(2).getPlayerHandle());
    }

    @Test
    public void TestPlayerCharacter() {
        GameModel.getInstance().addPlayer(1, "joao");
        GameModel.getInstance().addPlayer(2, "maria");
        GameModel.getInstance().getPlayer(1).setSelectedCharacter(GameModel.getInstance().getCharacters().get(0));
        GameModel.getInstance().getPlayer(2).setSelectedCharacter(GameModel.getInstance().getCharacters().get(1));
        assertEquals("Mario", GameModel.getInstance().getPlayer(1).getSelectedCharacter().getName());
        assertEquals("Luigi", GameModel.getInstance().getPlayer(2).getSelectedCharacter().getName());
    }


    @Test
    public void testCreateRace() {
        GameModel.getInstance().addPlayer(1, "joao");
        GameModel.getInstance().addPlayer(2, "maria");
        Race race = new Race(new TrackModel(0, 0, 0));
        assertEquals(READY, race.getState());
        for (Player player : GameModel.getInstance().getPlayers()) {
            race.addPosition(player.getPosition());
        }
        for (Position position : race.getPlayerPositions()) {
            assertEquals(position.laps, -1);
        }
    }


    @Test
    public void TestPlayerEndRace() {
        Player player = new Player(1, "joao");
        assertEquals(player.getPosition().laps, -1);
        player.getPosition().laps = 3;
        assertTrue(player.getPosition().isFinished());
    }

    @Test
    public void TestRaceOver() {
        GameModel.getInstance().addPlayer(1, "joao");
        GameModel.getInstance().addPlayer(2, "maria");
        Race race = new Race(new TrackModel(0, 0, 0));
        GameModel.getInstance().setCurrentRace(race);
        for (Player player : GameModel.getInstance().getPlayers()) {
            race.addPosition(player.getPosition());
        }

        for (Position position : race.getPlayerPositions()) {
            position.laps = 3;
        }

        assertTrue(race.raceOver());
    }

    @Test
    public void TestPlayerKartModel() {
        Player player = new Player(1, "joao");
        assertEquals(player.getKartModel().getPlayerId(), player.getPlayerId());
    }

    @Test
    public void TestKartGetObject() {
        Player player = new Player(1, "joao");
        assertEquals(NULL, player.getKartModel().getObject());
    }

}

package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;

import java.util.*;

import static com.lpoo1718_t1g3.mariokart.model.Race.race_state.*;

/**
 * Class that represents a race in the game
 */
public class Race {
    private TrackModel track;

    private List<Position> playerPositions = new ArrayList<Position>();

    /**
     * Race state
     */
    public  enum race_state {READY, SET, GO, RACE, OVER}
    private race_state state;

    /**
     * Constructs a race with the given  track
     * @param track race's track
     */
    public Race(TrackModel track) {
        this.track = track;
        state = race_state.READY;
    }

    /**
     * Sorts positions of the players
     */
    public void sortPositions() {
        Collections.sort(playerPositions, new Comparator<Position>() {
            @Override
            public int compare(com.lpoo1718_t1g3.mariokart.model.Position o1, com.lpoo1718_t1g3.mariokart.model.Position o2) {
                if (o1.laps == o2.laps) {
                    return Long.compare(o1.time, o2.time);
                }

                return - Integer.compare(o1.laps, o2.laps);
            }
        });

        for (com.lpoo1718_t1g3.mariokart.model.Position position : playerPositions) {
            System.out.println(position.laps + " " + position.time + " " + position.playerId );
        }
    }

    /**
     * Finish race
     */
    public void finishRace() {
        this.state = OVER;
    }

    /**
     * Gets the race's track
     * @return Returns the race's TrackModel
     */
    public TrackModel getTrack() {
        return track;
    }

    /**
     * Adds player's position to the race
     * @param position Position to be added
     */
    public void addPosition(Position position) {
        playerPositions.add(position);
    }

    /**
     * Gets all the player's positions in the race
     * @return Returns playerPositions
     */
    public List<Position> getPlayerPositions() {
        return playerPositions;
    }

    /**
     * Starts race
     */
    public void initRace() {
        for (Position position : playerPositions) {
            position.description = "Ready";
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                state = SET;
                for (Position position : playerPositions) {
                    position.description = "Set";
                }

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        state = GO;
                        for (Position position : playerPositions) {
                            position.description = "Go";
                        }

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                state = RACE;
                                for (Position position : playerPositions) {
                                    position.description = "0/3";
                                }

                                GameController.getInstance().getRaceController().enableKarts();
                            }
                        }, 1000);

                    }
                }, 1000);
            }
        }, 1000);
    }

    /**
     * Gets race state
     * @return Returns race's state
     */
    public race_state getState() {
        return state;
    }

    /**
     * Checks if the race is over
     * @return Returns true if the race is over and false
     */
    public boolean raceOver() {
        for (Position position : playerPositions) {
            if (position.laps != 3) return false;
        }

        return true;
    }
}

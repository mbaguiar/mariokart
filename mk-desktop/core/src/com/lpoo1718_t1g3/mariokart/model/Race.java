package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;

import java.util.*;

import static com.lpoo1718_t1g3.mariokart.model.Race.race_state.*;

public class Race {
    private TrackModel track;

    private List<Position> playerPositions = new ArrayList<Position>();
    public enum race_state {READY, SET, GO, RACE, OVER}
    private race_state state;

    public Race(TrackModel track) {
        this.track = track;
        state = race_state.READY;
    }

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

    public void finishRace() {
        this.state = OVER;
    }

    public TrackModel getTrack() {
        return track;
    }

    public void addPosition(Position position) {
        playerPositions.add(position);
    }

    public List<Position> getPlayerPositions() {
        return playerPositions;
    }

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

    public race_state getState() {
        return state;
    }

    public boolean raceOver() {
        for (Position position : playerPositions) {
            if (position.laps != 3) return false;
        }

        return true;
    }
}

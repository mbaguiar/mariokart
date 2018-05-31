package com.lpoo1718_t1g3.mariokart.model;

import com.lpoo1718_t1g3.mariokart.model.entities.TrackModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Race {

    private TrackModel track;

    private List<Position> playerPositions = new ArrayList<Position>();

    public Race(TrackModel track) {
        this.track = track;
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

    public TrackModel getTrack() {
        return track;
    }

    public void addPosition(Position position) {
        playerPositions.add(position);
    }

    public List<Position> getPlayerPositions() {
        return playerPositions;
    }
}

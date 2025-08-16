package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.interfaces.RadarDataListener;
import nl.saxion.ptbc.models.RadarDataPointModel;
import nl.saxion.ptbc.models.RoverCommandModel;

import java.util.ArrayList;

/**
 * Teleports to all given locations.
 * Used to automatically pick up mission items for testing purposes.
 */
public class CheatMission implements RadarDataListener {

    private RoverCommandModel[] teleports;
    private int nextTeleport = 0;
    private boolean done = false;

    /**
     * @param teleports the locations to teleport to
     */
    public CheatMission(RoverCommandModel[] teleports) {
        this.teleports = teleports;

        RadarDataController.addListener(this);
    }


    public boolean isDone() {
        return done;
    }


    @Override
    public void radarDataUpdated(ArrayList<RadarDataPointModel> radarData) {
        if (done)
            return;

        RoverCommandController.teleport(teleports[nextTeleport]);

        nextTeleport++;

        if (teleports.length == nextTeleport)
            done = true;
    }

}

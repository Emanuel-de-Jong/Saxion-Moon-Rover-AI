package nl.saxion.ptbc.models;

/**
 * A class representing the data in the mission_status.txt file
 */
public class MissionStatusModel {

    boolean detonatorCollected;
    boolean tntCollected;
    boolean ignited;


    public MissionStatusModel() {
        this.detonatorCollected = false;
        this.tntCollected = false;
        this.ignited = false;
    }

    public MissionStatusModel(boolean detonatorCollected, boolean tntCollected, boolean ignited) {
        this.detonatorCollected = detonatorCollected;
        this.tntCollected = tntCollected;
        this.ignited = ignited;
    }


    public boolean isDetonatorCollected() {
        return detonatorCollected;
    }

    public boolean isTntCollected() {
        return tntCollected;
    }

    public boolean isIgnited() {
        return ignited;
    }

}

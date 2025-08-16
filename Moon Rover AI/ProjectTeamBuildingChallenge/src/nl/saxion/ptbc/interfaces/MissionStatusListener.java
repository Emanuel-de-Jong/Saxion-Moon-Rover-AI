package nl.saxion.ptbc.interfaces;

import nl.saxion.ptbc.models.MissionStatusModel;

/**
 * Event listener for the MissionStatusController
 */
public interface MissionStatusListener {

    /**
     * Called when the mission status has been updated and read.
     *
     * @param missionStatus the new mission status
     */
    void missionStatusUpdated(MissionStatusModel missionStatus);

}

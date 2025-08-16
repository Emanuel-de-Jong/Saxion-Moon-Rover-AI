package nl.saxion.ptbc.interfaces;

import nl.saxion.ptbc.models.RadarDataPointModel;

import java.util.ArrayList;

/**
 * Event listener for the RadarDataController
 */
public interface RadarDataListener {

    /**
     * Called when the CSV has been updated and read.
     *
     * @param radarData the new radar data points
     */
    void radarDataUpdated(ArrayList<RadarDataPointModel> radarData);

}

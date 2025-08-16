package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.G;
import nl.saxion.ptbc.interfaces.RadarDataListener;
import nl.saxion.ptbc.models.RadarDataPointModel;
import nl.saxion.ptbc.views.ErrorDialogView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This controller reads the rover CSV file
 * and sends the RadarDataPointModels to its listeners.
 * <p>
 * It's static to make its functionality available anywhere without needing an instance.
 */
public class RadarDataController {

    private static ArrayList<RadarDataListener> listeners = new ArrayList<>();
    private static long prevLastModified = 0;

    private static ArrayList<RadarDataPointModel> radarData = new ArrayList<>();
    private static String csvPath = G.winFrogPath + "Com_Dir\\RadarDataFromFrog.csv";


    public static ArrayList<RadarDataPointModel> getRadarData() {
        return radarData;
    }


    /**
     * Starts the timer that calls updateRadarDataListener.
     *
     * @param timeBetweenReads the time interval between calls.
     */
    public static void start(int timeBetweenReads) {
        Timer updateRadarDataTimer = new Timer(timeBetweenReads, new updateRadarDataListener());
        updateRadarDataTimer.start();
    }

    public static ArrayList<RadarDataListener> getListeners() {
        return listeners;
    }

    public static void addListener(RadarDataListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(RadarDataListener listener) {
        if (!listeners.contains(listener)) {
            throw new IllegalArgumentException("listener is not in the list of listeners");
        }
        listeners.remove(listener);
    }

    /**
     * Reads the RadarDataFromFrog csv of the rover.
     *
     * @return boolean for if the csv was read
     */
    private static boolean readCSV() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(csvPath));
        } catch (FileNotFoundException e) {
            new ErrorDialogView("The radar data CSV could not be opened!", true);
            return false;
        }

        radarData.clear();
        while (scanner.hasNext()) {
            String[] lineParts = scanner.nextLine().split(";");

            if (lineParts.length < 7) {
                continue;
            }

            String dateTime = lineParts[0];
            double x = Double.parseDouble(lineParts[1]);
            double y = Double.parseDouble(lineParts[2]);
            double z = Double.parseDouble(lineParts[3]);
            double distance = Double.parseDouble(lineParts[4]);
            double roverX = Double.parseDouble(lineParts[5]);
            double roverZ = Double.parseDouble(lineParts[6]);

            radarData.add(new RadarDataPointModel(dateTime, x, y, z, distance, roverX, roverZ));
        }

        scanner.close();

        return true;
    }


    /**
     * Checks if the rover CSV got updated
     * and reads the CSV and updates the listeners if it was.
     */
    private static class updateRadarDataListener implements ActionListener {
        private static void sendRadarDataUpdated() {
            for (RadarDataListener listener : listeners)
                if (!radarData.isEmpty()) {
                    listener.radarDataUpdated(radarData);
                }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            File csv = new File(csvPath);

            if (!csv.exists())
                new ErrorDialogView("The radar data CSV could not be found!", false);

            if (prevLastModified == 0)
                prevLastModified = csv.lastModified();

            if (csv.lastModified() == prevLastModified)
                return;

            prevLastModified = csv.lastModified();

            boolean csvRead = readCSV();
            if (csvRead)
                sendRadarDataUpdated();
        }
    }

}

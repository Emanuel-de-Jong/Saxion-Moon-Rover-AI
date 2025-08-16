package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.G;
import nl.saxion.ptbc.interfaces.MissionStatusListener;
import nl.saxion.ptbc.models.MissionStatusModel;
import nl.saxion.ptbc.views.ErrorDialogView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller reads the mission status text file
 * and sends the MissionStatusModel to its listeners.
 * <p>
 * It's static to make its functionality available anywhere without needing an instance.
 */
public class MissionStatusController {

    private static ArrayList<MissionStatusListener> listeners = new ArrayList<>();
    private static long prevLastModified = 0;

    private static MissionStatusModel missionStatus = new MissionStatusModel();
    private static String missionStatusPath = G.winFrogPath + "Com_Dir\\mission_status.txt";


    public static MissionStatusModel getMissionStatus() {
        return missionStatus;
    }


    /**
     * Starts the timer that calls updateMissionStatusListener.
     *
     * @param timeBetweenReads the time interval between calls.
     */
    public static void start(int timeBetweenReads) {
        Timer updateMissionStatusTimer = new Timer(timeBetweenReads, new updateMissionStatusListener());
        updateMissionStatusTimer.start();
    }

    public static void addListener(MissionStatusListener listener) {
        listeners.add(listener);
    }

    /**
     * Reads the mission status file of the rover.
     *
     * @return boolean for if the file was read
     */
    private static boolean readMissionStatus() {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(missionStatusPath));
        } catch (IOException ex) {
            new ErrorDialogView("The mission status file could not be opened!", true);
            return false;
        }

        if (lines.size() < 2) {
            return false;
        }

        String[] statuses = lines.get(1).split(";");

        boolean detonatorCollected = statuses[0].equals("True");
        boolean tntCollected = statuses[1].equals("True");
        boolean ignited = statuses[2].equals("True");

        missionStatus = new MissionStatusModel(detonatorCollected, tntCollected, ignited);

        return true;
    }


    /**
     * Checks if the mission status got updated
     * and reads the mission status file and updates the listeners if it was.
     */
    private static class updateMissionStatusListener implements ActionListener {
        private static void sendMissionStatusUpdated() {
            for (MissionStatusListener listener : listeners)
                listener.missionStatusUpdated(missionStatus);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            File file = new File(missionStatusPath);

            if (!file.exists())
                new ErrorDialogView("The mission status file could not be found!", false);

            if (prevLastModified == 0)
                prevLastModified = file.lastModified();

            if (file.lastModified() == prevLastModified)
                return;

            prevLastModified = file.lastModified();

            boolean fileRead = readMissionStatus();
            if (fileRead)
                sendMissionStatusUpdated();
        }
    }

}

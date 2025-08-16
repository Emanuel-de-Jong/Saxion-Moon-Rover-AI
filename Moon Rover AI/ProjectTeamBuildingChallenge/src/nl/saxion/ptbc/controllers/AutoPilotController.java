package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.G;
import nl.saxion.ptbc.interfaces.MissionStatusListener;
import nl.saxion.ptbc.interfaces.RadarDataListener;
import nl.saxion.ptbc.models.MissionStatusModel;
import nl.saxion.ptbc.models.RadarDataPointModel;
import nl.saxion.ptbc.views.PopupDialogView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The auto pilot helps the rover find and follow the left wall.
 * It does this until it's 20 coordinate units away from the destination.
 */
public class AutoPilotController implements RadarDataListener, MissionStatusListener {

    private ControlPanelController controlPanelController;
    private JTextArea autoPilotConsole;

    private ArrayList<RadarDataPointModel> yellowRadarData;
    private ArrayList<RadarDataPointModel> greenRadarData;
    private ArrayList<RadarDataPointModel> pinkRadarData;
    private ArrayList<RadarDataPointModel> redRadarData;

    private Timer unstuckRoverTimer;
    private Timer pauseOnExplosion;

    private boolean active;
    private int leftCounter;


    public AutoPilotController(ControlPanelController controlPanelController) {
        this.controlPanelController = controlPanelController;
        this.autoPilotConsole = controlPanelController.getControlPanelView().getAutoPilotConsole();

        this.active = false;
        this.leftCounter = 0;

        yellowRadarData = new ArrayList<>();
        greenRadarData = new ArrayList<>();
        pinkRadarData = new ArrayList<>();
        redRadarData = new ArrayList<>();

        RadarDataController.addListener(this);
        MissionStatusController.addListener(this);

        unstuckRoverTimer = new Timer(7000, new UnstuckRoverListener());
        pauseOnExplosion = new Timer(10000, new PauseOnExplosionListener());
        pauseOnExplosion.setRepeats(false);
    }


    public int getLeftCounter() {
        return leftCounter;
    }


    /**
     * Checks if the rover x and y are both withing 20 coordinate units from the finish x and y.
     *
     * @return boolean for if the finish has been reached
     */
    public boolean checkFinishReached(double x, double z) {
        if (G.finishX == 0 || G.finishZ == 0)
            return false;

        double xDifference = Math.abs(G.finishX - x);
        double zDifference = Math.abs(G.finishZ - z);

        if (xDifference <= 20 && zDifference <= 20) {
            autoPilotConsole.append("finish reached\n");
            controlPanelController.getControlPanelView().getAutoPilotButton().doClick();
            new PopupDialogView("Autopilot Finished");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fills the yellow, green, pink and red radar data point lists with the given radar data points.
     * Each color has it's own rectangle (x and z range).
     *
     * @param radarData the unsorted radar data points
     */
    private void fillRadarDatas(ArrayList<RadarDataPointModel> radarData) {
        yellowRadarData.clear();
        greenRadarData.clear();
        pinkRadarData.clear();
        redRadarData.clear();

        double z, x;
        for (RadarDataPointModel radarPoint : radarData) {
            z = radarPoint.getZ();
            x = radarPoint.getX();

            if ((z >= -3 && z <= 3)
                    && (x > -7.5 && x <= 7.5)) {
                yellowRadarData.add(radarPoint);
                break;
            } else if ((z > 5 && z <= 17)
                    && (x > -7.5 && x <= 0)) {
                greenRadarData.add(radarPoint);
            } else if ((z > 5 && z <= 16)
                    && (x > 4 && x <= 7.5)) {
                pinkRadarData.add(radarPoint);
            } else if ((z >= 5 && z <= 10)
                    && (x > -16.5 && x < -7.5)) {
                redRadarData.add(radarPoint);
            }
        }
    }

    /**
     * Sends a command to the rover.
     * The command send depends on which radar data lists have points in them.
     */
    private void move() {
        if (!yellowRadarData.isEmpty()) {
            RoverCommandController.sendCommand(-2000, 0, 1400);
            autoPilotConsole.append("backwards\n");
        } else if (!greenRadarData.isEmpty()) { // && !redRadarData.isEmpty()
            RoverCommandController.sendCommand(2000, 20, 1300);
            autoPilotConsole.append("right\n");
        } else if (!pinkRadarData.isEmpty()) {
            RoverCommandController.sendCommand(2000, -20, 1300);
            autoPilotConsole.append("left\n");
        } else if (!redRadarData.isEmpty()) {
            RoverCommandController.sendCommand(2000, 0, 1500);
            autoPilotConsole.append("forwards\n");
        } else {
            RoverCommandController.sendCommand(2000, -20, 1300);
            autoPilotConsole.append("left\n");
        }
    }

    public void stuckInCircle() {
        if (yellowRadarData.isEmpty() &&
                greenRadarData.isEmpty() &&
                redRadarData.isEmpty()) {
            leftCounter++;
        } else {
            leftCounter = 0;
        }
    }

    @Override
    public void radarDataUpdated(ArrayList<RadarDataPointModel> radarData) {
        if (!active)
            return;

        boolean finishReached = checkFinishReached(radarData.get(0).getRoverX(), radarData.get(0).getRoverZ());
        if (finishReached) {
            stopAutoPilot();
            return;
        }

        fillRadarDatas(radarData);

        stuckInCircle();

        if (leftCounter >= 20) {
            leftCounter = 0;
            RoverCommandController.sendCommand(2000, 20, 2500);
        } else {
            move();
        }

        // The timer is reset to 0 each command and only calls actionPerformed when it hasn't been reset in time
        unstuckRoverTimer.restart();
    }

    public void startAutoPilot() {
        active = true;
        unstuckRoverTimer.start();
        radarDataUpdated(RadarDataController.getRadarData());
    }

    public void stopAutoPilot() {
        active = false;
        unstuckRoverTimer.stop();
    }

    @Override
    public void missionStatusUpdated(MissionStatusModel missionStatus) {
        if (missionStatus.isIgnited()) {
            stopAutoPilot();
            autoPilotConsole.append("waiting...\n");
            pauseOnExplosion.start();
        }
    }


    /**
     * The rover doesn't write to the CSV when it's stuck.
     * Go backwards when no new CSV data is available for [Timer Delay] seconds.
     */
    private class UnstuckRoverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RoverCommandController.sendCommand(-2000, 0, 2200);
            autoPilotConsole.append("rover stuck\n");
        }
    }

    private class PauseOnExplosionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startAutoPilot();
        }
    }

}

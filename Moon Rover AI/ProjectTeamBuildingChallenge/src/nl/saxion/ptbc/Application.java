package nl.saxion.ptbc;

import nl.saxion.ptbc.controllers.*;
import nl.saxion.ptbc.models.RoverCommandModel;
import nl.saxion.ptbc.views.DashboardFrameView;
import nl.saxion.ptbc.views.ErrorDialogView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Application {

    private static boolean CHEAT_MISSION = false;
    private static boolean START_ROVER_ON_STARTUP = false;


    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Set the swing theme to windows
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        // Reset the command text file so the rover reads the version as 0
        RoverCommandController.sendCommand(0, 0, 0);

        // GUI
        DashboardFrameView dashboardFrameView = new DashboardFrameView("Dashboard", 700, 630);
        DashboardFrameController dashboardFrameController = new DashboardFrameController(dashboardFrameView);

        // Start the csv updater
        RadarDataController.start(100);

        // Start the mission status updater
        MissionStatusController.start(100);

        // Set the finish coordinates
        G.finishX = 350;
        G.finishZ = 410;
        dashboardFrameController.getGraphPanelController().getGraphPanelView().setFinishXTextField(String.valueOf(G.finishX));
        dashboardFrameController.getGraphPanelController().getGraphPanelView().setFinishZTextField(String.valueOf(G.finishZ));

        // Teleport to all checkpoints of the mission
        if (CHEAT_MISSION) {
            new CheatMission(new RoverCommandModel[] {
                    new RoverCommandModel(191, -70, 0), // TNT
                    new RoverCommandModel(-38, 72, 60), // Detonator
                    new RoverCommandModel(260, 260, 80), // Before ignite
//                    new RoverCommandModel(279, 264, 80) // Ignite
            });
        }

        // Start the rover
        if (START_ROVER_ON_STARTUP) {
            ProcessBuilder pb = new ProcessBuilder(G.winFrogPath + "The Frog.exe");
            pb.directory(new File("WinFrog"));
            try {
                pb.start();
            } catch (IOException e) {
                new ErrorDialogView("The rover could not be started", true);
            }
        }
    }

}
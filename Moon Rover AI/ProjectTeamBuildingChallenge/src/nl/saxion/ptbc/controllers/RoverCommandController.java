package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.G;
import nl.saxion.ptbc.models.RoverCommandModel;
import nl.saxion.ptbc.views.ErrorDialogView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The controller with which commands are send to the rover text file.
 * <p>
 * It's static to make its functionality available anywhere without needing an instance.
 */
public class RoverCommandController {

    private static String messagePath = G.winFrogPath + "Com_Dir\\MessageToMoonRover.txt";
    private static int version = 0;

    private static ArrayList<RoverCommandModel> commandQueue = new ArrayList<>();
    private static Timer sendCommandInQueueTimer = new Timer(100, new SendCommandInQueueListener());


    private static boolean writeCommand(int version, RoverCommandModel roverCommandModel) {
        File file = new File(messagePath);

        if (!file.exists())
            new ErrorDialogView("The Rover message text file could not be found!", false);

        try {
            FileWriter fileWriter = new FileWriter(messagePath);
            fileWriter.write(String.format("\n%d;%s", version, roverCommandModel.toString()));
            fileWriter.close();
        } catch (IOException e) {
            commandQueue.add(roverCommandModel);
            sendCommandInQueueTimer.start();
            return false;
        }

        return true;
    }

    /**
     * Sends a command to the rover.
     *
     * @param roverCommandModel the command to send
     * @return boolean for if the command was send successfully
     */
    public static boolean sendCommand(RoverCommandModel roverCommandModel) {
        boolean result = writeCommand(version, roverCommandModel);

        if (result)
            version++;

        return result;
    }

    /**
     * Sends a command to the rover.
     *
     * @param power         the strength/speed of the movement
     * @param steeringAngle the strength of the turn
     * @param duration      the duration of the command in ms
     * @return boolean for if the command was send successfully
     */
    public static boolean sendCommand(int power, int steeringAngle, int duration) {
        return sendCommand(new RoverCommandModel(power, steeringAngle, duration));
    }

    public static boolean teleport(RoverCommandModel roverCommandModel) {
        return writeCommand(-1, roverCommandModel);
    }


    private static class SendCommandInQueueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean commandSend = sendCommand(commandQueue.get(0));
            if (!commandSend)
                return;

            commandQueue.remove(0);

            if (commandQueue.isEmpty())
                sendCommandInQueueTimer.stop();
        }
    }

}

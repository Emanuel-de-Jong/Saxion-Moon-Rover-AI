package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.enums.Direction;
import nl.saxion.ptbc.views.ControlPanelView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The control panel gives users direction buttons and sliders to control the rover manually.
 * It also has the GUI for the AutoPilot.
 */
public class ControlPanelController {

    private ControlPanelView controlPanelView;
    private AutoPilotController autoPilotController;


    public ControlPanelController(ControlPanelView controlPanelView) {
        this.controlPanelView = controlPanelView;

        this.controlPanelView.getFrontButton().addActionListener(new FrontButtonListener());
        this.controlPanelView.getFrontLeftButton().addActionListener(new FrontLeftButtonListener());
        this.controlPanelView.getFrontRightButton().addActionListener(new FrontRightButtonListener());
        this.controlPanelView.getBackButton().addActionListener(new BackButtonListener());
        this.controlPanelView.getBackLeftButton().addActionListener(new BackLeftButtonListener());
        this.controlPanelView.getBackRightButton().addActionListener(new BackRightButtonListener());
        this.controlPanelView.getAutoPilotButton().addActionListener(new AutoPilotButtonListener());

        autoPilotController = new AutoPilotController(this);
    }


    public ControlPanelView getControlPanelView() {
        return controlPanelView;
    }


    /**
     * Takes the power, angle and duration values from the view
     * and moves the rover to the given direction with those values.
     *
     * @param direction the direction in which the rover will move from its point of view
     */
    private void move(Direction direction) {
        if (controlPanelView.getPower() == -1)
            return;
        if (controlPanelView.getAngle() == -1)
            return;
        if (controlPanelView.getDuration() == -1)
            return;

        int power = controlPanelView.getPower();
        if (direction == Direction.back || direction == Direction.backLeft || direction == Direction.backRight) {
            power = -power;
        }

        int angle = 0;
        if (direction == Direction.frontLeft || direction == Direction.backLeft) {
            angle = -controlPanelView.getAngle();
        } else if (direction == Direction.frontRight || direction == Direction.backRight) {
            angle = controlPanelView.getAngle();
        }

        RoverCommandController.sendCommand(power, angle, controlPanelView.getDuration());
    }


    class FrontButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            move(Direction.front);
        }
    }

    class FrontLeftButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            move(Direction.frontLeft);
        }
    }

    class FrontRightButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            move(Direction.frontRight);
        }
    }

    class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            move(Direction.back);
        }
    }

    class BackLeftButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            move(Direction.backLeft);
        }
    }

    class BackRightButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            move(Direction.backRight);
        }
    }

    /**
     * Starts or stops the auto pilot depending on the current state.
     */
    class AutoPilotButtonListener implements ActionListener {
        private boolean autoPilotStarted = false;

        public void actionPerformed(ActionEvent e) {
            if (!autoPilotStarted) {
                autoPilotStarted = true;
                controlPanelView.getAutoPilotButton().setText("Stop");
                autoPilotController.startAutoPilot();
            } else {
                autoPilotStarted = false;
                controlPanelView.getAutoPilotButton().setText("Start");
                autoPilotController.stopAutoPilot();
            }

        }
    }

}
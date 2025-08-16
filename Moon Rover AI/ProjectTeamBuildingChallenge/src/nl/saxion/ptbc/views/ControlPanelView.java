package nl.saxion.ptbc.views;

import javax.swing.*;
import java.awt.*;

/**
 * The control panel gives users direction buttons and sliders to control the rover manually.
 * It also has the GUI for the AutoPilot.
 */
public class ControlPanelView {

    private JPanel controlPanel;

    private JPanel keyPanel;
    private JButton frontButton;
    private JButton frontLeftButton;
    private JButton frontRightButton;
    private JButton backButton;
    private JButton backLeftButton;
    private JButton backRightButton;

    private JPanel valuePanel;
    private JSlider powerSlider;
    private JSlider angleSlider;
    private JSlider durationSlider;

    private JPanel autoPilotPanel;
    private JButton autoPilotButton;
    private JTextArea autoPilotConsole;
    private JScrollPane autoPilotScrollPane;


    public ControlPanelView() {
        initComponents();
    }

    private void initComponents() {
        autoPilotScrollPane.setPreferredSize(new Dimension(1, 1));

        frontButton.setIcon(new ImageIcon("Assets\\Images\\arrow-front.png"));
        frontLeftButton.setIcon(new ImageIcon("Assets\\Images\\arrow-front-left.png"));
        frontRightButton.setIcon(new ImageIcon("Assets\\Images\\arrow-front-right.png"));
        backButton.setIcon(new ImageIcon("Assets\\Images\\arrow-back.png"));
        backLeftButton.setIcon(new ImageIcon("Assets\\Images\\arrow-back-left.png"));
        backRightButton.setIcon(new ImageIcon("Assets\\Images\\arrow-back-right.png"));
    }


    public JPanel getControlPanel() {
        return controlPanel;
    }

    public JButton getFrontButton() {
        return frontButton;
    }

    public JButton getFrontLeftButton() {
        return frontLeftButton;
    }

    public JButton getFrontRightButton() {
        return frontRightButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getBackLeftButton() {
        return backLeftButton;
    }

    public JButton getBackRightButton() {
        return backRightButton;
    }

    public JButton getAutoPilotButton() {
        return autoPilotButton;
    }

    public int getPower() {
        return powerSlider.getValue();
    }

    public int getAngle() {
        return angleSlider.getValue();
    }

    public int getDuration() {
        return durationSlider.getValue();
    }

    public JTextArea getAutoPilotConsole() {
        return autoPilotConsole;
    }

}

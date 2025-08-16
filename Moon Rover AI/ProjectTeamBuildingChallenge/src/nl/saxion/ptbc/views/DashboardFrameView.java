package nl.saxion.ptbc.views;

import javax.swing.*;
import java.awt.*;

/**
 * The dashboard frame is the main window of the application.
 * It has a graph panel and a control panel.
 */
public class DashboardFrameView {

    private MissionPanelView missionPanelView;
    private GraphPanelView graphPanelView;
    private ControlPanelView controlPanelView;

    private JFrame dashboardFrame;
    private JPanel dashboardPanel;
    private JPanel missionPanel;
    private JPanel graphPanel;
    private JPanel controlPanel;


    public DashboardFrameView(String title, int width, int height) {
        missionPanelView = new MissionPanelView();
        graphPanelView = new GraphPanelView();
        controlPanelView = new ControlPanelView();

        dashboardFrame = new JFrame();

        initComponents();

        dashboardFrame.setTitle(title);
        dashboardFrame.setSize(width, height);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setLocationRelativeTo(null);
        dashboardFrame.setResizable(false);
        dashboardFrame.setVisible(true);
    }

    private void initComponents() {
        missionPanel = missionPanelView.getMissionPanel();
        graphPanel = graphPanelView.getGraphPanel();
        controlPanel = controlPanelView.getControlPanel();

        dashboardPanel.add(missionPanel, BorderLayout.NORTH);
        dashboardPanel.add(graphPanel, BorderLayout.CENTER);
        dashboardPanel.add(controlPanel, BorderLayout.SOUTH);

        dashboardFrame.setContentPane(dashboardPanel);
    }


    public MissionPanelView getMissionPanelView() {
        return missionPanelView;
    }

    public GraphPanelView getGraphPanelView() {
        return graphPanelView;
    }

    public ControlPanelView getControlPanelView() {
        return controlPanelView;
    }

}

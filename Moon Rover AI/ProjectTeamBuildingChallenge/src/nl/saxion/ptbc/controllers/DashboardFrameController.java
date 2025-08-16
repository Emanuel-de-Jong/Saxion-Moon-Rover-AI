package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.views.ControlPanelView;
import nl.saxion.ptbc.views.DashboardFrameView;
import nl.saxion.ptbc.views.GraphPanelView;
import nl.saxion.ptbc.views.MissionPanelView;

/**
 * The dashboard frame is the main window of the application.
 * It has a graph panel and a control panel.
 */
public class DashboardFrameController {

    private MissionPanelController missionPanelController;
    private GraphPanelController graphPanelController;
    private ControlPanelController controlPanelController;

    private DashboardFrameView dashboardFrameView;
    private MissionPanelView missionPanelView;
    private GraphPanelView graphPanelView;
    private ControlPanelView controlPanelView;


    public DashboardFrameController(DashboardFrameView dashboardFrameView) {
        this.dashboardFrameView = dashboardFrameView;

        missionPanelView = dashboardFrameView.getMissionPanelView();
        graphPanelView = dashboardFrameView.getGraphPanelView();
        controlPanelView = dashboardFrameView.getControlPanelView();

        missionPanelController = new MissionPanelController(missionPanelView);
        graphPanelController = new GraphPanelController(graphPanelView);
        controlPanelController = new ControlPanelController(controlPanelView);
    }


    public MissionPanelController getMissionPanelController() {
        return missionPanelController;
    }

    public GraphPanelController getGraphPanelController() {
        return graphPanelController;
    }

    public ControlPanelController getControlPanelController() {
        return controlPanelController;
    }

    public DashboardFrameView getDashboardFrameView() {
        return dashboardFrameView;
    }

}

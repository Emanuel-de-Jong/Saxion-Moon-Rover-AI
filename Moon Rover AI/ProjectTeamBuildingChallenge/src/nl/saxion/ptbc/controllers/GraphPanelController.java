package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.G;
import nl.saxion.ptbc.interfaces.RadarDataListener;
import nl.saxion.ptbc.models.RadarDataPointModel;
import nl.saxion.ptbc.views.DrawPanel;
import nl.saxion.ptbc.views.ErrorDialogView;
import nl.saxion.ptbc.views.PopupDialogView;
import nl.saxion.ptbc.views.GraphPanelView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The graph panel visually shows the radar data of the rover from a top down perspective.
 */
public class GraphPanelController implements RadarDataListener {

    private GraphPanelView graphPanelView;
    private DrawPanel drawPanel;


    public GraphPanelController(GraphPanelView graphPanelView) {
        this.graphPanelView = graphPanelView;

        this.drawPanel = (DrawPanel) graphPanelView.getGraph();

        this.graphPanelView.getFinishXTextField().addActionListener(new finishXTextFieldListener());
        this.graphPanelView.getFinishZTextField().addActionListener(new finishZTextFieldListener());

        this.graphPanelView.getZoomInButton().addActionListener(new ZoomInButtonListener());
        this.graphPanelView.getZoomOutButton().addActionListener(new ZoomOutButtonListener());

        RadarDataController.addListener(this);
    }


    public GraphPanelView getGraphPanelView() {
        return graphPanelView;
    }


    @Override
    public void radarDataUpdated(ArrayList<RadarDataPointModel> radarData) {
        // Display the rover's x and z
        graphPanelView.setXLabel(String.format("%.2f", radarData.get(0).getRoverX()));
        graphPanelView.setZLabel(String.format("%.2f", radarData.get(0).getRoverZ()));

        // Draw the new points
        drawPanel.setPoints(radarData);
        drawPanel.repaint();
    }


    /**
     * Validates the user input
     * and changes the global finishX if it's a number.
     */
    class finishXTextFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double finishX;
            try {
                finishX = Double.parseDouble(e.getActionCommand());
            } catch (NumberFormatException ex) {
                new ErrorDialogView("X finish needs to be a number!", true);
                graphPanelView.setFinishXTextField(String.valueOf(G.finishX));
                return;
            }

            new PopupDialogView("X finish has been changed to: " + finishX);
            G.finishX = finishX;
        }
    }

    /**
     * Validates the user input
     * and changes the global finishY if it's a number.
     */
    class finishZTextFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double finishZ;
            try {
                finishZ = Double.parseDouble(e.getActionCommand());
            } catch (NumberFormatException ex) {
                new ErrorDialogView("Z finish needs to be a number!", true);
                graphPanelView.setFinishZTextField(String.valueOf(G.finishZ));
                return;
            }

            new PopupDialogView("Z finish has been changed to: " + finishZ);
            G.finishZ = finishZ;
        }
    }

    class ZoomInButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawPanel.zoomIn();
            drawPanel.repaint();
        }
    }

    class ZoomOutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawPanel.zoomOut();
            drawPanel.repaint();
        }
    }

}

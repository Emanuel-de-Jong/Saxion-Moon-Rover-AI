package nl.saxion.ptbc.views;

import javax.swing.*;

/**
 * The graph panel visually shows the radar data of the rover from a top down perspective.
 */
public class GraphPanelView {

    private JPanel graphPanel;
    private JPanel graph;

    private JTextField finishXTextField;
    private JTextField finishZTextField;

    private JLabel xLabel;
    private JLabel zLabel;

    private JButton ZoomInButton;
    private JButton ZoomOutButton;


    public GraphPanelView() {
        initComponents();
    }

    private void initComponents() {
    }

    private void createUIComponents() {
        graph = new DrawPanel();
    }


    public JPanel getGraphPanel() {
        return graphPanel;
    }

    public JPanel getGraph() {
        return graph;
    }

    public JButton getZoomInButton() {
        return ZoomInButton;
    }

    public JButton getZoomOutButton() {
        return ZoomOutButton;
    }

    public void setXLabel(String text) {
        xLabel.setText(text);
    }

    public void setZLabel(String text) {
        zLabel.setText(text);
    }

    public JTextField getFinishXTextField() {
        return finishXTextField;
    }

    public void setFinishXTextField(String text) {
        finishXTextField.setText(text);
    }

    public JTextField getFinishZTextField() {
        return finishZTextField;
    }

    public void setFinishZTextField(String text) {
        finishZTextField.setText(text);
    }

}





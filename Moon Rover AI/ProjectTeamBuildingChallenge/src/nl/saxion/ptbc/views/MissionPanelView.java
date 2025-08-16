package nl.saxion.ptbc.views;

import javax.swing.*;

/**
 * The mission panel shows the status of the current mission
 */
public class MissionPanelView {

    private JPanel missionPanel;
    private JLabel tntLabel;
    private JLabel detonatorLabel;
    private JLabel ignitedLabel;


    public MissionPanelView() {
        initComponents();
    }

    private void initComponents() {

    }


    public JPanel getMissionPanel() {
        return missionPanel;
    }

    public void setTntLabel(String text) {
        this.tntLabel.setText(text);
    }

    public void setDetonatorLabel(String text) {
        this.detonatorLabel.setText(text);
    }

    public void setIgnitedLabel(String text) {
        this.ignitedLabel.setText(text);
    }

}

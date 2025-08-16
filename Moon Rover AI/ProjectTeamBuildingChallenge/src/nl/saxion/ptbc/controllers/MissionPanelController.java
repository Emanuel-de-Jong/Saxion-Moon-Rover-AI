package nl.saxion.ptbc.controllers;

import nl.saxion.ptbc.interfaces.MissionStatusListener;
import nl.saxion.ptbc.models.MissionStatusModel;
import nl.saxion.ptbc.views.MissionPanelView;

/**
 * The mission panel shows the status of the current mission
 */
public class MissionPanelController implements MissionStatusListener {

    private MissionPanelView missionPanelView;


    public MissionPanelController(MissionPanelView missionPanelView) {
        this.missionPanelView = missionPanelView;

        MissionStatusController.addListener(this);
    }


    public MissionPanelView getMissionPanelView() {
        return missionPanelView;
    }


    @Override
    public void missionStatusUpdated(MissionStatusModel missionStatus) {
        if (missionStatus.isTntCollected()) {
            missionPanelView.setTntLabel("Yes");
        }

        if (missionStatus.isDetonatorCollected()) {
            missionPanelView.setDetonatorLabel("Yes");
        }

        if (missionStatus.isIgnited()) {
            missionPanelView.setIgnitedLabel("Yes");
        }
    }

}

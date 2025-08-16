package nl.saxion.ptbc;

import nl.saxion.ptbc.controllers.AutoPilotController;
import nl.saxion.ptbc.controllers.ControlPanelController;
import nl.saxion.ptbc.controllers.RadarDataController;
import nl.saxion.ptbc.controllers.RoverCommandController;
import nl.saxion.ptbc.models.RoverCommandModel;
import nl.saxion.ptbc.views.ControlPanelView;
import nl.saxion.ptbc.views.DrawPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    AutoPilotController autoPilotController;


    @BeforeEach
    public void init() {
        autoPilotController = new AutoPilotController(new ControlPanelController(new ControlPanelView()));
    }


    // RADARDATA START
    @Test
    public void RadarDataController() {
        // getRadarData
        assertNotNull(RadarDataController.getRadarData());
    }
    // RADARDATA END


    // ROVERCOMMAND START
    @Test
    public void RoverCommandModel() {
        // construct
        RoverCommandModel r = new RoverCommandModel(1, 2, 3);
        assertEquals("1;2;3", r.toString());
    }

    @Test
    public void RoverCommandController() {
        // sendCommand(RoverCommandModel(int, int, int))
        assertTrue(RoverCommandController.sendCommand(new RoverCommandModel(200, 10, 200)));
        // sendCommand(int, int, int)
        assertTrue(RoverCommandController.sendCommand(200, 10, 200));
    }
    // ROVERCOMMAND END


    // AUTOPILOT START
    @Test
    public void TestAutoPilot(){
        assertTrue(RadarDataController.getListeners().contains(autoPilotController));
    }

    @Test
    public void TestCheckFinishReached() {
        G.finishX = 100;
        G.finishZ = 100;

        assertFalse(autoPilotController.checkFinishReached(0,0));
    }

    @Test
    public void TestCheckFinishReached2() {
        G.finishX = 100;
        G.finishZ = 100;

        assertTrue(autoPilotController.checkFinishReached(100,100));
    }

    @Test
    public void TestCheckFinishReached3() {
        G.finishX = 100;
        G.finishZ = 100;

        assertTrue(autoPilotController.checkFinishReached(120,120));
    }

    @Test
    public void TestCheckFinishReached4() {
        G.finishX = 100;
        G.finishZ = 100;

        assertFalse(autoPilotController.checkFinishReached(121,121));
    }

    @Test
    public void TestEndlessCircle() {
        int prefCounter = autoPilotController.getLeftCounter();
        autoPilotController.stuckInCircle();
        assertTrue(prefCounter < autoPilotController.getLeftCounter());
    }
    // AUTOPILOT END



    // DRAWPANEL START
    DrawPanel drawPanel;

    // Test zoom in
    @Test
    public void zoomInTest() {
        drawPanel = new DrawPanel();
        int zoom = drawPanel.getZoom();
        drawPanel.zoomIn();
        zoom++;
        assertEquals(zoom,drawPanel.getZoom());
    }

    // Test zoom out
    @Test
    public void zoomOutTest() {
        drawPanel = new DrawPanel();
        int zoom = drawPanel.getZoom();
        drawPanel.zoomOut();
        zoom--;
        assertEquals(zoom,drawPanel.getZoom());
    }

    @Test
    public void setPointTestNull() {
        drawPanel = new DrawPanel();
        assertEquals(drawPanel.getPoints().size(), 0);
    }
    // DRAWPANEL END

}

package nl.saxion.ptbc.views;

import nl.saxion.ptbc.models.RadarDataPointModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * A draw panel/canvas that draws adarDataPoints to the screen in a top down view.
 */
public class DrawPanel extends JPanel {

    private Ellipse2D.Double roverCircle;

    private ArrayList<RadarDataPointModel> points;
    private boolean doDrawingFirstCall;
    private double startX, startZ;
    private int zoom = 5;


    public DrawPanel() {
        points = new ArrayList<>();
        doDrawingFirstCall = true;
    }


    public int getZoom() {
        return zoom;
    }

    public ArrayList<RadarDataPointModel> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<RadarDataPointModel> points) {
        this.points = points;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (doDrawingFirstCall) {
            doDrawingFirstCall = false;

            startX = (double) (this.getWidth() / 2) - 15;
            startZ = (double) this.getHeight() - 50;
            roverCircle = new Ellipse2D.Double(startX, startZ, 30, 30);
        }

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.GRAY);
        g2d.fill(roverCircle);

        double x, z;
        for (RadarDataPointModel point : points) {
            z = point.getZ();
            x = point.getX();

            if ((z >= -3 && z <= 3)
                    && (x > -7.5 && x <= 7.5)) {
                g2d.setColor(Color.YELLOW);
            } else if ((z > 5 && z <= 17)
                    && (x > -7.5 && x <= 0)) {
                g2d.setColor(Color.GREEN);
            } else if ((z > 5 && z <= 16)
                    && (x > 4 && x <= 7.5)) {
                g2d.setColor(Color.PINK);
            } else if ((z >= 5 && z <= 10)
                    && (x > -16.5 && x < -7.5)) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.BLUE);
            }

            x = startX + (point.getX() * zoom);
            z = startZ - (point.getZ() * zoom);
            Ellipse2D.Double circlePoint = new Ellipse2D.Double(x, z, 10, 10);
            g2d.fill(circlePoint);
        }
    }

    public void zoomIn() {
        if (zoom < 10) {
            zoom++;
        }
    }

    public void zoomOut() {
        if (zoom > 1) {
            zoom--;
        }
    }

}


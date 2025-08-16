package nl.saxion.ptbc.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * A class representation of a row in the rovers csv file.
 */
public class RadarDataPointModel {

    LocalDateTime dateTime;
    double x, y, z;
    double distance;
    double roverX;
    double roverZ;


    public RadarDataPointModel(String dateTime, double x, double y, double z, double distance, double roverX, double roverZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = distance;
        this.roverX = roverX;
        this.roverZ = roverZ;

        String dateTimeFormat;
        Locale currentLocale = Locale.getDefault();
        if (currentLocale.getCountry().equals("US")) {
            dateTimeFormat = "M/d/yyyy h:m:ss a";
        } else {
            dateTimeFormat = "dd/MM/yyyy HH:mm:ss";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        this.dateTime = LocalDateTime.parse(dateTime, formatter);
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getDistance() {
        return distance;
    }

    public double getRoverX() {
        return roverX;
    }

    public double getRoverZ() {
        return roverZ;
    }


    @Override
    public String toString() {
        return "dateTime: " + dateTime.toString() +
                "\nx: " + x +
                "\ny: " + y +
                "\nz: " + z +
                "\ndistance: " + distance +
                "\nroverX: " + roverX +
                "\nroverZ: " + roverZ;
    }

}

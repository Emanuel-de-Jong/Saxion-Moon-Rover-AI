package nl.saxion.ptbc.models;

/**
 * A class with all variables needed to send a command with the RoverCommandController.
 */
public class RoverCommandModel {

    // -2000 (back) to 2000 (front)
    private int power;
    // -20 (left) to 20 (right)
    private int steeringAngle;
    // 0 to 20000 (milliseconds)
    private int duration;


    /**
     * @param power         the strength/speed of the movement
     * @param steeringAngle the strength of the turn
     * @param duration      the duration of the command in ms
     */
    public RoverCommandModel(int power, int steeringAngle, int duration) {
        this.power = power;
        this.steeringAngle = steeringAngle;
        this.duration = duration;
    }


    /**
     * The return value is used to send commands to the rover.
     *
     * @return a String in the format of: power;steeringAngle;duration
     */
    @Override
    public String toString() {
        return String.format("%d;%d;%d", power, steeringAngle, duration);
    }

}

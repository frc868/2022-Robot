package frc.robot.auton.cameras;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class Astra {
    private static NetworkTable table;
    public static Astra instance;

    private Astra() {
        table = NetworkTableInstance.getDefault().getTable("FRCVision");
    }

    public static Astra getInstance() {
        if (instance == null) {
            instance = new Astra();
        }
        return instance;
    }

    /**
     * Get the x offset from a specified ball in degrees.
     * Balls are sorted by distance (0 is the closest)
     * @param ball_number ball to provide information for
     * @return the x offset in degrees
     * @author dr
     */
    public double getTx(int ball_number) {
        NetworkTableEntry tx = table.getEntry("tx");
        
        return tx.getDoubleArray(new double[1])[0];
    }

    /**
     * Get the y offset from a specified ball in degrees.
     * Balls are sorted by distance (0 is the closest)
     * @param ball_number ball to provide information for
     * @return the y offset in degrees
     * @author dr
     */
    public double getTy(int ball_number) {
        NetworkTableEntry ty = table.getEntry("ty");
        return ty.getDoubleArray(new double[1])[0];
    }

    /**
     * Get the distance from a specified ball in inches.
     * Balls are sorted by distance (0 is the closest)
     * @param ball_number ball to provide information for
     * @return the distance in inches
     * @author dr
     */
    public double getTd(int ball_number) {
        NetworkTableEntry td = table.getEntry("td");
        return td.getDoubleArray(new double[1])[0];
    }

    /**
     * Get the color of a specified ball.
     * Balls are sorted by distance (0 is the closest)
     * @param ball_number ball to provide information for
     * @return either "B" or "R" to specify ball color
     * @author dr
     */
    public String getColor(int ball_number) {
        NetworkTableEntry ty = table.getEntry("ty");
        String[] s = {"N"};
        return ty.getStringArray(s)[0];
    }
}

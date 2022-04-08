package frc.robot.subsystems;

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
     * Get the alliance that the Pi thinks we are (mainly for debug)
     * 
     * @return either "B" or "R"
     * @author dr
     */
    public String getAlliance() {
        NetworkTableEntry alliance = table.getEntry("alliance");
        return alliance.getString("");
    }

    /**
     * Get the x offset from a specified ball in degrees.
     * Balls are sorted by distance (0 is the closest).
     * This will only return balls that are our current
     * alliance (that can be set in the Driver Station).
     * 
     * @param ball_number ball to provide information for
     * @return the x offset in degrees
     * @author dr
     */
    public double getTx(int ball_number) {
        NetworkTableEntry tx = table.getEntry("tx");
        double[] out = { 0.0 };
        double[] arr = tx.getDoubleArray(out);
        return arr.length > 0 ? arr[0] : 0.0;
    }

    /**
     * Get the y offset from a specified ball in degrees.
     * Balls are sorted by distance (0 is the closest).
     * This will only return balls that are our current
     * alliance (that can be set in the Driver Station).
     * 
     * @param ball_number ball to provide information for
     * @return the y offset in degrees
     * @author dr
     */
    public double getTy(int ball_number) {
        NetworkTableEntry ty = table.getEntry("ty");
        double[] out = { 0.0 };
        double[] arr = ty.getDoubleArray(out);
        return arr.length > 0 ? arr[0] : 0.0;
    }

    /**
     * Get the distance from a specified ball in inches.
     * Balls are sorted by distance (0 is the closest)
     * This will only return balls that are our current
     * alliance (that can be set in the Driver Station).
     * 
     * @param ball_number ball to provide information for
     * @return the distance in inches
     * @author dr
     */
    public double getTd(int ball_number) {
        NetworkTableEntry td = table.getEntry("td");
        double[] out = { 0.0 };
        double[] arr = td.getDoubleArray(out);
        return arr.length > 0 ? arr[0] : 0.0;
    }
}

package frc.robot.auton.cameras;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.RobotMap;

public class Limelight {
    private static NetworkTable table;
    public static Limelight instance;

    private Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }
        return instance;
    }

    /**
     * Get whether or not the Limelight has any valid targets (0 or 1)
     * @return
     */
    public double getTv() {
        NetworkTableEntry tv = table.getEntry("tv");
        return tv.getDouble(0.0);
    }

    /**
     * Get the x offset from the target in degrees
     * @author dr
     */
    public double getTx() {
        NetworkTableEntry tx = table.getEntry("tx");
        return tx.getDouble(0.0);
    }

    /**
     * Get the y offset from the target in degrees
     * @author dr
     */
    public double getTy() {
        NetworkTableEntry ty = table.getEntry("ty");
        return ty.getDouble(0.0);
    }

    /**
     * Get the percentage the target takes 
     * of the frame (can be used to calc distance)
     * @author dr
     */
    public double getTa() {
        NetworkTableEntry ta = table.getEntry("ta");
        return ta.getDouble(0.0);
    }

    /**
     * Get the distance the limelight is from the camera.
     * @author dr
     */
    public double getDistance() {
        return (RobotMap.LIMELIGHT.HUB_HEIGHT
             - RobotMap.LIMELIGHT.LL_HEIGHT)
         / Math.tan(RobotMap.LIMELIGHT.ANGLE + this.getTy());
    }

}

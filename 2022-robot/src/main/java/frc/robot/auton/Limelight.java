package frc.robot.auton;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private static NetworkTable table;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double getTx() {
        NetworkTableEntry tx = table.getEntry("tx");
        return tx.getDouble(0.0);
    }
    public double getTy() {
        NetworkTableEntry ty = table.getEntry("ty");
        return ty.getDouble(0.0);
    }
    public double getTv() {
        NetworkTableEntry tv = table.getEntry("tv");
        return tv.getDouble(0.0);
    }
    public double getTa() {
        NetworkTableEntry ta = table.getEntry("ta");
        return ta.getDouble(0.0);
    }

}

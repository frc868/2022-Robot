package frc.robot.auton;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class Astra {
    private static NetworkTable table;

    public Astra() {
        table = NetworkTableInstance.getDefault().getTable("FRCVision");
    }

    public double getTx() {
        NetworkTableEntry tx = table.getEntry("tx");
        return tx.getDouble(0.0);
    }
    public double getTy() {
        NetworkTableEntry ty = table.getEntry("ty");
        return ty.getDouble(0.0);
    }
}

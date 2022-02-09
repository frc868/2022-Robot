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

    public double getTx() {
        NetworkTableEntry tx = table.getEntry("tx");
        double[] d = {0.0};
        return tx.getDoubleArray(d)[0];
    }
    public double getTy() {
        NetworkTableEntry ty = table.getEntry("ty");
        double[] d = {0.0};
        return ty.getDoubleArray(d)[0];
    }
    public String getColor() {
        NetworkTableEntry ty = table.getEntry("ty");
        String[] s = new String[1];
        s[0] = "N";
        return ty.getStringArray(s)[0];
    }
}

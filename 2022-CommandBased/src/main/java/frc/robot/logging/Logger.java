package frc.robot.logging;

import java.util.List;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger<T> {
    private T obj;
    private String subsystem;
    private String deviceName;
    private LogValue<?>[] values;

    public Logger(T obj, String subsystem, String deviceName, LogValue<?>[] values) {
        this.obj = obj;
        this.subsystem = subsystem;
        this.deviceName = deviceName;
        this.values = values;
    }

    public void run() {
        for (LogValue<?> v : values) {
            try {
                switch (v.getType().getCanonicalName()) {
                    case LogType.STRING:
                        SmartDashboard.putString(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (String) v.getFunc().call());
                        break;
                    case LogType.NUMBER:
                        SmartDashboard.putNumber(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (double) v.getFunc().call());
                        break;
                    case LogType.BOOLEAN:
                        SmartDashboard.putBoolean(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (boolean) v.getFunc().call());
                        break;
                    case LogType.DATA:
                        SmartDashboard.putData(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (Sendable) v.getFunc().call());
                        break;
                    case LogType.STRING_ARRAY:
                        SmartDashboard.putStringArray(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (String[]) v.getFunc().call());
                        break;
                    case LogType.NUMBER_ARRAY:
                        SmartDashboard.putNumberArray(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (double[]) v.getFunc().call());
                        break;
                    case LogType.BOOLEAN_ARRAY:
                        SmartDashboard.putBooleanArray(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (boolean[]) v.getFunc().call());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

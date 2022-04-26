package frc.robot.logging;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger<T> {
    public T obj;
    private String subsystem;
    private String deviceName;
    private LogItem<?>[] values;

    public Logger(T obj, String subsystem, String deviceName, LogItem<?>[] values) {
        this.obj = obj;
        this.subsystem = subsystem;
        this.deviceName = deviceName;
        this.values = values;
    }

    public void run() {
        for (LogItem<?> v : values) {
            try {
                switch (v.getType()) {
                    case STRING:
                        SmartDashboard.putString(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (String) v.getFunc().call());
                        break;
                    case NUMBER:
                        SmartDashboard.putNumber(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (double) v.getFunc().call());
                        break;
                    case BOOLEAN:
                        SmartDashboard.putBoolean(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (boolean) v.getFunc().call());
                        break;
                    case DATA:
                        SmartDashboard.putData(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (Sendable) v.getFunc().call());
                        break;
                    case STRING_ARRAY:
                        SmartDashboard.putStringArray(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (String[]) v.getFunc().call());
                        break;
                    case NUMBER_ARRAY:
                        SmartDashboard.putNumberArray(subsystem + "/" + deviceName + "/" + v.getKey(),
                                (double[]) v.getFunc().call());
                        break;
                    case BOOLEAN_ARRAY:
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

package frc.robot.logging;

import com.revrobotics.CANSparkMax;

public class LogProfileBuilder {

    public static LogValue<?>[] buildCANSparkMaxLogger(CANSparkMax obj) {
        return new LogValue<?>[] {
                new LogValue<Double>(Double.class, "Encoder Distance", obj.getEncoder()::getPosition),
                new LogValue<Double>(Double.class, "Encoder Distance Conversion Factor",
                        obj.getEncoder()::getPositionConversionFactor),
                new LogValue<Double>(Double.class, "Encoder Speed", obj.getEncoder()::getVelocity),
                new LogValue<Double>(Double.class, "Encoder Speed Conversion Factor",
                        obj.getEncoder()::getVelocityConversionFactor),
                new LogValue<Double>(Double.class, "Speed", obj::get),
                new LogValue<Double>(Double.class, "Bus Voltage", obj::getBusVoltage),
                new LogValue<Double>(Double.class, "Temperature", obj::getMotorTemperature),
                new LogValue<Double>(Double.class, "Output Current", obj::getOutputCurrent),
                new LogValue<Double>(Double.class, "Device ID", () -> (double) obj.getDeviceId()),
                new LogValue<String>(String.class, "Firmware Version", obj::getFirmwareString),
        };
    }
}

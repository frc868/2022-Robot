package frc.robot.logging;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

public class LogProfileBuilder {

    public static LogValue<?>[] buildCANSparkMaxLogValues(CANSparkMax obj) {
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

    public static LogValue<?>[] buildNavXLogValues(AHRS obj) {
        return new LogValue<?>[] {
                new LogValue<Double>(Double.class, "Pitch", () -> (double) obj.getPitch()),
                new LogValue<Double>(Double.class, "Roll", () -> (double) obj.getRoll()),
                new LogValue<Double>(Double.class, "Yaw", () -> (double) obj.getYaw()),
                new LogValue<Double>(Double.class, "Angle", () -> (double) obj.getPitch()),
                new LogValue<Boolean>(Boolean.class, "Is Calibrating", obj::isCalibrating),
                new LogValue<Boolean>(Boolean.class, "Is Connected", obj::isConnected),
                new LogValue<Boolean>(Boolean.class, "Is Moving", obj::isMoving),
                new LogValue<Boolean>(Boolean.class, "Is Rotating", obj::isRotating),
                new LogValue<Double>(Double.class, "Temperature", () -> (double) obj.getTempC())
        };
    }
}

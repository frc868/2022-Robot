package frc.robot.logging;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class LogProfileBuilder {

    public static LogItem<?>[] buildCANSparkMaxLogItems(CANSparkMax obj) {
        return new LogItem<?>[] {
                new LogItem<Double>(LogType.NUMBER, "Encoder Distance", obj.getEncoder()::getPosition),
                new LogItem<Double>(LogType.NUMBER, "Encoder Distance Conversion Factor",
                        obj.getEncoder()::getPositionConversionFactor),
                new LogItem<Double>(LogType.NUMBER, "Encoder Speed", obj.getEncoder()::getVelocity),
                new LogItem<Double>(LogType.NUMBER, "Encoder Speed Conversion Factor",
                        obj.getEncoder()::getVelocityConversionFactor),
                new LogItem<Double>(LogType.NUMBER, "Speed", obj::get),
                new LogItem<Double>(LogType.NUMBER, "Bus Voltage", obj::getBusVoltage),
                new LogItem<Double>(LogType.NUMBER, "Temperature", obj::getMotorTemperature),
                new LogItem<Double>(LogType.NUMBER, "Output Current", obj::getOutputCurrent),
                new LogItem<Double>(LogType.NUMBER, "Device ID", () -> (double) obj.getDeviceId()),
                new LogItem<String>(LogType.STRING, "Firmware Version", obj::getFirmwareString),
        };
    }

    public static LogItem<?>[] buildNavXLogItems(AHRS obj) {
        return new LogItem<?>[] {
                new LogItem<Double>(LogType.NUMBER, "Pitch", () -> (double) obj.getPitch()),
                new LogItem<Double>(LogType.NUMBER, "Roll", () -> (double) obj.getRoll()),
                new LogItem<Double>(LogType.NUMBER, "Yaw", () -> (double) obj.getYaw()),
                new LogItem<Double>(LogType.NUMBER, "Angle", () -> (double) obj.getPitch()),
                new LogItem<Boolean>(LogType.BOOLEAN, "Is Calibrating", obj::isCalibrating),
                new LogItem<Boolean>(LogType.BOOLEAN, "Is Connected", obj::isConnected),
                new LogItem<Boolean>(LogType.BOOLEAN, "Is Moving", obj::isMoving),
                new LogItem<Boolean>(LogType.BOOLEAN, "Is Rotating", obj::isRotating),
                new LogItem<Double>(LogType.NUMBER, "Temperature", () -> (double) obj.getTempC())
        };
    }

    public static LogItem<?>[] buildDoubleSolenoidLogItems(DoubleSolenoid obj) {
        return new LogItem<?>[] {
                new LogItem<Boolean>(LogType.BOOLEAN, "Position", () -> obj.get() == DoubleSolenoid.Value.kForward),
                new LogItem<Double>(LogType.NUMBER, "Forward Channel", () -> (double) obj.getFwdChannel()),
                new LogItem<Double>(LogType.NUMBER, "Reverse Channel", () -> (double) obj.getRevChannel())
        };
    }
}

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The Gyro "subsystem", responsible for returning the current angle of our bot.
 * 
 * @author dr
 */
public class Gyro extends SubsystemBase {
    private AHRS navx = new AHRS(SerialPort.Port.kMXP); // Instance of the NavX, used for Gyro reporting

    /**
     * Instantiates the gyro, calibrates it
     */
    public Gyro() {
        navx.calibrate();
    }

    /**
     * Gets the current angle of the gyro.
     */
    public double getAngle() {
        return navx.getAngle();
    }

    /**
     * Gets the current pitch the navX reports.
     */
    public double getPitch() {
        return navx.getPitch();
    }

    /**
     * Gets the current yaw the navX reports.
     */
    public double getYaw() {
        return navx.getYaw();
    }

    /**
     * Gets the current roll the navX reports.
     */
    public double getRoll() {
        return navx.getRoll();
    }

    /**
     * Reset the navX angle.
     */
    public void reset() {
        navx.reset();
    }

}

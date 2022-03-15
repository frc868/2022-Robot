package frc.robot.sensors;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogGyro;

public class Gyro {
    private static Gyro instance = null;
    private AnalogGyro gyro; // Instance of the NavX, used for Gyro reporting

    /**
     * This is the constructor for our Gyro class. It is private because
     * this is a singleton, and it should never be instantiated outside of
     * the getInstance() method.
     */
    private Gyro() {
        gyro = new AnalogGyro(RobotMap.Subsystems.Gyro.PORT);
        gyro.calibrate();
    }

    /**
     * Checks to see if the instance of this class has already been created.
     * If so, return it. If not, create it and return it.
     */
    public static Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro();
        }
        return instance;
    }

    /**
     * Gets the current angle of the gyro.
     */
    public double getAngle() {
        return gyro.getAngle();
    }

    /**
     * Reset the gyro angle.
     */
    public void resetAngle() {
        gyro.reset();
    }

}

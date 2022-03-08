package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter {
    private CANSparkMax s_primary, s_secondary;
    private PIDController shooter_pid;
    public static Shooter instance;

    private Limelight sLimelight = Limelight.getInstance();

    /**
     * Constructor of class Shooter. Used to instantiate the Shooter class. Should
     * only be used in getInstance method.
     */
    private Shooter() {

        // CAN ID setting
        s_primary = new CANSparkMax(RobotMap.SUBSYSTEMS.SHOOTER.S_PRIMARY, MotorType.kBrushless);
        s_secondary = new CANSparkMax(RobotMap.SUBSYSTEMS.SHOOTER.S_SECONDARY, MotorType.kBrushless);

        // Inverse logic
        s_primary.setInverted(RobotMap.SUBSYSTEMS.SHOOTER.IS_INVERTED);
        s_secondary.follow(s_primary, true);

        // PID controller instantiation
        shooter_pid = new PIDController(RobotMap.PID_CONSTANTS.SHOOTER_CONSTANTS.SHOOTER_PID.KP,
                RobotMap.PID_CONSTANTS.SHOOTER_CONSTANTS.SHOOTER_PID.KI,
                RobotMap.PID_CONSTANTS.SHOOTER_CONSTANTS.SHOOTER_PID.KD);

        // Tolerance setpoints
        shooter_pid.setTolerance(50);

    }

    /**
     * Creates a Singleton for class Shooter. Use this whenever Shooter and it's
     * methods need to be accessed
     * 
     * @return a static reference to class Climber
     */
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    /**
     * Sets the speed of the shooter
     * 
     * @param speed speed at which the motors should run; takes values from -1 to 1,
     *              numbers above or below are trumpicated to -1 or 1
     */
    public void setSpeed(double speed) {
        s_primary.set(speed);
    }

    /**
     * Resets the Shooter
     */
    public void reset() {
        s_primary.getEncoder().setPosition(0);
        shooter_pid.reset();
    }

    /**
     * Gets the current velocity of the shooter
     * 
     * @return current speed of the shooter, in rpm
     */
    public double getVelocity() {
        return s_primary.getEncoder().getVelocity();
    }

    /**
     * Sets the speed of the shooter using a PID controller
     * 
     * @param targetSpeed speed to which the shooter is trying to shoot at
     */
    public void shoot(double targetSpeed) {
        double calcSpeed = shooter_pid.calculate(getVelocity(), targetSpeed);
        setSpeed(calcSpeed);
    }

    /**
     * Gets the boolean value of where the PID controller is at
     * 
     * @return true if at target false if not
     */
    public boolean speedOnTarget() {
        return shooter_pid.atSetpoint();
    }

}

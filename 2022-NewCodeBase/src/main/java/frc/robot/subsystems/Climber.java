package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotMap;

public class Climber {
    private CANSparkMax c_primary, c_secondary;
    private DoubleSolenoid climber_extend, climber_lock;
    private PIDController climber_pid;
    public static Climber instance;

    /**
     * Constructor of class Climber. Used to instantiate the Climber class. Should
     * only be used in getInstance method.
     */
    private Climber() {

        // CAN ID setting
        c_primary = new CANSparkMax(RobotMap.Subsystems.Climber.C_PRIMARY, MotorType.kBrushless);
        c_secondary = new CANSparkMax(RobotMap.Subsystems.Climber.C_SECONDARY, MotorType.kBrushless);

        // Solenoid ID setting
        climber_extend = new DoubleSolenoid(PneumaticsModuleType.REVPH,
                RobotMap.Subsystems.Climber.CLIMBER_EXTEND_CHANNEL1,
                RobotMap.Subsystems.Climber.CLIMBER_EXTEND_CHANNEL2);
        climber_lock = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Subsystems.Climber.CLIMBER_LOCK_CHANNEL1,
                RobotMap.Subsystems.Climber.CLIMBER_LOCK_CHANNEL2);

        // Inverse logic
        c_primary.setInverted(RobotMap.Subsystems.Climber.IS_INVERTED);
        c_secondary.follow(c_primary, true);

        // PID controller instantiation
        climber_pid = new PIDController(RobotMap.PIDConstants.Climber.KP,
                RobotMap.PIDConstants.Climber.KI,
                RobotMap.PIDConstants.Climber.KD);

        // Setpoint tolerance
        climber_pid.setTolerance(0.25);
    }

    /**
     * Creates a Singleton for class Climber. Use this whenever Climber and it's
     * methods need to be accessed
     * 
     * @return a static reference to class Climber
     */
    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    /**
     * Gets the current encoder value of the climber
     * 
     * @return current distance traveled, in radians, since last reset
     */
    public double getPosition() {
        return c_primary.getEncoder().getPosition();
    }

    /**
     * Sets the speed of the climber
     * 
     * @param speed speed at which the motors should run; takes values from -1 to 1,
     *              numbers above or below are trumpicated to -1 or 1
     */
    public void setSpeed(double speed) {
        c_primary.set(speed);
    }

    /**
     * Resets the Climber
     */
    public void reset() {
        c_primary.getEncoder().setPosition(0);
        climber_pid.reset();
    }

    /**
     * Extends the locking pneumatic
     */
    public void lockExtend() {
        climber_lock.set(Value.kReverse);
    }

    /**
     * Retracts the locking pneumatic
     */
    public void lockRetract() {
        climber_lock.set(Value.kForward);
    }

    /**
     * Drives the climber to target position
     * 
     * @param targetRadians target in radians
     */
    public void climberToPosition(double targetRadians) {
        double calcSpeed = climber_pid.calculate(getPosition(), targetRadians);
        setSpeed(calcSpeed);
    }

    /**
     * Gets the boolean value of where the PID controller is at
     * 
     * @return true if at target false if not
     */
    public boolean speedOnTarget() {
        return climber_pid.atSetpoint();
    }
}

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

/**
 * Climber subsystem, includes the climber arms, locks, and 2nd stage arms.
 * 
 * @author dr
 */
public class Climber extends SubsystemBase {
    /**
     * The primary climber motor.
     */
    private CANSparkMax c_primary = new CANSparkMax(Constants.Climber.C_PRIMARY, MotorType.kBrushless);
    /**
     * The secondary climber motor.
     */
    private CANSparkMax c_secondary = new CANSparkMax(Constants.Climber.C_SECONDARY, MotorType.kBrushless);
    /**
     * The double solenoid for the second stage of the climber
     */
    private DoubleSolenoid climberSecondStage = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            Constants.Climber.CLIMBER_EXTEND_CHANNEL1,
            Constants.Climber.CLIMBER_EXTEND_CHANNEL2);
    /**
     * The lock for the climber.
     */
    private DoubleSolenoid climber_lock = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            Constants.Climber.CLIMBER_LOCK_CHANNEL1,
            Constants.Climber.CLIMBER_LOCK_CHANNEL2);
    /**
     * The motor controller group containing both of the climber motors.
     */
    private MotorControllerGroup climberMotors = new MotorControllerGroup(c_primary, c_secondary);

    /**
     * The constructor for the class. Sets the climber motors to the inverted status
     * described in Constants.java.
     */
    public Climber() {
        climberMotors.setInverted(Constants.Climber.IS_INVERTED);
        addChild("Climber Motors", climberMotors);
    }

    /**
     * Gets the current encoder value of the climber
     * 
     * @return current distance traveled since last reset
     */
    public double getPosition() {
        return c_primary.getEncoder().getPosition();
    }

    /**
     * Sets the speed of the climber
     * 
     * @param speed speed from -1 to 1 at which to run the motors.
     */
    public void setSpeed(double speed) {
        climberMotors.set(speed);
    }

    /**
     * Resets the encoders by setting their positions to zero.
     */
    public void resetEncoders() {
        c_primary.getEncoder().setPosition(0);
    }

    /**
     * Extends the climber locking pneumatic.
     */
    public void extendLock() {
        climber_lock.set(Value.kReverse);
    }

    /**
     * Retracts the climber locking pneumatic.
     */
    public void retractLock() {
        climber_lock.set(Value.kForward);
    }

    /**
     * Get the state of the climber locks.
     * 
     * @return true if the locks are extended, false if not
     */
    public boolean getLock() {
        return climber_lock.get() == Value.kReverse;
    }

    /**
     * Extends the 2nd stage of the climber.
     */
    public void extendSecondStage() {
        climberSecondStage.set(Value.kForward);
    }

    /**
     * Retracts the 2nd stage of the climber.
     */
    public void retractSecondStage() {
        climberSecondStage.set(Value.kReverse);
    }
}

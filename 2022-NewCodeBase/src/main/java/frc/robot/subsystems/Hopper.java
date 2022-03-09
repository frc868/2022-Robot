package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;

public class Hopper {
    public static Hopper instance;
    private CANSparkMax motor;
    private DoubleSolenoid gatekeepers;

    /**
     * Constructor of class Intake. Used to instantiate the Intake class. Should
     * only be used in getInstance method.
     */
    private Hopper() {
        motor = new CANSparkMax(RobotMap.Subsystems.Hopper.H_MOTOR, MotorType.kBrushless);
        motor.setInverted(RobotMap.Subsystems.Hopper.IS_INVERTED);

        gatekeepers = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Subsystems.Hopper.GATEKEEPER_CHANNEL2,
                RobotMap.Subsystems.Hopper.GATEKEEPER_CHANNEL1);

    }

    /**
     * Creates a Singleton for class Hopper. Use this whenever Hopper and
     * it's methods need to be accessed
     * 
     * @return a static reference to class Hopper
     */
    public static Hopper getInstance() {
        if (instance == null) {
            instance = new Hopper();
        }
        return instance;
    }

    /**
     * Sets the gatekeepers to the in position (where they are holding the ball)
     */
    public void gatekeepersIn() {
        gatekeepers.set(Value.kForward); // TODO: check whether this is in or out
    }

    /**
     * Sets the gatekeepers to the out position (where they are no longer holding
     * the ball)
     */
    public void gatekeepersOut() {
        gatekeepers.set(Value.kReverse);
    }

    /**
     * Toggles the current state of the gatekeepers.
     */
    public void toggleGatekeepers() {
        gatekeepers.toggle();
    }

    /**
     * Runs the motor on the hopper.
     */
    public void run() {
        motor.set(1);
    }

    /**
     * Stops the motor on the hopper.
     */
    public void stop() {
        motor.set(0);
    }

}

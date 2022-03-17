package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Intake {
    private CANSparkMax i_primary;
    private DoubleSolenoid intake_solenoid;
    public static Intake instance;

    /**
     * Constructor of class Intake. Used to instantiate the Intake class. Should
     * only be used in getInstance method.
     */
    private Intake() {

        // CAN ID setting
        i_primary = new CANSparkMax(RobotMap.Subsystems.Intake.I_PRIMARY, MotorType.kBrushless);

        // Solenoid ID setting
        intake_solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,
                RobotMap.Subsystems.Intake.INTAKE_SOLENOID_CHANNEL2,
                RobotMap.Subsystems.Intake.INTAKE_SOLENOID_CHANNEL1);

        // Inverse logic
        i_primary.setInverted(RobotMap.Subsystems.Intake.IS_INVERTED);

    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    /**
     * Sets the intake to the up position.
     */
    public void setUp() {
        intake_solenoid.set(Value.kForward);
    }

    /**
     * Sets the intake to the down position.
     */
    public void setDown() {
        intake_solenoid.set(Value.kReverse);
    }

    /**
     * Runs the intake motors.
     */
    public void run() {
        i_primary.set(1);
    }

    /**
     * Runs the intake motors in reverse.
     */
    public void reverse() {
        i_primary.set(-1);
    }

    /**
     * Stops the intake motors.
     */
    public void stop() {
        i_primary.set(0);
    }

}

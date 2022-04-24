package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private CANSparkMax i_primary = new CANSparkMax(Constants.Intake.CANIDs.MOTOR,
            MotorType.kBrushless);
    private DoubleSolenoid solenoid = new DoubleSolenoid(
            PneumaticsModuleType.REVPH,
            Constants.Intake.Solenoids.INTAKE_CHANNEL_1,
            Constants.Intake.Solenoids.INTAKE_CHANNEL_2);

    public Intake() {
        i_primary.setInverted(Constants.Intake.IS_INVERTED);
    }

    /**
     * Sets the intake to the up position.
     */
    public void setUp() {
        solenoid.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Sets the intake to the down position.
     */
    public void setDown() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Runs the intake motors.
     */
    public void runMotors() {
        i_primary.set(1);
    }

    /**
     * Runs the intake motors in reverse.
     */
    public void reverseMotors() {
        i_primary.set(-1);
    }

    /**
     * Stops the intake motors.
     */
    public void stop() {
        i_primary.set(0);
    }
}

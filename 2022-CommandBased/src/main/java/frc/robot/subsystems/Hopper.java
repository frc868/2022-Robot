package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * The hopper subsystem, includes the hopper motors and the gatekeepers.
 * 
 * @author dr
 */
public class Hopper extends SubsystemBase {
    private CANSparkMax h_primary = new CANSparkMax(Constants.Hopper.CANIDs.MOTOR, MotorType.kBrushless);;
    private DoubleSolenoid gatekeepers = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            Constants.Hopper.Solenoids.GATEKEEPER_CHANNEL_2,
            Constants.Hopper.Solenoids.GATEKEEPER_CHANNEL_1);

    public Hopper() {
        h_primary.setInverted(Constants.Hopper.IS_INVERTED);
    }

    public void gatekeepersIn() {
        gatekeepers.set(DoubleSolenoid.Value.kForward);
    }

    public void gatekeepersOut() {
        gatekeepers.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleGatekeepers() {
        gatekeepers.toggle();
    }

    public void runMotor() {
        h_primary.set(1);
    }

    public void reverseMotor() {
        h_primary.set(-1);
    }

    public void stopMotor() {
        h_primary.set(0);
    }
}

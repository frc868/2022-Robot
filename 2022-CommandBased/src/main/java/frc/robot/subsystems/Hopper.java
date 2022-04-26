package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.logging.LogGroup;
import frc.robot.logging.LogProfileBuilder;
import frc.robot.logging.Logger;

/**
 * The hopper subsystem, includes the hopper motors and the gatekeepers.
 * 
 * @author dr
 */
public class Hopper extends SubsystemBase {
    private CANSparkMax motor = new CANSparkMax(Constants.Hopper.CANIDs.MOTOR, MotorType.kBrushless);;
    private DoubleSolenoid gatekeepers = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            Constants.Hopper.Solenoids.GATEKEEPER_CHANNEL_2,
            Constants.Hopper.Solenoids.GATEKEEPER_CHANNEL_1);

    private LogGroup logger = new LogGroup(
            new Logger<?>[] {
                    new Logger<CANSparkMax>(motor, "Hopper", "Motor",
                            LogProfileBuilder.buildCANSparkMaxLogItems(motor)),
                    new Logger<DoubleSolenoid>(gatekeepers, "Hopper", "Gatekeepers",
                            LogProfileBuilder.buildDoubleSolenoidLogItems(gatekeepers))
            });

    public Hopper() {
        motor.setInverted(Constants.Hopper.IS_INVERTED);
    }

    @Override
    public void periodic() {
        logger.run();
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
        motor.set(1);
    }

    public void reverseMotor() {
        motor.set(-1);
    }

    public void stopMotor() {
        motor.set(0);
    }
}

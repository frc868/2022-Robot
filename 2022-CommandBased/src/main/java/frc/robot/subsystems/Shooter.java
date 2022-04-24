package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    private CANSparkMax s_primary = new CANSparkMax(Constants.Shooter.CANIDs.PRIMARY,
            MotorType.kBrushless);
    private CANSparkMax s_secondary = new CANSparkMax(Constants.Shooter.CANIDs.SECONDARY,
            MotorType.kBrushless);
    private MotorControllerGroup shooterMotors = new MotorControllerGroup(s_primary, s_secondary);

    public Shooter() {
        shooterMotors.setInverted(Constants.Shooter.IS_INVERTED);
    }

    public void setSpeed(double speed) {
        shooterMotors.set(speed);
    }

    /**
     * Resets the Shooter
     */
    public void resetEncoders() {
        s_primary.getEncoder().setPosition(0);
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
     * Stops the shooter
     */
    public void stop() {
        setSpeed(0);
    }
}

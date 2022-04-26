package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.logging.LogGroup;
import frc.robot.logging.LogProfileBuilder;
import frc.robot.logging.Logger;

public class ShooterPIDFF extends PIDSubsystem {
    private CANSparkMax primaryMotor = new CANSparkMax(Constants.Shooter.CANIDs.PRIMARY,
            MotorType.kBrushless);
    private CANSparkMax secondaryMotor = new CANSparkMax(Constants.Shooter.CANIDs.SECONDARY,
            MotorType.kBrushless);
    private MotorControllerGroup shooterMotors = new MotorControllerGroup(primaryMotor, secondaryMotor);

    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.Shooter.kS, Constants.Shooter.kV);

    private LogGroup logger = new LogGroup(
            new Logger<?>[] {
                    new Logger<CANSparkMax>(primaryMotor, "Hopper", "Motor",
                            LogProfileBuilder.buildCANSparkMaxLogItems(primaryMotor)),
                    new Logger<CANSparkMax>(primaryMotor, "Hopper", "Motor",
                            LogProfileBuilder.buildCANSparkMaxLogItems(secondaryMotor)),
            });

    public ShooterPIDFF() {
        super(new PIDController(Constants.Shooter.kP, Constants.Shooter.kI, Constants.Shooter.kD));
        shooterMotors.setInverted(Constants.Shooter.IS_INVERTED);
    }

    @Override
    public void periodic() {
        logger.run();
    }

    @Override
    public double getMeasurement() {
        return getVelocity();
    }

    @Override
    public void useOutput(double output, double setpoint) {
        setSpeedVolts(output + feedforward.calculate(setpoint));
    }

    public void setSpeed(double speed) {
        shooterMotors.set(speed);
    }

    public void setSpeedVolts(double volts) {
        shooterMotors.setVoltage(volts);
    }

    /**
     * Resets the Shooter
     */
    public void resetEncoders() {
        primaryMotor.getEncoder().setPosition(0);
    }

    /**
     * Gets the current velocity of the shooter
     * 
     * @return current speed of the shooter, in rpm
     */
    public double getVelocity() {
        return primaryMotor.getEncoder().getVelocity();
    }

    /**
     * Stops the shooter
     */
    public void stop() {
        setSpeed(0);
    }
}

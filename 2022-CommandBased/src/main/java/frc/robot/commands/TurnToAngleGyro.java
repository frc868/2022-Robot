package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro;
import frc.robot.Constants;

public class TurnToAngleGyro extends PIDCommand {
    private final Drivetrain drivetrain;
    private final Gyro gyro;

    public TurnToAngleGyro(double setpoint, Drivetrain drivetrain, Gyro gyro) {
        super(new PIDController(Constants.Drivetrain.TurnToAngleGyroPID.kP, Constants.Drivetrain.TurnToAngleGyroPID.kI,
                Constants.Drivetrain.TurnToAngleGyroPID.kD), gyro::getPitch, setpoint, d -> drivetrain.tankDrive(d, -d),
                drivetrain, gyro);
        this.drivetrain = drivetrain;
        this.gyro = gyro;
        addRequirements(drivetrain, gyro);
    }

    @Override
    public void initialize() {
        this.drivetrain.resetEncoders();
        this.drivetrain.stop();
        this.gyro.reset();
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }

}

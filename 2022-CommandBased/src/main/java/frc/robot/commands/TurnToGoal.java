package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.sensors.Limelight;
import frc.robot.subsystems.Drivetrain;

public class TurnToGoal extends PIDCommand {
    private final Drivetrain drivetrain;
    // private final Limelight limelight;

    public TurnToGoal(Drivetrain drivetrain, Limelight limelight) {
        super(new PIDController(Constants.Drivetrain.TurnToGoalPID.kP, Constants.Drivetrain.TurnToGoalPID.kI,
                Constants.Drivetrain.TurnToGoalPID.kD), limelight::getTx, 0, d -> drivetrain.tankDrive(d, -d),
                drivetrain, limelight);

        this.drivetrain = drivetrain;
        // this.limelight = limelight;
        addRequirements(drivetrain, limelight);
        getController().setTolerance(0.5);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        // Get everything in a safe starting state.
        this.drivetrain.resetEncoders();
        this.drivetrain.stop();
        super.initialize();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}

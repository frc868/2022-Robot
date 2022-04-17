package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.sensors.Astra;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;

public class TurnToBall extends PIDCommand {
    private final Drivetrain drivetrain;
    // private final Astra astra;

    public TurnToBall(Drivetrain drivetrain, Astra astra) {
        super(new PIDController(Constants.Drivetrain.TurnToBallPID.kP, Constants.Drivetrain.TurnToBallPID.kI,
                Constants.Drivetrain.TurnToBallPID.kD), () -> astra.getTx(0), 0, d -> drivetrain.tankDrive(d, -d));

        this.drivetrain = drivetrain;
        // this.astra = astra;
        getController().setTolerance(0.5);
        addRequirements(drivetrain, astra);
    }

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

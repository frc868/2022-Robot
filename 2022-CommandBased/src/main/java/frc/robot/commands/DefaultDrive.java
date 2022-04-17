package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DefaultDrive extends CommandBase {
    private Drivetrain drivetrain;
    private DoubleSupplier leftSpeed, rightSpeed;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public DefaultDrive(Drivetrain drivetrain, DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
        this.drivetrain = drivetrain;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.tankDrive(leftSpeed.getAsDouble(), rightSpeed.getAsDouble());
    }
}

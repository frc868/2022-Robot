package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.RunShooterLockedSpeed;
import frc.robot.commands.TurnToGoal;
import frc.robot.sensors.Limelight;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class ShootSequence extends SequentialCommandGroup {
    public ShootSequence(Drivetrain drivetrain, Shooter shooter, Limelight limelight, Hopper hopper) {
        addCommands(
                new TurnToGoal(drivetrain, limelight),
                new ParallelCommandGroup(
                        new RunShooterLockedSpeed(shooter, limelight).withTimeout(5),
                        new SequentialCommandGroup(
                                new InstantCommand(hopper::gatekeepersIn),
                                new RunCommand(hopper::runMotor, hopper).withTimeout(1.5),
                                new InstantCommand(hopper::gatekeepersOut))));
    }
}

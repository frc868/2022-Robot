package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
                new ParallelRaceGroup(
                        new RunShooterLockedSpeed(shooter, limelight),
                        new WaitCommand(1)),
                new InstantCommand(hopper::gatekeepersIn),
                new RunCommand(hopper::runMotor, hopper),
                new InstantCommand(hopper::gatekeepersOut));
    }
}

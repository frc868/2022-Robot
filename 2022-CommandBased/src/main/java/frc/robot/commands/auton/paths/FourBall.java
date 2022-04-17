package frc.robot.commands.auton.paths;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.TurnToAngleGyro;
import frc.robot.commands.TurnToBall;
import frc.robot.commands.auton.ShootSequence;
import frc.robot.sensors.Astra;
import frc.robot.sensors.Limelight;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class FourBall extends SequentialCommandGroup {
    public FourBall(Drivetrain drivetrain, Shooter shooter, Intake intake, Hopper hopper, Limelight limelight,
            Astra astra, Gyro gyro) {
        addCommands(
                new InstantCommand(intake::setDown, intake),
                new WaitCommand(1),
                new ParallelRaceGroup(
                        new DriveStraight(0, drivetrain),
                        new RunCommand(intake::runMotors, intake)),

                new ShootSequence(drivetrain, shooter, limelight, hopper), // 1st and 2nd ball
                new TurnToAngleGyro(150, drivetrain, gyro),
                new TurnToBall(drivetrain, astra),
                new ParallelRaceGroup(
                        new DriveStraight(0, drivetrain),
                        new RunCommand(intake::runMotors, intake)),
                new TurnToAngleGyro(-25, drivetrain, gyro),
                new TurnToBall(drivetrain, astra),
                new ParallelRaceGroup(
                        new DriveStraight(0, drivetrain),
                        new RunCommand(intake::runMotors, intake)),
                new DriveStraight(-0, drivetrain), // drive back to goal
                new ShootSequence(drivetrain, shooter, limelight, hopper), // 3rd and 4th ball
                new InstantCommand(intake::setUp, intake));
    }
}

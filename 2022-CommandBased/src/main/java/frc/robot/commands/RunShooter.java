package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.sensors.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;

public class RunShooter extends PIDCommand {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private Shooter shooter;
    // private Limelight limelight;

    public RunShooter(Shooter shooter, Limelight limelight) {
        super(new PIDController(Constants.Shooter.kP, Constants.Shooter.kI, Constants.Shooter.kD), shooter::getVelocity,
                limelight::calcShooterSpeed,
                s -> shooter.setSpeed(s), shooter, limelight);

        this.shooter = shooter;
        getController().setTolerance(0.5);
        addRequirements(shooter, limelight);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;

public class FeedBalls extends CommandBase {
    private Hopper hopper;

    public FeedBalls(Hopper hopper) {
        this.hopper = hopper;
        addRequirements(hopper);
    }

    @Override
    public void execute() {
        hopper.runMotor();
    }

    @Override
    public void end(boolean interrupted) {
        hopper.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

package frc.robot.auton.paths;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.auton.AutonMap;
import frc.robot.auton.AutonPath;

public class BackupTwoBallOriginal extends AutonPath {

    private AutonState currentState = AutonState.intakeDown;
    private static Timer timer;

    private enum AutonState {
        intakeDown {
            @Override
            public void init() {

            }

            @Override
            public void execute() {
                Robot.intake.setDown();
            }

            @Override
            public AutonState nextState() {
                driveToBall.init();
                return driveToBall;
            }
        },
        waitOneSecond {
            @Override
            public void init() {
                timer.reset();
            }

            @Override
            public void execute() {

            }

            @Override
            public AutonState nextState() {
                if (timer.get() < 1) {
                    return this;
                } else {
                    return driveToBall;
                }
            }
        },
        driveToBall {
            @Override
            public void init() {
                Robot.drivetrain.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(11, 0.4, 60);

                Robot.drivetrain.driveStraightLeft(11, 0.4, 60);
                Robot.intake.run();
                System.out.println(Robot.drivetrain.getRightPosition() + " " + Robot.drivetrain.getLeftPosition());
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() < 10.5) && !(Robot.drivetrain.getLeftPosition() < 10.5)) {
                    Robot.drivetrain.stop();
                    Robot.intake.stop();
                    Robot.hopper.stop();
                    Robot.shooter.reset();
                    return turnToGoal;
                } else {
                    return this;
                }

            }
        },
        turnToGoal {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                Robot.drivetrain.turnToLimelight();
            }

            @Override
            public AutonState nextState() {
                if (!Robot.drivetrain.turnToLimelightAtTarget()) {
                    return this;
                }

                return driveToGoal;
            }
        },
        driveToGoal {
            @Override
            public void init() {

            }

            @Override
            public void execute() {
                Robot.drivetrain.driveToLimelight(5.3);
            }

            @Override
            public AutonState nextState() {
                if (!Robot.drivetrain.driveToLimelightAtTarget()) {
                    return this;
                }
                Robot.drivetrain.stop();
                Robot.hopper.stop();
                Robot.intake.stop();
                return shooterUpToSpeedOne;
            }
        },
        shooterUpToSpeedOne {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                System.out.println("shooting" + Robot.shooter.getPosition());
                Robot.shooter.shoot(2800);
            }

            @Override
            public AutonState nextState() {
                if (Robot.shooter.getPosition() < 100) {
                    return this;
                } else {
                    Robot.hopper.gatekeepersIn();
                    Robot.hopper.reset();
                    return feedBallOne;
                }
            }
        },
        feedBallOne {
            @Override
            public void init() {

            }

            @Override
            public void execute() {
                Robot.hopper.run();
            }

            @Override
            public AutonState nextState() {
                if (Robot.hopper.getPosition() < 200) {
                    return this;
                }
                Robot.shooter.reset();
                return intakeUp;
            }
        },
        intakeUp {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                Robot.intake.setUp();
            }

            @Override
            public AutonState nextState() {
                return done;
            }
        },
        done {
            @Override
            public void init() {

            }

            @Override
            public void execute() {
                System.out.println("done");
                Robot.drivetrain.setLeftSpeed(0);
                Robot.drivetrain.setRightSpeed(0);
                Robot.shooter.setSpeed(0);
                Robot.hopper.stop();
            }

            @Override
            public AutonState nextState() {
                return this;
            }
        };

        public abstract void init();

        public abstract void execute();

        public abstract AutonState nextState();

    }

    @Override
    public void run() {
        this.currentState.execute();
        this.currentState = this.currentState.nextState();
    }

    @Override
    public void reset() {
        this.currentState = AutonState.intakeDown;
    }

    @Override
    public String toString() {
        return "BackupTwoBallOriginal";
    }
}

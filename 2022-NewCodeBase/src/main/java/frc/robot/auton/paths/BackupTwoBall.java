package frc.robot.auton.paths;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.auton.AutonMap;
import frc.robot.auton.AutonPath;

public class BackupTwoBall extends AutonPath {

    private AutonState currentState = AutonState.intakeDown;
    private static Timer timer = new Timer();

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
                waitOneSecond.init();
                return waitOneSecond;
            }
        },
        waitOneSecond {
            @Override
            public void init() {
                timer.start();
            }

            @Override
            public void execute() {

            }

            @Override
            public AutonState nextState() {
                if (timer.get() < 1) {
                    return this;
                } else {
                    driveToBall.init();
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
            }

            @Override
            public AutonState nextState() {
                if ((Robot.drivetrain.getRightPosition() < 10.5) && (Robot.drivetrain.getLeftPosition() < 10.5)) {
                    return this;
                } else {
                    Robot.drivetrain.stop();
                    Robot.intake.stop();
                    Robot.hopper.stop();
                    Robot.shooter.reset();
                    return turnToGoal;
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
                } else {
                    return driveToGoal;
                }
            }
        },
        driveToGoal {
            @Override
            public void init() {

            }

            @Override
            public void execute() {
                Robot.drivetrain.driveToLimelight(RobotMap.Subsystems.Limelight.HIGH_GOAL_SHOT_DISTANCE);
            }

            @Override
            public AutonState nextState() {
                if (!Robot.drivetrain.driveToLimelightAtTarget()) {
                    return this;
                } else {
                    Robot.drivetrain.stop();
                    Robot.hopper.stop();
                    Robot.intake.stop();
                    shooterUpToSpeed.init();
                    return shooterUpToSpeed;
                }
            }
        },
        shooterUpToSpeed {
            @Override
            public void init() {
                timer.reset();
            }

            @Override
            public void execute() {
                Robot.shooter.shoot(2800);
            }

            @Override
            public AutonState nextState() {
                if (timer.get() < 2) {
                    return this;
                } else {
                    Robot.hopper.reset();
                    feedBalls.init();
                    return feedBalls;
                }
            }
        },
        feedBalls {
            @Override
            public void init() {
                Robot.hopper.gatekeepersIn();
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
                Robot.drivetrain.stop();
                Robot.shooter.stop();
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
        return "BackupTwoBall";
    }
}

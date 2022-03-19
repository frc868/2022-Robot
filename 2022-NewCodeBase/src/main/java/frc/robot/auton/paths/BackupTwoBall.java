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
                // Nothing, just waiting for the timer to finish
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
                Robot.drivetrain.driveStraightRight(8, 0.25, 60);
                Robot.drivetrain.driveStraightLeft(8, 0.25, 60);
                System.out.println(Robot.drivetrain.getRightPosition() + " " + Robot.drivetrain.getLeftPosition());
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() < 7.5) && !(Robot.drivetrain.getLeftPosition() < 7.5)) {
                    Robot.drivetrain.stop();
                    Robot.shooter.reset();
                    return shooterUpToSpeedOne;
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

                return shooterUpToSpeedOne;
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
                }
                return shooterUpToSpeed;
            }
        },
        shooterUpToSpeed {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                Robot.shooter.shoot(RobotMap.Subsystems.Shooter.HIGH_GOAL_RPM);
            }

            @Override
            public AutonState nextState() {
                if (Robot.shooter.getPosition() < 400) {
                    return this;
                } else {
                    return feedBallOne;
                }
            }
        },
        waitOneSecond2 {
            @Override
            public void init() {
                timer.reset();
            }

            @Override
            public void execute() {
                // Nothing, just waiting for the timer to finish
            }

            @Override
            public AutonState nextState() {
                if (timer.get() < 1) {
                    return this;
                } else {
                    return feedBallOne;
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
                if (!Robot.shooter.speedOnTarget()) {
                    return this;
                }
                Robot.shooter.reset();
                return shooterUpToSpeedTwo;
            }
        },
        shooterUpToSpeedTwo {
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
                if (Robot.shooter.getPosition() < 200) {
                    return this;
                } else {
                    Robot.hopper.gatekeepersIn();
                    return feedBallTwo;
                }
            }
        },
        feedBallTwo {
            @Override
            public void init() {

            }

            @Override
            public void execute() {
                Robot.hopper.run();
            }

            @Override
            public AutonState nextState() {
                if (!Robot.shooter.speedOnTarget()) {
                    return this;
                }
                Robot.shooter.stop();
                return done;
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
        return "BackupTwoBall";
    }
}
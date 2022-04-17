// 7.39 for third ball drive distance
// 1.27 left, -1.22 right encoder for turn
// -67.68 navx for turn

// 2.14 for turn
// 5.85 for dist
// 0.8 for turn

package frc.robot.auton.paths;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.auton.AutonPath;

public class ThreeBallPartial extends AutonPath {

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
                driveToSecondBall.init();
                return driveToSecondBall;
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
                    driveToSecondBall.init();
                    return driveToSecondBall;
                }
            }
        },
        driveToSecondBall {
            @Override
            public void init() {
                Robot.drivetrain.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(6.1, 0.55, 60);

                Robot.drivetrain.driveStraightLeft(6.1, 0.55, 60);
                Robot.intake.run();
                System.out.println(Robot.drivetrain.getRightPosition() + " " + Robot.drivetrain.getLeftPosition());
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() < 5.85) && !(Robot.drivetrain.getLeftPosition() < 5.85)) {
                    turnToGoal.init();
                    return turnToGoal;
                } else {
                    return this;
                }

            }
        },
        turnToGoal {
            @Override
            public void init() {
                Robot.drivetrain.stop();
                Robot.intake.stop();
                Robot.hopper.stop();
                Robot.shooter.reset();
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
                    shooterUpToSpeed.init();
                    return shooterUpToSpeed;
                }
            }
        },
        shooterUpToSpeed {
            // double shooterSpeed;

            @Override
            public void init() {
                Robot.drivetrain.stop();
                Robot.shooter.reset();
                // shooterSpeed = Robot.shooter.calcSpeed();
            }

            @Override
            public void execute() {
                System.out.println("shooting" + Robot.shooter.getPosition());
                Robot.shooter.shoot(Robot.shooter.calcSpeed());
            }

            @Override
            public AutonState nextState() {
                if (Robot.shooter.getPosition() < 100) {
                    return this;
                } else {
                    feedBallsOneAndTwo.init();
                    return feedBallsOneAndTwo;
                }
            }
        },
        feedBallsOneAndTwo {
            @Override
            public void init() {
                Robot.hopper.gatekeepersIn();
                Robot.hopper.reset();
            }

            @Override
            public void execute() {
                Robot.hopper.run();
            }

            @Override
            public AutonState nextState() {
                if (Robot.hopper.getPosition() < 300) {
                    return this;
                }
                turnToRoughlyThirdBall.init();
                return turnToRoughlyThirdBall;
            }
        },
        turnToRoughlyThirdBall {
            @Override
            public void init() {
                Robot.drivetrain.reset();
                Robot.drivetrain.stop();
                Robot.hopper.stop();
                Robot.intake.stop();
                Robot.shooter.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(-2.6, 1.4, 60);
                Robot.drivetrain.driveStraightLeft(2.6, 1.4, 60);
            }

            @Override
            public AutonState nextState() {
                if (Robot.drivetrain.getLeftPosition() > 2.14) {
                    drivePartialDistance.init();
                    return drivePartialDistance;
                } else {
                    return this;
                }
            }
        },
        drivePartialDistance {
            @Override
            public void init() {
                Robot.drivetrain.reset();
                Robot.drivetrain.stop();
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(4.3, 0.7, 60);
                Robot.drivetrain.driveStraightLeft(4.3, 0.7, 60);
                // Robot.intake.run();
                System.out.println(Robot.drivetrain.getRightPosition() + " " + Robot.drivetrain.getLeftPosition());
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() < 4) && !(Robot.drivetrain.getLeftPosition() < 4)) {
                    done.init();
                    return done;
                } else {
                    return this;
                }

            }
        },
        intakeUp {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                Robot.intake.setUp();
                Robot.hopper.gatekeepersOut();
            }

            @Override
            public AutonState nextState() {
                return done;
            }
        },
        done {
            @Override
            public void init() {
                Robot.drivetrain.stop();
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
        return "ThreeBallPartial";
    }
}

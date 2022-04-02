// 7.39 for third ball drive distance
// 1.27 left, -1.22 right encoder for turn
// -67.68 navx for turn

package frc.robot.auton.paths;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.auton.AutonPath;

public class FiveBall extends AutonPath {

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
                Robot.drivetrain.driveStraightRight(7.1, 0.4, 60);

                Robot.drivetrain.driveStraightLeft(7.1, 0.4, 60);
                Robot.intake.run();
                System.out.println(Robot.drivetrain.getRightPosition() + " " + Robot.drivetrain.getLeftPosition());
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() < 6.7) && !(Robot.drivetrain.getLeftPosition() < 6.7)) {
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
            double shooterSpeed;

            @Override
            public void init() {
                Robot.drivetrain.stop();
                shooterSpeed = Robot.shooter.calcSpeed();
            }

            @Override
            public void execute() {
                System.out.println("shooting" + Robot.shooter.getPosition());
                Robot.shooter.shoot(shooterSpeed);
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
                driveBackToStartingPosition.init();
                return driveBackToStartingPosition;
            }
        },
        driveBackToStartingPosition {
            @Override
            public void init() {
                Robot.drivetrain.reset();
                Robot.hopper.stop();
                Robot.shooter.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(-4.8, 0.4, 60);
                Robot.drivetrain.driveStraightLeft(-4.8, 0.4, 60);
                System.out.println(Robot.drivetrain.getRightPosition() + " " + Robot.drivetrain.getLeftPosition());
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() > -4.6) && !(Robot.drivetrain.getLeftPosition() > -4.6)) {
                    turnToRoughlyThirdBall.init();
                    return turnToRoughlyThirdBall;
                } else {
                    return this;
                }
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
                Robot.drivetrain.driveStraightRight(-1.4, 0.4, 60);
                Robot.drivetrain.driveStraightLeft(1.4, 0.4, 60);
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() > -1.27) && !(Robot.drivetrain.getLeftPosition() < 1.27)) {
                    turnToAstraThirdBall.init();
                    return turnToAstraThirdBall;
                } else {
                    return this;
                }
            }
        },
        turnToAstraThirdBall {
            @Override
            public void init() {
                Robot.drivetrain.stop();
                Robot.intake.stop();
                Robot.hopper.stop();
                Robot.shooter.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.turnToAstra();
            }

            @Override
            public AutonState nextState() {
                if (!Robot.drivetrain.turnToAstraAtTarget()) {
                    return this;
                } else {
                    driveToThirdBall.init();
                    return driveToThirdBall;
                }
            }
        },
        driveToThirdBall {
            @Override
            public void init() {
                Robot.drivetrain.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(7.5, 0.4, 60);
                Robot.drivetrain.driveStraightLeft(7.5, 0.4, 60);
                Robot.intake.run();
                System.out.println(Robot.drivetrain.getRightPosition() + " " + Robot.drivetrain.getLeftPosition());
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() < 7.39) && !(Robot.drivetrain.getLeftPosition() < 7.39)) {
                    turnToGoal2.init();
                    return turnToGoal2;
                } else {
                    return this;
                }

            }
        },
        turnToGoal2 {
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
                    shooterUpToSpeed2.init();
                    return shooterUpToSpeed2;
                }
            }
        },
        shooterUpToSpeed2 {
            double shooterSpeed;

            @Override
            public void init() {
                Robot.drivetrain.stop();
                shooterSpeed = Robot.shooter.calcSpeed();
            }

            @Override
            public void execute() {
                System.out.println("shooting" + Robot.shooter.getPosition());
                Robot.shooter.shoot(shooterSpeed);
            }

            @Override
            public AutonState nextState() {
                if (Robot.shooter.getPosition() < 100) {
                    return this;
                } else {
                    feedBallThree.init();
                    return feedBallThree;
                }
            }
        },
        feedBallThree {
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
                driveBackToStartingPosition.init();
                return driveBackToStartingPosition;
            }
        },
        turnToAstraFourthBall {
            @Override
            public void init() {
                Robot.drivetrain.stop();
                Robot.intake.stop();
                Robot.hopper.stop();
                Robot.shooter.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.turnToAstra();
            }

            @Override
            public AutonState nextState() {
                if (!Robot.drivetrain.turnToAstraAtTarget()) {
                    return this;
                } else {
                    driveToFourthBall.init();
                    return driveToFourthBall;
                }
            }
        },
        driveToFourthBall {
            @Override
            public void init() {
                Robot.drivetrain.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(11.5, 0.4, 60);
                Robot.drivetrain.driveStraightLeft(11.5, 0.4, 60); // untested coords
                Robot.intake.run();
                System.out.println(Robot.drivetrain.getRightPosition() + " " + Robot.drivetrain.getLeftPosition());
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() < 11.39) && !(Robot.drivetrain.getLeftPosition() < 11.39)) {
                    turnToGoal2.init();
                    return turnToGoal2;
                } else {
                    return this;
                }

            }
        },
        waitThreeSecondsForHumanPlayer {
            @Override
            public void init() {
                timer.reset();
                timer.start();
            }

            @Override
            public void execute() {
                Robot.intake.run();
            }

            @Override
            public AutonState nextState() {
                if (timer.get() < 3) {
                    return this;
                } else {
                    turnToGoal3.init();
                    return turnToGoal3;
                }

            }
        },
        turnToGoal3 {
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
                    driveToGoal.init();
                    return driveToGoal;
                }
            }
        },
        driveToGoal {
            @Override
            public void init() {
                Robot.drivetrain.stop();
                Robot.intake.stop();
                Robot.hopper.stop();
                Robot.shooter.reset();
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(11.5, 0.4, 60);
                Robot.drivetrain.driveStraightLeft(11.5, 0.4, 60); // untested coords
            }

            @Override
            public AutonState nextState() {
                if (!(Robot.drivetrain.getRightPosition() < 11.39) && !(Robot.drivetrain.getLeftPosition() < 11.39)) {
                    realignToGoal.init();
                    return realignToGoal;
                } else {
                    return this;
                }
            }
        },
        realignToGoal {
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
                    shooterUpToSpeed3.init();
                    return shooterUpToSpeed3;
                }
            }
        },
        shooterUpToSpeed3 {
            double shooterSpeed;

            @Override
            public void init() {
                Robot.drivetrain.stop();
                shooterSpeed = Robot.shooter.calcSpeed();
            }

            @Override
            public void execute() {
                System.out.println("shooting" + Robot.shooter.getPosition());
                Robot.shooter.shoot(shooterSpeed);
            }

            @Override
            public AutonState nextState() {
                if (Robot.shooter.getPosition() < 100) {
                    return this;
                } else {
                    feedBallsFourAndFive.init();
                    return feedBallsFourAndFive;
                }
            }
        },
        feedBallsFourAndFive {
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
                intakeUp.init();
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
        return "FiveBall";
    }
}

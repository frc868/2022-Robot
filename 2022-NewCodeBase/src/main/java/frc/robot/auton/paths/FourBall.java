package frc.robot.auton.paths;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.auton.AutonMap;
import frc.robot.auton.AutonPath;
import frc.robot.RobotMap;

public class FourBall extends AutonPath {

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
                    feedFirstAndSecondBalls.init();
                    return feedFirstAndSecondBalls;
                }
            }
        },
        feedFirstAndSecondBalls {
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
                return turnToRoughlyThirdBall;
            }
        },
        turnToRoughlyThirdBall {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraightRight(2, 0.4, 60);
                Robot.drivetrain.driveStraightLeft(-2, 0.4, 60);
            }

            @Override
            public AutonState nextState() {
                if ((Robot.drivetrain.getRightPosition() < 1.5) && (Robot.drivetrain.getLeftPosition() > -1.5)) {
                    return this;
                } else {
                    Robot.drivetrain.stop();
                    return turnToAstraThirdBall;
                }
            }
        },
        turnToAstraThirdBall {
            @Override
            public void init() {
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
                    Robot.drivetrain.reset();
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
                    return turnToRoughlyFourthBall;
                }
            }
        },
        turnToRoughlyFourthBall {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                // TODO: use navx
            }

            @Override
            public AutonState nextState() {
                return turnToAstraFourthBall;
            }
        },
        turnToAstraFourthBall {
            @Override
            public void init() {
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
                    Robot.drivetrain.reset();
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
                Robot.drivetrain.driveStraightRight(11, 0.4, 60);
                Robot.drivetrain.driveStraightLeft(11, 0.4, 60);
                Robot.intake.run();
                Robot.hopper.run();
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
                    return turnToRoughlyGoal;
                }
            }
        },
        turnToRoughlyGoal {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                // TODO: use navx
            }

            @Override
            public AutonState nextState() {
                return turnToGoal2;
            }
        },
        turnToGoal2 {
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
                    return driveToGoal2;
                }
            }
        },
        driveToGoal2 {
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
                    shooterUpToSpeed2.init();
                    return shooterUpToSpeed2;
                }
            }
        },
        shooterUpToSpeed2 {
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
                    feedThirdandFourthBalls.init();
                    return feedThirdandFourthBalls;
                }
            }
        },
        feedThirdandFourthBalls {
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
                Robot.shooter.stop();
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
        return "FourBall";
    }
}

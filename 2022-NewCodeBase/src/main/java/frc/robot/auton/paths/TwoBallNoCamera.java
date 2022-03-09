package frc.robot.auton.paths;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.auton.AutonMap;
import frc.robot.auton.AutonPath;

public class TwoBallNoCamera extends AutonPath {

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
                waitOneSecond.init();
                return waitOneSecond;
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
            }

            @Override
            public void execute() {
                Robot.drivetrain.driveStraight(AutonMap.TwoBallNoCamera.DISTANCE_TO_SECOND_BALL);
                Robot.intake.run();
                Robot.hopper.run();
            }

            @Override
            public AutonState nextState() {
                if (Robot.drivetrain.getRightPosition() < AutonMap.TwoBallNoCamera.DISTANCE_TO_SECOND_BALL) {
                    return this;
                } else {
                    Robot.drivetrain.stop();
                    Robot.hopper.stop();
                    Robot.intake.stop();
                    Robot.drivetrain.reset();
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
                }
                return shootBalls;
            }
        },
        shootBalls {
            @Override
            public void init() {
            }

            @Override
            public void execute() {
                Robot.shooter.shoot(Robot.shooter.calcSpeed());
            }

            @Override
            public AutonState nextState() {
                if (!Robot.shooter.speedOnTarget()) {
                    return this;
                } else {
                    return intakeUp;
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
        return "TwoBallNoCamera";
    }
}

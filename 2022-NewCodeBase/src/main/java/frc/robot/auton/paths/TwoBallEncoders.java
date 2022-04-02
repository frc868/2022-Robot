package frc.robot.auton.paths;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.auton.AutonPath;

public class TwoBallEncoders extends AutonPath {

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
                Robot.hopper.reset();
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
                }
                shooterUpToSpeed.init();
                return shooterUpToSpeed;
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
                    feedBalls.init();
                    return feedBalls;
                }
            }
        },
        feedBalls {
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
        return "TwoBallEncoders";
    }
}

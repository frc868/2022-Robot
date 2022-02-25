package frc.robot.auton.paths;


import frc.robot.Robot;
import frc.robot.auton.AutonMap;
import frc.robot.auton.AutonPath;

public class FirstPath extends AutonPath{
    //Drive forward 40.44 in
    //Shoot balls
    //Turn
    private State currentState = State.toFirstBall;
    private enum State{
        setIntakeDown{
            @Override
            public void run(){
                Robot.intake.toggle();
            }
            public State nextState(){
                return toFirstBall;
            }
        },
        toFirstBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(AutonMap.FirstPath.FIRST_TARGET_DISTANCE, 0.5, 30);
                Robot.intake.run();
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getRightPosition() < AutonMap.FirstPath.FIRST_TARGET_DISTANCE){
                    return this;
                }
                Robot.drivetrain.reset();
                return turnToGoal;
            }
        },
        turnToGoal{
            @Override
            public void run(){
                Robot.drivetrain.turnToLimelight();
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.atTarget() != true){
                    return this;
                }
                return shootBalls;
            }
        },
        shootBalls{
            @Override
            public void run(){
                Robot.shooter.shootLogic(AutonMap.FirstPath.FIRST_RPM);
            }
            @Override
            public State nextState(){
                if(Robot.hopper.ballsInHopper() != 0){
                    return this;
                }
                return turnToSecondBall;
            }
        },
        turnToSecondBall{
            @Override
            public void run(){
                //Robot.drivetrain.turnToClosestBall();
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.atTarget() != true){
                    return this;
                }
                Robot.drivetrain.reset();
                return toSecondBall;
            }
        },
        toSecondBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(AutonMap.FirstPath.SECOND_TARGET_DISTANCE, 0.5, 50);
                Robot.intake.run();
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getRightPosition() < AutonMap.FirstPath.SECOND_TARGET_DISTANCE){
                    return this;
                }
                return toSecondShootPosition;
            }
        },
        toSecondShootPosition{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(AutonMap.FirstPath.RETURN_SECOND_TARGET_DISTANCE, 0.5, 50);
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getRightPosition() > AutonMap.FirstPath.RETURN_SECOND_TARGET_DISTANCE){
                    return this;
                }
                return turnToGoalSecond;
            }
        },
        turnToGoalSecond{
            @Override
            public void run(){
                Robot.drivetrain.turnToLimelight();
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.atTarget() != true){
                    return this;
                }
                return shootBall;
            }
        },
        shootBall{
            @Override
            public void run(){
                Robot.shooter.shootLogic(AutonMap.FirstPath.FIRST_RPM);
            }
            @Override
            public State nextState(){
                if(Robot.hopper.ballsInHopper() != 0){
                    return this;
                }
                return Done;
            }
        },
        Done{
            @Override
            public void run(){
                Robot.drivetrain.setSpeed(0, 0);
            }
            @Override
            public State nextState(){
                return this;
            }

        };
        public abstract void run();
        public abstract State nextState();
    }
    @Override
    public void run(){
        this.currentState.run();
        this.currentState = this.currentState.nextState();
    }
    @Override
    public void reset(){
        this.currentState = State.toFirstBall;
    }
    @Override
    public String toString(){
        return "FirstPath";
    }
}

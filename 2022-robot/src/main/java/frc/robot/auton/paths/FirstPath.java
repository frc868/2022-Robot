package frc.robot.auton.paths;


import frc.robot.Robot;
import frc.robot.auton.AutonPath;

public class FirstPath extends AutonPath{
    //Drive forward 40.44 in
    //Shoot balls
    //Turn
    private State currentState = State.toFirstBall;
    private enum State{
        toFirstBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(30, 0.5, 30);
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getRightPosition() < 30){
                    return this;
                }
                Robot.drivetrain.reset();
                return turnToSecondBall;
            }
        },
        turnToSecondBall{
            @Override 
            public void run(){
                Robot.drivetrain.turnToAngle(37.5);
            }
            public State nextState(){
                if(Robot.gyro.distanceTo(37.5) > 3){
                    return this;
                }
                Robot.gyro.reset();
                return toSecondBall;
            }

        },
        toSecondBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(50, 0.5, 50);
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getRightPosition() < 50){
                    return this;
                }
                Robot.drivetrain.reset();
                return toFirstBallPosition;
            }
        },
        toFirstBallPosition{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(-50, 0.5, 50);
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getRightPosition() < 50){
                    return this;
                }
                Robot.drivetrain.reset();
                return turnToGoal;
            }


        },
        turnToGoal{
            @Override
            public void run(){
                Robot.drivetrain.turnToAngle(-37.5);
            }
            @Override
            public State nextState(){
                if(Robot.gyro.distanceTo(-37.5) > 0){
                    return this;
                }
                Robot.gyro.reset();
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

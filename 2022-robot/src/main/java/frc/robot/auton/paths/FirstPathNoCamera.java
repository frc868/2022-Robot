package frc.robot.auton.paths;


import frc.robot.Robot;
import frc.robot.auton.AutonMap;
import frc.robot.auton.AutonPath;

import java.util.Timer;
//6 inch wheel diameter
//54 inches drive
//-24.6 degree turn
//26.6 degree turn
//160.1 inches drive
//-160.1 inches drive
//-24.6 degree turn


public class FirstPathNoCamera extends AutonPath{
    private State currentState = State.toFirstBall;
    public static Timer time = new Timer();
    private enum State{
        setIntakeDown{
            @Override
            public void run(){
                
                
            }
            public State nextState(){
                return toFirstBall;
            }
        },

        toFirstBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(-40, 0.45, 30); 
                Robot.intake.run();
            }
            @Override
            public State nextState(){
               if(Robot.drivetrain.getRightPosition() > -37.5){
                    return this;
                }
                Robot.drivetrain.stop();
                Robot.hopper.stop();
                Robot.intake.stop();
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
                if(!Robot.drivetrain.atTarget()){
                    return this;
                }
                Robot.drivetrain.stop();
                return shootBalls;
            }
        },

        shootBalls{
            @Override
            public void run(){
                Robot.shooter.shoot(Robot.shooter.calcSpeed());
            }
            @Override
            public State nextState(){
                if(!Robot.shooter.onTarget()){
                    return this;
                }
                Robot.hopper.setForward();
                Robot.hopper.reset();
                return shoot;
            }
        },

        shoot{
            @Override
            public void run(){
                Robot.hopper.run();
            }
            @Override
            public State nextState(){
                if(Math.abs(Robot.hopper.getDistance()) < 300){
                    return this;
                }
                Robot.hopper.stop();
                return turnBack;
            }
        },  
        
        turnBack{
            @Override
            public void run(){
                Robot.drivetrain.turnToZeroLeftSide(0.25, 30);
                Robot.drivetrain.turntoZeroRightSide(0.25, 30);
            }
            @Override
            public State nextState(){
                if(Math.abs(Robot.drivetrain.getRightPosition()) > 1){
                    return this;
                }
                return toSecondBall;
            }
        },
        toSecondBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(-113, 0.45, 30); 
                Robot.intake.run();
            }
            @Override
            public State nextState(){
               if(Robot.drivetrain.getRightPosition() > -111){
                    return this;
                }
                Robot.drivetrain.stop();
                Robot.hopper.stop();
                Robot.intake.stop();
                Robot.drivetrain.reset();
                return toFirstBallPosition;
            }
        },
        toFirstBallPosition{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(113, 0.45, 30); 
                Robot.intake.run();
            }
            @Override
            public State nextState(){
               if(Robot.drivetrain.getRightPosition() < 111){
                    return this;
                }
                Robot.drivetrain.stop();
                Robot.hopper.stop();
                Robot.intake.stop();
                Robot.drivetrain.reset();
                return turnToGoal2;
            }
        },

        turnToGoal2{
            @Override
            public void run(){
               Robot.drivetrain.turnToLimelight();
            }
            @Override
            public State nextState(){
                if(!Robot.drivetrain.atTarget()){
                    return this;
                }
                Robot.drivetrain.stop();
                return shootBalls2;
            }
        },

        shootBalls2{
            @Override
            public void run(){
                Robot.shooter.shoot(Robot.shooter.calcSpeed());
            }
            @Override
            public State nextState(){
                if(!Robot.shooter.onTarget()){
                    return this;
                }
                Robot.hopper.setForward();
                Robot.hopper.reset();
                return shoot2;
            }
        },

        shoot2{
            @Override
            public void run(){
                Robot.hopper.run();
            }
            @Override
            public State nextState(){
                if(Math.abs(Robot.hopper.getDistance()) < 300){
                    return this;
                }
                Robot.hopper.stop();
                return Done;
            }
        },  

        Done{
            @Override
            public void run(){
                Robot.drivetrain.setSpeed(0, 0);
                Robot.shooter.setSpeed(0);
                Robot.hopper.stop();
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
        return "FirstPathNoCamera";
    }
}
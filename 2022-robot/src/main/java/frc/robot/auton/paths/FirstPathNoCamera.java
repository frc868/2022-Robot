package frc.robot.auton.paths;


import frc.robot.Robot;
import frc.robot.auton.AutonMap;
import frc.robot.auton.AutonPath;

import java.sql.Driver;
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
                Robot.intake.setReverse();
                
            }
            public State nextState(){
                return toFirstBall;
            }
        },

        toFirstBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(-70, 0.5, 30); 
                Robot.intake.run();
            }
            @Override
            public State nextState(){
               if(Robot.drivetrain.getRightPosition() > -70){
                    return this;
                }
                Robot.drivetrain.reset();
                return turnToGoal;
            }
        },

        turnToGoal{
            @Override
            public void run(){
               Robot.drivetrain.turn(6.5, 0.5, 30);
               System.out.println(Robot.drivetrain.getLeftPosition());
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getLeftPosition() < 6){
                    return this;
                }
                Robot.drivetrain.stop();
                Robot.drivetrain.reset();
                return hopperOpen;
            }
        },

        hopperOpen{
            @Override
            public void run(){
                Robot.hopper.setForward();
            }
            @Override
            public State nextState(){
                return shootBalls;
            }
        },

        shootBalls{
            @Override
            public void run(){
                Robot.intake.stop();
                Robot.shooter.shoot(1500);
            }
            @Override
            public State nextState(){
                if(!Robot.shooter.onTarget()){
                    return this;
                }
                Robot.hopper.reset();
                return shoot;
            }
        },

        shoot{
            @Override
            public void run(){
                Robot.shooter.shoot(1500);
                Robot.hopper.run();
            }
            @Override
            public State nextState(){
                if(Math.abs(Robot.hopper.getDistance()) < 400){
                    return this;
                }
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

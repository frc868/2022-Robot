package frc.robot.auton.paths;


import frc.robot.Robot;
import frc.robot.RobotMap.Hopper;
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
                Robot.hopper.run();
            }
            @Override
            public State nextState(){
               if(Robot.drivetrain.getRightPosition() > -70){
                    return this;
                }
                Robot.intake.stop();
                Robot.hopper.stop();
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
                Robot.drivetrain.reset();
                return shootToSpeedFirst;
            }
        },

        shootToSpeedFirst{
            @Override
            public void run(){
                Robot.shooter.shoot(Robot.shooter.calcSpeed());
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
                Robot.hopper.setReverse();
                Robot.hopper.run();
            }
            @Override
            public State nextState(){
                if(Robot.shooter.onTarget()){
                    return this;
                }
                Robot.hopper.stop();
                Robot.hopper.setForward();
                return shootToSpeedSecond;
            }
        },    

        shootToSpeedSecond{
            @Override
            public void run(){

            }
            @Override
            public State nextState(){
                if(!Robot.shooter.onTarget()){
                    return this;
                }
                Robot.hopper.setReverse();
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
                if(Robot.shooter.onTarget()){
                    return this;
                }
                Robot.hopper.setForward();
                return turnBack;
            }
        },   

        turnBack{
            @Override
            public void run(){
                Robot.drivetrain.turn(0, 0.5, 30);
            }
            @Override
            public State nextState(){
                if(Math.abs(Robot.drivetrain.getRightPosition()) > 1){
                    return this;
                }
                Robot.drivetrain.reset();
                return Done;
            }
        },

        driveToSecondBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(-200, 0.5, 30);
                Robot.intake.run();
                Robot.hopper.run();
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getRightPosition() > -200){
                    return this;
                }
                Robot.intake.stop();
                Robot.hopper.stop();
                return driveToShoot;
            }
        },

        driveToShoot{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(0, 0.5, 30);
            }
            @Override
            public State nextState(){
                if(Robot.drivetrain.getRightPosition() < 0){
                    return this;
                }
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
                Robot.drivetrain.reset();
                return shootToSpeedFirst2;
            }
        },

        shootToSpeedFirst2{
            @Override
            public void run(){
                Robot.shooter.shoot(Robot.shooter.calcSpeed());
            }
            @Override
            public State nextState(){
                if(!Robot.shooter.onTarget()){
                    return this;
                }
                Robot.hopper.setReverse();
                Robot.hopper.reset();
                return shoot3;
            }
        },

        shoot3{
            @Override
            public void run(){
                Robot.hopper.run();
            }
            @Override
            public State nextState(){
                if(Robot.shooter.onTarget()){
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

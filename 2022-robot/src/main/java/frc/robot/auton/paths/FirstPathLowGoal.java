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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class FirstPathLowGoal extends AutonPath{
    private State currentState = State.setIntakeDown;
    private static double count = 0;
    private enum State{
        setIntakeDown{
            @Override
            public void run(){
                count += 1;
                
            }
            public String current(){
                return "setIntakeDown";
            }
            public State nextState(){
                if(count < 1000){
                    return this;
                }
                count = 0;
                return toFirstBall;
            }
        },

        toFirstBall{
            @Override
            public void run(){
                Robot.drivetrain.driveStraight(-95, 0.25, 60); 
                System.out.println(Robot.drivetrain.getRightPosition());
                Robot.intake.run();
                Robot.hopper.run();
            }
            public String current(){
                return "toFirstBall";
            }
            @Override
            public State nextState(){
               if(Robot.drivetrain.getRightPosition() > -90){
                    return this;
                }
                Robot.drivetrain.stop();
                Robot.hopper.stop();
                Robot.intake.stop();
                Robot.drivetrain.reset();
                return intakeUp;
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
                return Done;
            }
        },

        toShootPosition{
            @Override
            public void run(){
                Robot.drivetrain.goToTarget();
            }
            @Override
            public State nextState(){
                if(!Robot.drivetrain.atTargetDrive()){
                    return this;
                }
                Robot.drivetrain.stop();
                return turnToGoal;
            }
        },
        shootBalls{
            @Override
            public void run(){
                Robot.shooter.shoot(2250);
                
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
                if(Math.abs(Robot.hopper.getDistance()) < 500){
                    return this;
                }
                Robot.hopper.stop();
                Robot.hopper.reset();
                return Done;
            }
        },  

        intakeUp{
            @Override
            public void run(){
                Robot.intake.setForward();
            }
            @Override
            public State nextState(){
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
        Robot.drivetrain.stop();
        Robot.shooter.stop();
        Robot.intake.stop();
        Robot.hopper.stop();
        Robot.shooter.reset();
        this.currentState = State.toFirstBall;
    }
    @Override
    public String toString(){
        return "FirstPathNoCamera";
    }
}
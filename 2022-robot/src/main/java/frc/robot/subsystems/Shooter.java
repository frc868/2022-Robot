package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter {
    private CANSparkMax s_primary, s_secondary;
    private PIDController pid;
    private double kP, kI, kD;
    private double vI = 0;
    private double vF = 0;
    public static Shooter instance;

    //theory
    private boolean loweredSpeed = false;
    private boolean gainedSpeed = false;

    private Shooter(){
        s_primary = new CANSparkMax(RobotMap.Shooter.S_PRIMARY, MotorType.kBrushless);
        s_secondary = new CANSparkMax(RobotMap.Shooter.S_SECONDARY, MotorType.kBrushless);

        s_primary.setInverted(RobotMap.Shooter.IS_INVERTED);
        s_secondary.follow(s_primary, true);


        if(Robot.isCompBot){
            kP = RobotMap.PID_CONSTANTS.COMP_BOT.SHOOTER_CONSTANTS.KP;
            kI = RobotMap.PID_CONSTANTS.COMP_BOT.SHOOTER_CONSTANTS.KI;
            kD = RobotMap.PID_CONSTANTS.COMP_BOT.SHOOTER_CONSTANTS.KD;
        }
        else{
            kP = RobotMap.PID_CONSTANTS.PRAC_BOT.SHOOTER_CONSTANTS.KP;
            kI = RobotMap.PID_CONSTANTS.PRAC_BOT.SHOOTER_CONSTANTS.KI;
            kD = RobotMap.PID_CONSTANTS.PRAC_BOT.SHOOTER_CONSTANTS.KD;
        }
        pid = new PIDController(kP, kI, kD);
    }

    public static Shooter getInstance(){
        if(instance == null){
            instance = new Shooter();
        }
        return instance;
    }

    public double getRPM(){
        return s_primary.getEncoder().getVelocity();
    }

    public void setSpeed(double speed){
        s_primary.set(speed);
    }

    public void stop(){
        s_primary.set(0);
    }

    public void shoot(double targetRPM){
        double calcSpeed = pid.calculate(getRPM(), targetRPM);
        setSpeed(calcSpeed);
    }
    
    public boolean onTarget(){
        pid.setTolerance(30);
        return pid.atSetpoint();
    }

    //theory code
    public void ballIsShoot(){
        vI = vF;
        vF = s_primary.getEncoder().getVelocity();
        double acceleration = (vF - vI) / 0.02;
        if(acceleration < -5){
            loweredSpeed = true;
        }
        if(acceleration > 5){
            gainedSpeed = true;
        }
        if(loweredSpeed && gainedSpeed){
            Robot.hopper.subBall();
            loweredSpeed = false;
            gainedSpeed = false;
        }
    }

    public void shootLogic(double targetRPM){
        if(Robot.hopper.ballCount > 0){
            shoot(targetRPM);
            if(onTarget()){
                Robot.hopper.setBack();
                Robot.hopper.run();
            }
            else{
                Robot.hopper.setForward();
                Robot.hopper.run();
            }
        }
    }
}

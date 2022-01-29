package frc.robot.subsystems;

import java.util.Timer;

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

    private Shooter(){
        s_primary = new CANSparkMax(RobotMap.Shooter.S_PRIMARY, MotorType.kBrushless);
        s_secondary = new CANSparkMax(RobotMap.Shooter.S_SECONDARY, MotorType.kBrushless);

        s_primary.setInverted(RobotMap.Shooter.IS_INVERTED);
        s_secondary.follow(s_primary, true);


        kP = 0.0008;
        kI = 0.0023;
        kD = 0.000045;
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
            Robot.
        }
    }
}

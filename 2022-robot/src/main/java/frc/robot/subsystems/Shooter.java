package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.RobotMap;

public class Shooter {
    private CANSparkMax s_primary, s_secondary;
    private PIDController pid;
    private double kP, kI, kD;
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
}

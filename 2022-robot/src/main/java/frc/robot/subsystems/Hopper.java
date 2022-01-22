package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.RobotMap;

public class Hopper {
    private CANSparkMax hopper;
    public static Hopper instance;

    private Hopper(){
        hopper = new CANSparkMax(RobotMap.Hopper.HOPPER, MotorType.kBrushless); //TODO:Pretty sure its a 550 but need to check
        hopper.setInverted(RobotMap.Hopper.IS_INVERTED);
    }

    public static Hopper getInstance(){
        if(instance == null){
            instance = new Hopper();
        }
        return instance;
    }

    public void run(){
        hopper.set(0.2);
    }

    public void setSpeed(double speed){
        hopper.set(speed);
    }

    public void ballsInHopper(){ //TODO: Is void now but should return the number of balls in hopper

    }
}

package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;
import frc.robot.RobotMap;


public class Gyro {
    private AHRS gyro;
    public static Gyro instance;

    private Gyro(){
        gyro = new AHRS(RobotMap.Gyro.GYRO);
    }

    public static Gyro getInstance(){
        if(instance == null){
            instance = new Gyro();
        }
        return instance;
    }

    public double getAngle(){
        return gyro.getAngle();
    }

    public double getYaw(){
        return gyro.getYaw();
    }

    public double getPitch(){
        return gyro.getPitch();
    }
    
    public double getRoll(){
        return gyro.getRoll();
    }

    public void reset(){
        gyro.reset();
    }

    public void calibrate(){
        gyro.calibrate();
    }
    
    public double distanceTo(double target){
        return Math.abs(target - getAngle());
    }
}
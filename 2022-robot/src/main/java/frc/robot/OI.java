package frc.robot;

import frc.robot.helpers.ControllerWrapper;

public class OI {
    public static ControllerWrapper driver = new ControllerWrapper(RobotMap.Controllers.DRIVER_PORT, true);
    public static OI instance;
    public static OI getInstance(){
        if(instance == null){
            instance = new OI();
        }
        return instance;
    }
    public static void updateOI(){
       // Robot.drivetrain.setLeftSpeed(driver.getLY());
        //obot.drivetrain.setRightSpeed(driver.getLY());
        Robot.drivetrain.arcadeDrive(0.5);
        //Robot.drivetrain.turnLeft(driver.getRX());
    }
}
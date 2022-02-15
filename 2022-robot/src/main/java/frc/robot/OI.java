package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.helpers.ControllerWrapper;

public class OI {
    public static ControllerWrapper driver = new ControllerWrapper(RobotMap.Controllers.DRIVER_PORT, true);
    public static ControllerWrapper operator = new ControllerWrapper(RobotMap.Controllers.OPERATOR_PORT, true);
    public static OI instance;
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }
    
    public static void updateOI() {
        Robot.drivetrain.arcadeDrive(0.5);
        driver.bX.whenPressed(() -> Robot.hopper.run());
        
    }

    public static void updateSmartDashboard() {
        SmartDashboard.putNumber("Number of balls", Robot.hopper.ballCount);
    }
}
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
        //Driver
        Robot.drivetrain.tankDrive(1);
        
        
        driver.bLB.whileHeld(() -> Robot.hopper.run());
        driver.bLB.whenReleased(() -> Robot.hopper.stop());
        
        driver.bY.whenPressed(() -> Robot.intake.setForward());
        driver.bB.whenPressed(() -> Robot.intake.setReverse());

        driver.bA.whenPressed(() -> Robot.hopper.setForward());
        driver.bX.whenPressed(() -> Robot.hopper.setReverse());

        driver.bRB.whileHeld(() -> Robot.intake.run());
        driver.bRB.whenReleased(() -> Robot.intake.stop());

        //Shooter logic
        driver.dN.whileHeld(() -> Robot.drivetrain.turnToLimelight());
        driver.dN.whenReleased(() -> Robot.drivetrain.stop());

        

        driver.bSTART.whileHeld(() -> Robot.shooter.shoot(3000));
        driver.bSTART.whenReleased(() -> Robot.shooter.setSpeed(0));
        
        
    }

    public static void updateSmartDashboard() {
        SmartDashboard.putNumber("Number of balls", Robot.hopper.ballCount);
        SmartDashboard.putNumber("rom", Robot.shooter.getRPM());
        SmartDashboard.putBoolean("onTarget", Robot.shooter.onTarget());
        SmartDashboard.putNumber("acc", Robot.shooter.acceleration());
        SmartDashboard.putNumber("distance", Robot.limelight.getDistance());
    }
}
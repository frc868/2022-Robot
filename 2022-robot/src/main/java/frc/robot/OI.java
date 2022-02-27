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
        
        driver.bLB.whenPressed(() -> Robot.intake.setForward());
        driver.bRB.whenPressed(() -> Robot.intake.setReverse());
        
       driver.bA.whileHeld(() -> Robot.intake.run());
       driver.bA.whenReleased(() -> Robot.intake.stop());

        driver.dN.whileHeld(() -> Robot.drivetrain.turnToLimelight());
        driver.dN.whenReleased(() -> Robot.drivetrain.stop());

        driver.dS.whileHeld(() -> Robot.drivetrain.goToTarget());
        driver.dS.whenReleased(() -> Robot.drivetrain.stop());

        //Operator
       
        operator.bLB.whileHeld(() -> Robot.hopper.run());
        operator.bLB.whenReleased(() -> Robot.hopper.stop());

        operator.bX.whenPressed(() -> Robot.hopper.setReverse());
        operator.bB.whenPressed(() -> Robot.hopper.setForward());

        operator.bRB.whileHeld(() -> Robot.shooter.shoot(2000));
        operator.bRB.whenReleased(() -> Robot.shooter.stop());

        operator.bY.whenPressed(() -> Robot.climber.setForward());
        operator.bA.whenPressed(() -> Robot.climber.setReverse());

        Robot.climber.setSpeed(1 * operator.getLY());

        operator.dN.whenPressed(() -> Robot.climber.setTrue());
        operator.dS.whenPressed(() -> Robot.climber.setFalse());

        
        
        
        
    }

    public static void updateSmartDashboard() {
        SmartDashboard.putNumber("Number of balls", Robot.hopper.ballCount);
        SmartDashboard.putNumber("rom", Robot.shooter.getRPM());
        SmartDashboard.putBoolean("onTarget", Robot.shooter.onTarget());
        SmartDashboard.putNumber("acc", Robot.shooter.acceleration());
        SmartDashboard.putNumber("distance", Robot.limelight.getDistance());
    }
}
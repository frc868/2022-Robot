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

        Robot.drivetrain.tankDrive(-.75);
        
        driver.bA.whenPressed(() -> Robot.intake.setReverse());
        driver.bY.whenPressed(() -> Robot.intake.setForward());
        
       driver.bRB.whileHeld(() -> Robot.intake.run());
       driver.bRB.whenReleased(() -> Robot.intake.stop());

        driver.dS.whileHeld(() -> Robot.drivetrain.goToTarget());
        driver.dS.whenReleased(() -> Robot.drivetrain.stop());

        //Operator
       
        operator.bRB.whileHeld(() -> Robot.hopper.run());
        operator.bRB.whenReleased(() -> Robot.hopper.stop());

        operator.bX.whenPressed(() -> Robot.hopper.setReverse());
        operator.bB.whenPressed(() -> Robot.hopper.setForward());

        operator.bLB.whileHeld(() -> Robot.shooter.shoot(Robot.shooter.calcSpeed()));
        operator.bLB.whenReleased(() -> Robot.shooter.stop());

        operator.dN.whileHeld(() -> Robot.drivetrain.turnToLimelight());
        operator.dN.whenReleased(() -> Robot.drivetrain.stop());

       

        Robot.climber.setSpeed(1 * operator.getLY());

        operator.bY.whenPressed(() -> Robot.climber.setTrue());
        operator.bA.whenPressed(() -> Robot.climber.setFalse());

        
        
        
        
    }

    public static void updateSmartDashboard() {
     SmartDashboard.putNumber("rpm", Robot.shooter.getRPM());
     SmartDashboard.putBoolean("atTarget", Robot.shooter.onTarget());
     SmartDashboard.putBoolean("gatekeeper", Robot.hopper.getGateKeeper());
    }
}
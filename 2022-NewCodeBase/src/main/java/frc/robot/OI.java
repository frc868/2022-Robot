package frc.robot;

import frc.robot.helpers.ControllerWrapper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

    // USB Ports
    public static ControllerWrapper driver = new ControllerWrapper(RobotMap.Controllers.DRIVER_PORT, true);
    public static ControllerWrapper operator = new ControllerWrapper(RobotMap.Controllers.OPERATOR_PORT, true);

    // Singleton Instance
    public static OI instance;

    public static double speed = 2300;
    public static boolean shooterOn = false;

    /**
     * Creates a Singleton for class OI. Use this whenever OI and it's methods need
     * to be accessed
     * 
     * @return a static reference to class OI
     */
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    /**
     * Code to be called during TeleOp periodic
     */
    public static void updateOI() {
        Robot.drivetrain.tankDrive(1);
        // Driver

        driver.bA.whenPressed(() -> Robot.intake.setUp());
        driver.bY.whenPressed(() -> Robot.intake.setDown());

        // driver.dS.whileHeld(() -> Robot.drivetrain.goToTarget());
        // driver.dS.whenReleased(() -> Robot.drivetrain.stop());

        // Operator

        operator.bRB.whileHeld(() -> {
            Robot.hopper.run();
            Robot.intake.run();
        });
        operator.bRB.whenReleased(() -> {
            Robot.hopper.stop();
            Robot.intake.stop();
        });

        operator.bA.whileHeld(() -> {
            Robot.hopper.reverse();
            Robot.intake.reverse();
        });
        operator.bA.whenReleased(() -> {
            Robot.hopper.stop();
            Robot.intake.stop();
        });

        operator.bX.whenPressed(() -> Robot.hopper.gatekeepersOut());
        operator.bB.whenPressed(() -> Robot.hopper.gatekeepersIn());

        operator.bLB.whenPressed(() -> {
            shooterOn = !shooterOn;
        });

        operator.dN.whileHeld(() -> Robot.drivetrain.turnToLimelight());
        operator.dN.whenReleased(() -> Robot.drivetrain.stop());

        Robot.climber.setSpeed(1 * operator.getLY());

        // operator.bY.whenPressed(() -> Robot.climber.lockExtend());
        // operator.bA.whenPressed(() -> Robot.climber.lockRetract());

        operator.dE.whenReleased(() -> speed += 50);
        operator.dW.whenReleased(() -> speed -= 50);

        if (shooterOn) {
            Robot.shooter.shoot(speed);
        } else {
            Robot.shooter.stop();
        }

    }

    /**
     * Code to display and get values on SmartDashboard
     */
    public static void updateSmartDashboard() {
        SmartDashboard.putNumber("rpm", Robot.shooter.getVelocity());
        SmartDashboard.putBoolean("atTarget", Robot.shooter.speedOnTarget());
        // SmartDashboard.putBoolean("gatekeeper", Robot.hopper.getGateKeeper());
        SmartDashboard.putNumber("distance", Robot.limelight.getDistance());
        SmartDashboard.putNumber("speed", speed);
        SmartDashboard.putBoolean("shooterOn", shooterOn);
        SmartDashboard.putNumber("limelightDistance", Robot.limelight.getDistance());
    }
}
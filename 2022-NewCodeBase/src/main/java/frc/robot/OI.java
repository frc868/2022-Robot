package frc.robot;

import frc.robot.helpers.ControllerWrapper;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

    // USB Ports
    public static ControllerWrapper driver = new ControllerWrapper(RobotMap.Controllers.DRIVER_PORT, true);
    public static ControllerWrapper operator = new ControllerWrapper(RobotMap.Controllers.OPERATOR_PORT, true);

    // Singleton Instance
    public static OI instance;

    public static double shooterSpeed = RobotMap.HIGH_GOAL_MODE ? RobotMap.Subsystems.Shooter.HIGH_GOAL_RPM
            : RobotMap.Subsystems.Shooter.LOW_GOAL_RPM;
    public static boolean shooterOn = false;

    public static double driveDistance = RobotMap.HIGH_GOAL_MODE ? RobotMap.Subsystems.Limelight.HIGH_GOAL_SHOT_DISTANCE
            : RobotMap.Subsystems.Limelight.LOW_GOAL_SHOT_DISTANCE;

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
        Robot.drivetrain.tankDrive(0.8);
        // Driver

        driver.bA.whenPressed(() -> Robot.intake.setDown());
        driver.bY.whenPressed(() -> Robot.intake.setUp());

        driver.dS.whileHeld(() -> Robot.drivetrain.driveToLimelight(driveDistance));
        driver.dS.whenReleased(() -> Robot.drivetrain.stop());

        driver.bB.whenPressed(() -> Robot.climber.lockExtend());
        driver.bX.whenPressed(() -> Robot.climber.lockRetract());

        driver.bSTART.whenPressed(() -> Robot.climber.setForward());
        driver.bMENU.whenPressed(() -> Robot.climber.setReverse());

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

        operator.bSTART.whenPressed(() -> shooterSpeed = shooterSpeed == RobotMap.Subsystems.Shooter.HIGH_GOAL_RPM
                ? RobotMap.Subsystems.Shooter.LOW_GOAL_RPM
                : RobotMap.Subsystems.Shooter.HIGH_GOAL_RPM);

        operator.bX.whenPressed(() -> Robot.hopper.gatekeepersOut());
        operator.bB.whenPressed(() -> Robot.hopper.gatekeepersIn());

        operator.bLB.whenPressed(() -> {
            shooterOn = !shooterOn;
        });

        operator.dN.whileHeld(() -> Robot.drivetrain.turnToLimelight());
        operator.dN.whenReleased(() -> Robot.drivetrain.stop());

        operator.dS.whileHeld(() -> Robot.drivetrain.turnToAstra());
        operator.dS.whenReleased(() -> Robot.drivetrain.stop());

        Robot.climber.setSpeed(1 * operator.getLY());

        // operator.bY.whenPressed(() -> Robot.climber.lockExtend());
        // operator.bA.whenPressed(() -> Robot.climber.lockRetract());

        operator.dE.whenReleased(() -> shooterSpeed += 10);
        operator.dW.whenReleased(() -> shooterSpeed -= 10);

        shooterSpeed = Robot.shooter.calcSpeed();
        if (shooterOn) {
            Robot.shooter.shoot(shooterSpeed);
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
        SmartDashboard.putBoolean("gatekeeper", Robot.hopper.gatekeepersStatus());
        SmartDashboard.putNumber("distance", Robot.limelight.getDistance());
        SmartDashboard.putNumber("shooterSpeed", shooterSpeed);
        SmartDashboard.putBoolean("shooterOn", shooterOn);
        SmartDashboard.putNumber("limelightDistance", Robot.limelight.getDistance());
        SmartDashboard.putNumber("pressure", Robot.pressureSensor.getPressure());
        SmartDashboard.putNumber("gyroAngle", Robot.gyro.getAngle());
        SmartDashboard.putNumber("gyropitch", Robot.gyro.getPitch());
        SmartDashboard.putNumber("gyroyaw", Robot.gyro.getYaw());
        SmartDashboard.putNumber("gyroroll", Robot.gyro.getRoll());
        SmartDashboard.putNumber("left", Robot.drivetrain.getLeftPosition());
        SmartDashboard.putNumber("right", Robot.drivetrain.getRightPosition());
        SmartDashboard.putBoolean("lock", Robot.climber.getLock());
        SmartDashboard.putNumber("shooter speed", shooterSpeed);
    }

    /**
     * Update SD to allow for PID tuning, this should only be called in testInit
     */
    public static void pidAdjustInit(PIDController pidController) {
        SmartDashboard.putNumber("kpAdjuster", pidController.getP());
        SmartDashboard.putNumber("kIAdjuster", pidController.getI());
        SmartDashboard.putNumber("kDAdjuster", pidController.getD());
    }

    /**
     * Update SD to allow for PID tuning, this should only be called in testPeriodic
     */
    public static void pidAdjust(PIDController pidController) {
        pidController.setP(SmartDashboard.getNumber("kpAdjuster", pidController.getP()));
        pidController.setI(SmartDashboard.getNumber("kIAdjuster", pidController.getI()));
        pidController.setD(SmartDashboard.getNumber("kDAdjuster", pidController.getD()));
    }

}
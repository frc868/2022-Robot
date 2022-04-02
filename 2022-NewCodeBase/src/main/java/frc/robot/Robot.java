// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auton.AutonChooser;
import frc.robot.subsystems.Astra;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Climber;
import frc.robot.sensors.PressureSensor;
import frc.robot.sensors.Gyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    public static Drivetrain drivetrain = Drivetrain.getInstance();
    public static Limelight limelight = Limelight.getInstance();
    public static Astra astra = Astra.getInstance();
    public static Shooter shooter = Shooter.getInstance();
    public static Hopper hopper = Hopper.getInstance();
    public static Intake intake = Intake.getInstance();
    public static Climber climber = Climber.getInstance();
    public static AutonChooser autonChooser = AutonChooser.getInstance();
    public static PressureSensor pressureSensor = PressureSensor.getInstance();
    public static Gyro gyro = Gyro.getInstance();

    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
     */
    @Override
    public void robotInit() {
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
        autonChooser.reset();
    }

    @Override
    public void autonomousPeriodic() {
        autonChooser.runSelectedPath();
        OI.updateSmartDashboard();
    }

    @Override
    public void teleopInit() {
        drivetrain.reset();
        gyro.resetAngle();
        Robot.gyro.resetAngle();
        OI.pidAdjustInit(Robot.drivetrain.getTurnToLimelightPID());
    }

    @Override
    public void teleopPeriodic() {
        OI.updateOI();
        OI.updateSmartDashboard();
        OI.pidAdjust(Robot.drivetrain.getTurnToLimelightPID());

    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
        // drivetrain.reset();
        drivetrain.reset();
        gyro.resetAngle();
        OI.pidAdjustInit(Robot.drivetrain.getTurnToLimelightPID());
    }

    @Override
    public void testPeriodic() {
        OI.updateOI();
        // OI.updateOI();
        OI.updateSmartDashboard();
    }

    @Override
    public void simulationInit() {
    }

    @Override
    public void simulationPeriodic() {
    }
}

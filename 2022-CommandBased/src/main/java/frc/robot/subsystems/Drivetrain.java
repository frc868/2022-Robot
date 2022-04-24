// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.logging.LogGroup;
import frc.robot.logging.LogProfileBuilder;
import frc.robot.logging.LogValue;
import frc.robot.logging.Logger;

/**
 * Drivetrain subsystem, includes all of the motors and the methods with which
 * to drive the bot.
 */
public class Drivetrain extends SubsystemBase {
    private CANSparkMax l_primary = new CANSparkMax(Constants.Drivetrain.CANIDs.L_PRIMARY,
            MotorType.kBrushless);
    private CANSparkMax l_secondary = new CANSparkMax(Constants.Drivetrain.CANIDs.L_SECONDARY,
            MotorType.kBrushless);
    private CANSparkMax r_primary = new CANSparkMax(Constants.Drivetrain.CANIDs.R_PRIMARY,
            MotorType.kBrushless);
    private CANSparkMax r_secondary = new CANSparkMax(Constants.Drivetrain.CANIDs.R_SECONDARY,
            MotorType.kBrushless);
    private MotorControllerGroup leftMotors = new MotorControllerGroup(l_primary, l_secondary);
    private MotorControllerGroup rightMotors = new MotorControllerGroup(r_primary, r_secondary);
    private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);
    private AHRS navx = new AHRS(SerialPort.Port.kMXP);
    private LogGroup logger = new LogGroup(
            new Logger<?>[] {
                    new Logger<CANSparkMax>(l_primary, "Drivetrain", "Left Primary Motor",
                            LogProfileBuilder.buildCANSparkMaxLogValues(l_primary)),
                    new Logger<CANSparkMax>(l_secondary, "Drivetrain", "Left Secondary Motor",
                            LogProfileBuilder.buildCANSparkMaxLogValues(l_secondary)),
                    new Logger<CANSparkMax>(r_primary, "Drivetrain", "Right Primary Motor",
                            LogProfileBuilder.buildCANSparkMaxLogValues(r_primary)),
                    new Logger<CANSparkMax>(r_secondary, "Drivetrain", "Right Secondary Motor",
                            LogProfileBuilder.buildCANSparkMaxLogValues(r_secondary)),
                    new Logger<AHRS>(navx, "Drivetrain", "NavX",
                            LogProfileBuilder.buildNavXLogValues(navx))
            });

    /**
     * Initializes the drivetrain.
     */
    public Drivetrain() {
        l_primary.getEncoder().setPositionConversionFactor(Constants.Drivetrain.ENCODER_DISTANCE_PER_PULSE);
        r_primary.getEncoder().setPositionConversionFactor(Constants.Drivetrain.ENCODER_DISTANCE_PER_PULSE);
        leftMotors.setInverted(Constants.Drivetrain.IS_LEFT_INVERTED);
        rightMotors.setInverted(Constants.Drivetrain.IS_RIGHT_INVERTED);
        drive.setMaxOutput(0.8);
    }

    /**
     * Runs every 20ms. In this method, all we do is run SmartDashboard/logging
     * related functions (do NOT run any code that should belong in a command here!)
     */
    @Override
    public void periodic() {
        logger.run();
    }

    /**
     * Runs the drivetrain in arcade mode.
     * 
     * @param speed the speed from -1 to 1 with which to drive the motors (usually
     *              taken from the L stick Y axis of a controller)
     * @param rot   the rotation from -1 to 1 with which to rotate the robot
     *              (usually taken from the R stick X axis of a controller)
     */
    public void arcadeDrive(double speed, double rot) {
        drive.arcadeDrive(speed, rot);
    }

    /**
     * Runs the drivetrain in tank mode.
     * 
     * @param leftSpeed  the speed from -1 to 1 with which to run the left motors
     *                   (usually taken from the L stick Y axis of a controller)
     * @param rightSpeed the speed from -1 to 1 with which to run the right motors
     *                   (usually taken from the R stick Y axis of a controller)
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        drive.tankDrive(leftSpeed, rightSpeed);
    }

    /**
     * Resets the drivetrain encoders by setting their positions to zero.
     */
    public void resetEncoders() {
        l_primary.getEncoder().setPosition(0);
        r_primary.getEncoder().setPosition(0);
    }

    /**
     * Gets the position of the left primary motor encoder.
     * 
     * @return the position of the left primary motor encoder
     */
    public double getLeftPosition() {
        return l_primary.getEncoder().getPosition();
    }

    /**
     * Gets the position of the right primary motor encoder.
     * 
     * @return the position of the right primary motor encoder
     */

    public double getRightPosition() {
        return r_primary.getEncoder().getPosition();
    }

    /**
     * Gets the average position of the left and right encoders.
     * 
     * @return the average position of the left and right encoders.
     */
    public double getPosition() {
        return (double) (l_primary.getEncoder().getPosition() + r_primary.getEncoder().getPosition()) / 2.0;
    }

    /**
     * Sets the max output of the drivetrain.
     * 
     * @param maxOutput the maximum output percentage (0 to 1) of the drivetrain
     */
    public void setMaxOutput(double maxOutput) {
        drive.setMaxOutput(maxOutput);
    }

    /**
     * Stops the drivetrain.
     */
    public void stop() {
        tankDrive(0, 0);
    }

    /**
     * Gets the current angle of the gyro.
     */
    public double getGyroAngle() {
        return navx.getAngle();
    }

    /**
     * Reset the navX angle.
     */
    public void resetGyro() {
        navx.reset();
    }
}

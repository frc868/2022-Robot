// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;

public class DriveStraight extends PIDCommand {
    private final Drivetrain drivetrain;

    /**
     * Create a new DriveStraight command.
     *
     * @param distance The distance to drive
     */
    public DriveStraight(double distance, Drivetrain drivetrain) {
        super(new PIDController(Constants.Drivetrain.DriveStraightPID.kP, Constants.Drivetrain.DriveStraightPID.kI,
                Constants.Drivetrain.DriveStraightPID.kD),
                drivetrain::getPosition, distance, d -> drivetrain.tankDrive(d, d),
                drivetrain);

        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        getController().setTolerance(0.01);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        // Get everything in a safe starting state.
        this.drivetrain.resetEncoders();
        super.initialize();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}
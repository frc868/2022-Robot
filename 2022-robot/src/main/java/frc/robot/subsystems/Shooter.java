/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.helpers.Helper;

/**
 * The shooter subsystem consists of the two-neo shooter mounted on the robot's
 * turret. It is controlled with REV's PID Controller on the SparkMAXes.
 *
 * @author dri
 */
public class Shooter {
    private static Shooter instance = null;

    private CANSparkMax primary;
    private CANSparkMax secondary;

    private SparkMaxPIDController pid;

    private double kP, kD, kFF, kI, kIa;

    private double setpoint;

    public Shooter() {
        primary = new CANSparkMax(RobotMap.Shooter.PRIMARY, MotorType.kBrushless);
        secondary = new CANSparkMax(RobotMap.Shooter.SECONDARY, MotorType.kBrushless);
        
        primary.restoreFactoryDefaults();
        secondary.restoreFactoryDefaults();

        primary.setInverted(RobotMap.Shooter.PRIMARY_IS_INVERTED);
        secondary.follow(primary, RobotMap.Shooter.SECONDARY_IS_OPPOSITE);
        
        pid = primary.getPIDController();
    }

    /**
     * Returns the instance of the Shooter class
     * @return instance of shooter
     */
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    /**
     * Sets the PID gains and setpoint for the PID controller.
     */
    public void init() {
        // kP = 0.3/1000;
        // kI = 0.00001/1000;
        // kD = 0.03/1000;
        // kFF = 0.190/1000;
        // kIa = 2;

        // kP = 0.3;
        // kI = 0.00001;
        // kD = 0.03;
        // kFF = 0.190;
        // kIa = 2;

        // Distance; Power
        // 17; 0.58
        // 20; 0.6
        // 25; 0.8

        pid.setP(SmartDashboard.getNumber("kP", kP));
        pid.setI(SmartDashboard.getNumber("kI", kI));
        pid.setD(SmartDashboard.getNumber("kD", kD));
        pid.setFF(SmartDashboard.getNumber("kFF", kFF));
        pid.setIMaxAccum(kIa, 0);

        if (this.kI == 0) {
            pid.setIAccum(0);
        }
        
        pid.setOutputRange(0, 1);
    }

    /**
     * sets the output of the PID loop to the setpoint
     */
    public void update(double rpm) {
        this.setpoint = rpm;
        
        pid.setP(SmartDashboard.getNumber("kP", kP)/1000);
        pid.setI(SmartDashboard.getNumber("kI", kI)/1000);
        pid.setD(SmartDashboard.getNumber("kD", kD)/1000);
        pid.setFF(SmartDashboard.getNumber("kFF", kFF)/1000);
        pid.setIMaxAccum(SmartDashboard.getNumber("kIa", kIa), 0);

        pid.setReference(setpoint, ControlType.kVelocity);
    }

    /**
     * runs the shooter at the current RPM
     */
    public void update() {
        this.update(this.setpoint);
    }

    /**
     * Checks whether the shooter is within a range of its target RPM.
     * @return is shooter at target
     */
    /*
    public boolean atTarget() {
        return Helper.tolerance(
            primary.getEncoder().getVelocity(),
            this.setpoint,
            0.01);
    }
    */

    /**
     * Manually sets the speed of the motors.
     * @param speed the speed from -1 to 1
     */
    public void setSpeed(double speed) {
        primary.set(speed);
    }

    /**
     * Retrieves the RPM of the shooter.
     */
    public double getRPM() {
        return primary.getEncoder().getVelocity();
    }

    /**
     * Stops the shooter.
     */
    public void stop() {
        primary.stopMotor();
        secondary.stopMotor();
    }

    /**
     * Shoots until all balls are cleared from the hopper.
     * Useful in autonomous.
     * TODO: this should have checking as to the hopper state, but that logic doesn't exist yet
     * @param rpm the RPM to run the shooter at
     * @author hrl
     */
    /*public void shootUntilClear(double rpm) {
        Robot.hopper.forward(this.atTarget());
        this.setpoint = rpm;
        this.update();
    }*/
}
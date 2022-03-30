package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Drivetrain {
    private CANSparkMax r_primary, r_secondary, l_primary, l_secondary;
    private PIDController turnToLimelightPID, driveToLimelightPID, turnToAstraPID, driveToAstraPID, leftSidePID,
            rightSidePID, driveStraightPID;
    public static Drivetrain instance;

    /**
     * Constructor of class Drivetrain. Used to instantiate the Drivetrain class.
     * Should only be used in getInstance method.
     */
    private Drivetrain() {

        // CAN ID settings
        r_primary = new CANSparkMax(RobotMap.Subsystems.Drivetrain.R_PRIMARY, MotorType.kBrushless);
        r_secondary = new CANSparkMax(RobotMap.Subsystems.Drivetrain.R_SECONDARY, MotorType.kBrushless);
        l_primary = new CANSparkMax(RobotMap.Subsystems.Drivetrain.L_PRIMARY, MotorType.kBrushless);
        l_secondary = new CANSparkMax(RobotMap.Subsystems.Drivetrain.L_SECONDARY, MotorType.kBrushless);

        // Speed control groups
        r_secondary.follow(r_primary);
        l_secondary.follow(l_primary);

        // Inverse logic
        r_primary.setInverted(RobotMap.Subsystems.Drivetrain.RIGHT_IS_INVERTED);
        l_primary.setInverted(RobotMap.Subsystems.Drivetrain.LEFT_IS_INVERTED);

        // Max acceleration
        // r_primary.setOpenLoopRampRate(RobotMap.Subsystems.Drivetrain.MAX_ACCEL_RATE);
        // l_primary.setOpenLoopRampRate(RobotMap.Subsystems.Drivetrain.MAX_ACCEL_RATE);

        // PID controller instantiation
        turnToLimelightPID = new PIDController(RobotMap.PIDConstants.Drivetrain.TurnToLimelight.KP,
                RobotMap.PIDConstants.Drivetrain.TurnToLimelight.KI,
                RobotMap.PIDConstants.Drivetrain.TurnToLimelight.KD);
        driveToLimelightPID = new PIDController(RobotMap.PIDConstants.Drivetrain.DriveToLimelight.KP,
                RobotMap.PIDConstants.Drivetrain.DriveToLimelight.KI,
                RobotMap.PIDConstants.Drivetrain.DriveToLimelight.KD);
        turnToAstraPID = new PIDController(RobotMap.PIDConstants.Drivetrain.TurnToAstra.KP,
                RobotMap.PIDConstants.Drivetrain.TurnToAstra.KI, RobotMap.PIDConstants.Drivetrain.TurnToAstra.KD);
        driveToAstraPID = new PIDController(RobotMap.PIDConstants.Drivetrain.DriveToAstra.KP,
                RobotMap.PIDConstants.Drivetrain.DriveToAstra.KI, RobotMap.PIDConstants.Drivetrain.DriveToAstra.KD);
        rightSidePID = new PIDController(RobotMap.PIDConstants.Drivetrain.RightDrivetrain.KP,
                RobotMap.PIDConstants.Drivetrain.RightDrivetrain.KI,
                RobotMap.PIDConstants.Drivetrain.RightDrivetrain.KD);
        leftSidePID = new PIDController(RobotMap.PIDConstants.Drivetrain.LeftDrivetrain.KP,
                RobotMap.PIDConstants.Drivetrain.LeftDrivetrain.KI,
                RobotMap.PIDConstants.Drivetrain.LeftDrivetrain.KD);
        driveStraightPID = new PIDController(RobotMap.PIDConstants.Drivetrain.LeftDrivetrain.KP,
                RobotMap.PIDConstants.Drivetrain.LeftDrivetrain.KI,
                RobotMap.PIDConstants.Drivetrain.LeftDrivetrain.KD);

        // Setpoint tolerances
        turnToLimelightPID.setTolerance(0.7);
        driveToLimelightPID.setTolerance(0.55);
        rightSidePID.setTolerance(0.25);
        leftSidePID.setTolerance(0.25);
        driveStraightPID.setTolerance(0.25);

        r_primary.getEncoder().setPositionConversionFactor(0.181348469499);
        l_primary.getEncoder().setPositionConversionFactor(0.181348469499);
    }

    /**
     * Creates a Singleton for class Drivetrain. Use this whenever Drivetrain and
     * it's methods need to be accessed
     * 
     * @return a static reference to class Drivetrain
     */
    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    /**
     * Sets the speed of the right drivetrain
     * 
     * @param speed speed at which the motors should run; takes values from -1 to 1,
     *              numbers above or below are trumpicated to -1 or 1
     */
    public void setRightSpeed(double speed) {
        r_primary.set(speed);
    }

    /**
     * Sets the speed of the left drivetrain
     * 
     * @param speed speed at which the motors should run; takes values from -1 to 1,
     *              numbers above or below are trumpicated to -1 or 1
     */
    public void setLeftSpeed(double speed) {
        l_primary.set(speed);
    }

    /**
     * Gets the current encoder value of the right side of the drivetrain
     * 
     * @return current distance traveled, in ft, since last reset for right side
     */
    public double getRightPosition() {
        return -1 * r_primary.getEncoder().getPosition();
    }

    /**
     * Gets the current encoder value of the left side of the drivetrain
     * 
     * @return current distance traveled, in ft, since last reset for left side
     */
    public double getLeftPosition() {
        return -1 * l_primary.getEncoder().getPosition();
    }

    /**
     * Resets the Drivetrain
     */
    public void reset() {
        r_primary.getEncoder().setPosition(0);
        l_primary.getEncoder().setPosition(0);
        turnToLimelightPID.reset();
        turnToAstraPID.reset();
        driveToLimelightPID.reset();
        rightSidePID.reset();
        leftSidePID.reset();
    }

    /**
     * Uses the left and right y-axis of the driver controller to control speed
     * 
     * @param maxSpeed maximum speed at which Drivetrain can run
     */
    public void tankDrive(double maxSpeed) {
        double leftSpeed = maxSpeed * OI.driver.getLY();
        double rightSpeed = maxSpeed * OI.driver.getRY();
        setLeftSpeed(leftSpeed);
        setRightSpeed(rightSpeed);
    }

    /**
     * Uses the left y-axis and the right x-axis of the driver controller to control
     * speed
     * 
     * @param maxSpeed maximum speed at which Drivetrain can run
     */
    public void arcadeDrive(double maxSpeed) {
        double yControl = maxSpeed * OI.driver.getLY();
        double xControl = maxSpeed * OI.driver.getRX();
        setLeftSpeed(yControl + xControl);
        setRightSpeed(yControl - xControl);
    }

    /**
     * Drives straight towards a target
     * 
     * @param target distance to target in feet
     */
    public void driveStraight(double target) {
        double calcSpeed = driveStraightPID.calculate(getLeftPosition(), 0);
        setLeftSpeed(calcSpeed * 0.80);
        setRightSpeed(calcSpeed * 0.80);
    }

    /**
     * Turns the robot to the limelight so that the angle is 0
     */
    public void turnToLimelight() {
        double calcSpeed = -turnToLimelightPID.calculate(Robot.limelight.getTx(), 0);
        setLeftSpeed(-calcSpeed);
        setRightSpeed(calcSpeed);
    }

    /**
     * Turns the robot to the nearest ball so that the angle is 0
     */
    public void turnToAstra() {
        double calcSpeed = -turnToAstraPID.calculate(Robot.astra.getTx(0), 0);
        calcSpeed *= 0.8;
        setLeftSpeed(-calcSpeed);
        setRightSpeed(calcSpeed);
    }

    /**
     * Drives the robot to target distance from the hub
     * (usually used to make shots)
     * 
     * @param distance target distance, in ft
     */
    public void driveToLimelight(double distance) {
        double calcSpeed = -driveToLimelightPID.calculate(Robot.limelight.getDistance(), distance);
        calcSpeed *= 0.6;
        setLeftSpeed(calcSpeed);
        setRightSpeed(calcSpeed);
    }

    /**
     * Drives the robot to the nearest ball that is of our alliance color
     * 
     * @param distance target distance, in ft
     */
    public void driveToAstra() {
        double calcSpeed = -driveToAstraPID.calculate(Robot.astra.getTd(0), 0);
        setLeftSpeed(calcSpeed);
        setRightSpeed(calcSpeed);
    }

    /**
     * Drives the left side to target distance
     * 
     * @param distance target distance, in ft
     */
    public void leftSide(double distance) {
        double calcSpeed = leftSidePID.calculate(getLeftPosition(), distance);
        setLeftSpeed(calcSpeed);
    }

    /**
     * Drives the right side to target distance
     * 
     * @param distance target distance, in ft
     */
    public void rightSide(double distance) {
        double calcSpeed = rightSidePID.calculate(getRightPosition(), distance);
        setRightSpeed(calcSpeed);
    }

    /**
     * Gets the boolean value of where the PID controller is at
     * 
     * @return true if at target false if not
     */
    public boolean turnToLimelightAtTarget() {
        return turnToLimelightPID.atSetpoint();
    }

    /**
     * Gets the boolean value of where the PID controller is at
     * 
     * @return true if at target false if not
     */
    public boolean driveToLimelightAtTarget() {
        return driveToLimelightPID.atSetpoint();
    }

    /**
     * Gets the boolean value of where the PID controller is at
     * 
     * @return true if at target false if not
     */
    public boolean turnToAstraAtTarget() {
        return turnToAstraPID.atSetpoint();
    }

    /**
     * Gets the boolean value of where the PID controller is at
     * 
     * @return true if at target false if not
     */
    public boolean driveToAstraAtTarget() {
        return driveToAstraPID.atSetpoint();
    }

    /**
     * Gets the boolean value of where the PID controller is at
     * 
     * @return true if at target false if not
     */
    public boolean rightAtTarget() {
        return rightSidePID.atSetpoint();
    }

    /**
     * Gets the boolean value of where the PID controller is at
     * 
     * @return true if at target false if not
     */
    public boolean leftAtTarget() {
        return leftSidePID.atSetpoint();
    }

    /**
     * Sets the speed of the drivetrain to zero.
     */
    public void stop() {
        setRightSpeed(0);
        setLeftSpeed(0);
    }

    public void driveStraightRight(double target, double maxPower, double smoothnessFactor) {
        double distanceToTarget = Math.abs(target) - Math.abs(getRightPosition());
        double calcSpeed = Math.log(distanceToTarget + 1) / Math.log(smoothnessFactor);
        calcSpeed = calcSpeed * maxPower;
        if (target > 0) {
            calcSpeed = calcSpeed * -1;
        }
        setRightSpeed(calcSpeed);
    }

    public void driveStraightLeft(double target, double maxPower, double smoothnessFactor) {
        double distanceToTarget = Math.abs(target) - Math.abs(getLeftPosition());
        double calcSpeed = Math.log(distanceToTarget + 1) / Math.log(smoothnessFactor);
        calcSpeed = calcSpeed * maxPower;
        if (target > 0) {
            calcSpeed = calcSpeed * -1;
        }
        setLeftSpeed(calcSpeed);
    }

    public PIDController getTurnToLimelightPID() {
        return this.turnToLimelightPID;
    }

}

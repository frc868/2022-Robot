package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.OI;
import frc.robot.RobotMap;

public class Drivetrain {
   private CANSparkMax r_primary, r_secondary, r_teritary, l_primary, l_secondary, l_tertiary;
   private PIDController turnToLimelightPID, driveToLimelightPID, leftSidePID, rightSidePID;
   public static Drivetrain instance;

   private Limelight dLimelight = Limelight.getInstance();
   
   /**
    * Constructor of class Drivetrain. Used to instantiate the Drivetrain class. Should only be used in getInstance method.
    */
   private Drivetrain(){

    // CAN ID settings
    r_primary = new CANSparkMax(RobotMap.SUBSYSTEMS.DRIVETRAIN.R_PRIMARY, MotorType.kBrushless);
    r_secondary = new CANSparkMax(RobotMap.SUBSYSTEMS.DRIVETRAIN.R_SECONDARY, MotorType.kBrushless);
    r_teritary = new CANSparkMax(RobotMap.SUBSYSTEMS.DRIVETRAIN.R_TERTIARY, MotorType.kBrushless);
    l_primary = new CANSparkMax(RobotMap.SUBSYSTEMS.DRIVETRAIN.L_PRIMARY, MotorType.kBrushless);
    l_secondary = new CANSparkMax(RobotMap.SUBSYSTEMS.DRIVETRAIN.L_SECONDARY, MotorType.kBrushless);
    l_tertiary = new CANSparkMax(RobotMap.SUBSYSTEMS.DRIVETRAIN.L_TERTIARY, MotorType.kBrushless);

    // Speed control groups
    r_secondary.follow(r_primary);
    r_teritary.follow(r_primary);
    l_secondary.follow(l_primary);
    l_tertiary.follow(l_primary);

    // Inverse logic
    r_primary.setInverted(RobotMap.SUBSYSTEMS.DRIVETRAIN.RIGHT_IS_INVERTED);
    l_primary.setInverted(RobotMap.SUBSYSTEMS.DRIVETRAIN.LEFT_IS_INVERTED);

    // Max acceleration
    r_primary.setOpenLoopRampRate(RobotMap.SUBSYSTEMS.DRIVETRAIN.TIME);
    l_primary.setOpenLoopRampRate(RobotMap.SUBSYSTEMS.DRIVETRAIN.TIME);

    //PID controller instantiation
    turnToLimelightPID = new PIDController(RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.TURN_TO_LIMELIGHT_PID.KP, RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.TURN_TO_LIMELIGHT_PID.KI, RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.TURN_TO_LIMELIGHT_PID.KD);
    driveToLimelightPID = new PIDController(RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.DRIVE_TO_LIMELIGHT_PID.KP, RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.DRIVE_TO_LIMELIGHT_PID.KI, RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.DRIVE_TO_LIMELIGHT_PID.KD);
    rightSidePID = new PIDController(RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.RIGHT_SIDE_PID.KP, RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.RIGHT_SIDE_PID.KI, RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.RIGHT_SIDE_PID.KD);
    leftSidePID = new PIDController(RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.LEFT_SIDE_PID.KP, RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.LEFT_SIDE_PID.KI, RobotMap.PID_CONSTANTS.DRIVETRAIN_CONSTANTS.LEFT_SIDE_PID.KD);
   }

   /**
    * Creates a Singleton for class Drivetrain. Use this whenever Drivetrain and it's methods need to be accessed
    * @return a static reference to class Drivetrain
    */
   public static Drivetrain getInstance(){
       if(instance == null){
           instance = new Drivetrain();
       }
       return instance;
   }

   /**
    * Sets the speed of the right drivetrain
    * @param speed speed at which the motors should run; takes values from -1 to 1, numbers above or below are trumpicated to -1 or 1
    */
   public void setRightSpeed(double speed){
       r_primary.set(speed);
   }

   /**
    * Sets the speed of the left drivetrain
    * @param speed speed at which the motors should run; takes values from -1 to 1, numbers above or below are trumpicated to -1 or 1
    */
   public void setLeftSpeed(double speed){
       l_primary.set(speed);
   }

   /**
    * Gets the current encoder value of the right side of the drivetrain 
    * @return current distance traveled, in ft, since last reset for right side
    */
   public double getRightPosition(){
       double calcDistance = (r_primary.getEncoder().getPosition() * 3 * (24 /50 )) / 12; //6 inch wheel diameter, 24/50 gear reduction
       return calcDistance;
   }

   /**
    * Gets the current encoder value of the left side of the drivetrain
    * @return current distance traveled, in ft, since last reset for left side
    */
   public double getLeftPosition(){
        double calcDistance = (l_primary.getEncoder().getPosition() * 3) / 12; //6 inch wheel diameter
        return calcDistance;
   }

   /**
    * Resets the position of the encoders
    */
   public void reset(){
       r_primary.getEncoder().setPosition(0);
       l_primary.getEncoder().setPosition(0);
   }

   /**
    * Uses the left and right y-axis of the driver controller to control speed
    * @param maxSpeed maximum speed at which Drivetrain can run
    */
   public void tankDrive(double maxSpeed){
       double leftSpeed = maxSpeed * OI.driver.getLY();
       double rightSpeed = maxSpeed *OI.driver.getRY();
       setLeftSpeed(leftSpeed);
       setRightSpeed(rightSpeed);
   }

   /**
    * Uses the left y-axis and the right x-axis of the driver controller to control speed
    * @param maxSpeed maximum speed at which Drivetrain can run
    */
   public void arcadeDrive(double maxSpeed){
       double yControl = maxSpeed * OI.driver.getLY();
       double xControl = maxSpeed * OI.driver.getRX();
       setLeftSpeed(yControl + xControl);
       setRightSpeed(yControl - xControl);
   }

   /**
    * Turns the robot to the limelight so that the angle is 0
    */
   public void turnToLimelight() {
        double calcSpeed = -turnToLimelightPID.calculate(dLimelight.getTx(), 0);
        setLeftSpeed(-calcSpeed);
        setRightSpeed(calcSpeed);
    }

    /**
     * Drives the robot to target distance
     * @param distance target distance, in ft
     */
    public void driveToLimelight(double distance){
        double calcSpeed = -driveToLimelightPID.calculate(dLimelight.getDistance(), distance);
        setLeftSpeed(calcSpeed);
        setRightSpeed(calcSpeed);
    }

    /**
     * Drives the left side to target distance
     * @param distance target distance, in ft
     */
    public void leftSide(double distance){
        double calcSpeed = leftSidePID.calculate(getLeftPosition(), distance);
        setLeftSpeed(calcSpeed);
    }

    /**
     * Drives the right side to target distance
     * @param distance target distance, in ft
     */
    public void rightSide(double distance){
        double calcSpeed = rightSidePID.calculate(getRightPosition(), distance);
        setRightSpeed(calcSpeed);
    }

}

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;



//1 encoder count = 3.91 degrees of rotation

public class Drivetrain {
    private CANSparkMax r_primary, r_secondary, r_teritary, l_primary, l_secondary, l_teritary;
    public static Drivetrain instance;
    
    private PIDController pid;
    private PIDController drivePID;
    private double kP, kI, kD;
    private Drivetrain() {
        
        r_primary = new CANSparkMax(RobotMap.Drivetrain.R_PRIMARY, MotorType.kBrushless);
        r_primary.setInverted(RobotMap.Drivetrain.RIGHT_IS_INVERTED);
        
        r_secondary = new CANSparkMax(RobotMap.Drivetrain.R_SECONDARY, MotorType.kBrushless);
        r_secondary.setInverted(RobotMap.Drivetrain.RIGHT_IS_INVERTED);

        r_teritary = new CANSparkMax(RobotMap.Drivetrain.R_TERTIARY, MotorType.kBrushless);
        r_teritary.setInverted(RobotMap.Drivetrain.RIGHT_IS_INVERTED);

        l_primary = new CANSparkMax(RobotMap.Drivetrain.L_PRIMARY, MotorType.kBrushless);
        l_primary.setInverted(RobotMap.Drivetrain.LEFT_IS_INVERTED);

        l_secondary = new CANSparkMax(RobotMap.Drivetrain.L_SECONDARY, MotorType.kBrushless);
        l_secondary.setInverted(RobotMap.Drivetrain.LEFT_IS_INVERTED);

        l_teritary = new CANSparkMax(RobotMap.Drivetrain.L_TERTIARY, MotorType.kBrushless);
        l_teritary.setInverted(RobotMap.Drivetrain.LEFT_IS_INVERTED);

        if (Robot.isCompBot) {
            kP = RobotMap.PID_CONSTANTS.COMP_BOT.DRIVETRAIN_CONSTANTS.KP;
            kI = RobotMap.PID_CONSTANTS.COMP_BOT.DRIVETRAIN_CONSTANTS.KI;
            kD = RobotMap.PID_CONSTANTS.COMP_BOT.DRIVETRAIN_CONSTANTS.KD;
        }
        else {
            kP = RobotMap.PID_CONSTANTS.PRAC_BOT.DRIVETRAIN_CONSTANTS.KP;
            kI = RobotMap.PID_CONSTANTS.PRAC_BOT.DRIVETRAIN_CONSTANTS.KI;
            kD = RobotMap.PID_CONSTANTS.PRAC_BOT.DRIVETRAIN_CONSTANTS.KD;
        }

        pid = new PIDController(kP, kI, kD);

        drivePID = new PIDController(0.08, 0, 0);

        
    }

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    public void setRightSpeed(double speed) {
        r_primary.set(speed);
        r_secondary.set(speed);
        r_teritary.set(speed);
    }

    public void setLeftSpeed(double speed) {
        l_primary.set(speed);
        l_secondary.set(speed);
        l_teritary.set(speed);
    }

    public void setSpeed(double speed) {
        setLeftSpeed(speed);
        setRightSpeed(speed);
    }

    public void turnRight(double speed) {
        setLeftSpeed(speed);
        setRightSpeed(-speed);
    }

    public void turnLeft(double speed) {
        setLeftSpeed(-speed);
        setRightSpeed(speed);
    }

    public double getRightPosition() {
        return r_primary.getEncoder().getPosition();
    }

    public double getLeftPosition() {
        return l_primary.getEncoder().getPosition();
    }

    public void resetRightPosition() {
        r_primary.getEncoder().setPosition(0);
    }

    public void resetLeftPosition() {
        l_primary.getEncoder().setPosition(0);
    }

    public void setSpeed(double leftSpeed, double rightSpeed) {
        setLeftSpeed(leftSpeed);
        setRightSpeed(rightSpeed);
    }

    public void arcadeDrive(double speed) {
        double y = OI.driver.getLY();
        double x = OI.driver.getRX();
        y = -speed * y;
        x = -speed * x;
        setSpeed(y+x,y-x);
    }

    public void reset() {
        resetRightPosition();
        resetLeftPosition();
    }

    public void stop() {
        setSpeed(0);
    }

    public void tankDrive(double speed){
        double left = speed*OI.driver.getLY();
        double right = speed*OI.driver.getRY();

        setLeftSpeed(left);
        setRightSpeed(right);
    }

    //**********************AUTON STUFF**************************/

    public void driveStraight(double target, double maxPower, double smoothnessFactor) {
        double distanceToTarget = Math.abs(target) - Math.abs(getRightPosition());
        double calcSpeed = Math.log(distanceToTarget + 1)/Math.log(smoothnessFactor);
        calcSpeed = calcSpeed*maxPower;
        if (target < 0) {
            calcSpeed = calcSpeed * -1;
        }
        setSpeed(calcSpeed);
    }

    public void turntoZero(double maxPower, double smoothnessFactor){
        double distanceToTarget = Math.abs(getRightPosition());
        double calcSpeed = Math.log(distanceToTarget + 1)/Math.log(smoothnessFactor);
        if(getRightPosition() > 0){
            calcSpeed = calcSpeed * -1;
        }
        turnRight(calcSpeed);
    }

    public void driveArc(double targetLeft, double targetRight, double leftMaxSpeed, double rightMaxSpeed, double smoothnessFactor) {
        double distanceToLeftTarget = Math.abs(targetLeft) - Math.abs(l_primary.getEncoder().getPosition());
        double distanceToRightTarget = Math.abs(targetRight) - Math.abs(r_primary.getEncoder().getPosition());

        double calcLeftSpeed = Math.log(distanceToLeftTarget + 1)/Math.log(smoothnessFactor);
        double calcRightSpeed = Math.log(distanceToRightTarget + 1)/Math.log(smoothnessFactor);

        calcLeftSpeed = calcLeftSpeed * leftMaxSpeed;
        calcRightSpeed = calcRightSpeed * rightMaxSpeed;

        if (targetLeft < 0) {
            calcLeftSpeed = calcLeftSpeed * -1;
        }
        if (targetRight < 0) {
            calcRightSpeed = calcRightSpeed * -1;
        }

        setLeftSpeed(calcLeftSpeed);
        setRightSpeed(calcRightSpeed);
    }
    
    public void driveLeftArc(double target, double maxSpeed, double constantSpeed, double smoothnessFactor) {
        double distanceToLeftTarget = Math.abs(target) - Math.abs(r_primary.getEncoder().getPosition());
        double calcRightAdd = Math.log(distanceToLeftTarget + 1) / Math.log(smoothnessFactor);
        double calcLeftSpeed = constantSpeed;
        double calcRightSpeed = (maxSpeed * calcRightAdd) + constantSpeed;
        if (target < 0) {
            calcRightSpeed = calcRightSpeed * -1;
        }
        if (distanceToLeftTarget > 0) {
            setRightSpeed(calcRightSpeed);
            setLeftSpeed(calcLeftSpeed);
        }
        else {
            stop();
        }
    }

    public void driveRightArc(double target, double maxSpeed, double constantSpeed, double smoothnessFactor) {
        double distanceToRightTarget = Math.abs(target) - Math.abs(l_primary.getEncoder().getPosition());
        double calcLeftAdd = Math.log(distanceToRightTarget + 1) / Math.log(smoothnessFactor);
        double calcRightSpeed = constantSpeed;
        double calcLeftSpeed = (maxSpeed * calcLeftAdd) + constantSpeed;

        if (target < 0) {
            calcRightSpeed = calcRightSpeed * -1;
        }
        if (distanceToRightTarget > 0) {
            setRightSpeed(calcRightSpeed);
            setLeftSpeed(calcLeftSpeed);
        }
        else {
            stop();
        }
    }

    public void turnToLimelight() {
        double calcSpeed = pid.calculate(Robot.limelight.getTx(), 0);
        setLeftSpeed(-calcSpeed);
        setRightSpeed(calcSpeed);
    }

    public void turnToBall(){
        double calcSpeed = pid.calculate(Robot.astra.getTx(0), 0);
        setLeftSpeed(-calcSpeed);
        setRightSpeed(calcSpeed);
    }

    public void goToTarget(){
        double calcSpeed = drivePID.calculate(Robot.limelight.getDistance(), 17.5);
        setSpeed(calcSpeed);
    }



    public boolean atTarget() {
        pid.setTolerance(0.20);
        return pid.atSetpoint();
    }
  
}

package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.OI;
import frc.robot.RobotMap;


public class Drivetrain {
    private CANSparkMax r_primary, r_secondary, r_teritary, l_primary, l_secondary, l_teritary;
    public static Drivetrain instance;
   
    private Gyro gyro = Gyro.getInstance();
    private PIDController pid;
    private double kP, kI, kD;
    private Drivetrain(){
        
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

        kP = 0.005;
        kD = 0.0;
        kI = 0.0;

        pid = new PIDController(kP, kI, kD);
        
    }

    public static Drivetrain getInstance(){
        if(instance == null){
            instance = new Drivetrain();
        }
        return instance;
    }

    public void setRightSpeed(double speed){
        r_primary.set(speed);
        r_secondary.set(speed);
        r_teritary.set(speed);
    }

    public void setLeftSpeed(double speed){
        l_primary.set(speed);
        l_secondary.set(speed);
        l_teritary.set(speed);
    }

    public void setSpeed(double speed){
        setLeftSpeed(speed);
        setRightSpeed(speed);
    }

    public void turnRight(double speed){
        setLeftSpeed(speed);
        setRightSpeed(-speed);
    }

    public void turnLeft(double speed){
        setLeftSpeed(-speed);
        setRightSpeed(speed);
    }

    public double getRightPosition(){
        return r_primary.getEncoder().getPosition();
    }

    public double getLeftPOsition(){
        return l_primary.getEncoder().getPosition();
    }

    public void resetRightPosition(){
        r_primary.getEncoder().setPosition(0);
    }

    public void resetLeftPosition(){
        l_primary.getEncoder().setPosition(0);
    }

    public void setSpeed(double leftSpeed, double rightSpeed){
        setLeftSpeed(leftSpeed);
        setRightSpeed(rightSpeed);
    }

    public void arcadeDrive(double speed){
        double y = OI.driver.getLY();
        double x = OI.driver.getRX();
        y = -speed * y;
        x = -speed * x;
        setSpeed(y-x,y+x);
    }

    public void reset(){
        resetRightPosition();
        resetLeftPosition();
    }

    //**********************AUTON STUFF**************************/

    public void driveStraight(double target, double maxPower, double smoothnessFactor){
        double distanceToTarget = Math.abs(target) - Math.abs(getRightPosition());
        double calcSpeed = Math.log(distanceToTarget + 1)/Math.log(smoothnessFactor);
        calcSpeed = calcSpeed*maxPower;
        if(target < 0){
            calcSpeed = calcSpeed * -1;
        }
        setSpeed(calcSpeed);
    }

    public void turnToAngle(double target){
        double calcSpeed = pid.calculate(gyro.getYaw(), target);
        setRightSpeed(calcSpeed);
        setLeftSpeed(-calcSpeed);
    }

    public void driveArc(double targetLeft, double targetRight, double leftMaxSpeed, double rightMaxSpeed, double smoothnessFactor){
        double distanceToLeftTarget = Math.abs(targetLeft) - Math.abs(l_primary.getEncoder().getPosition());
        double distanceToRightTarget = Math.abs(targetRight) - Math.abs(r_primary.getEncoder().getPosition());

        double calcLeftSpeed = Math.log(distanceToLeftTarget + 1)/Math.log(smoothnessFactor);
        double calcRightSpeed = Math.log(distanceToRightTarget + 1)/Math.log(smoothnessFactor);

        calcLeftSpeed = calcLeftSpeed * leftMaxSpeed;
        calcRightSpeed = calcRightSpeed * rightMaxSpeed;

        if(targetLeft < 0){
            calcLeftSpeed = calcLeftSpeed * -1;
        }
        if(targetRight < 0){
            calcRightSpeed = calcRightSpeed * -1;
        }

        setLeftSpeed(calcLeftSpeed);
        setRightSpeed(calcRightSpeed);
    }
    
    public void driveLeftArc(double target, double maxSpeed, double constantSpeed, double smoothnessFactor){
        double calcRightAdd = Math.log(target + 1) / Math.log(smoothnessFactor);
        double calcLeftSpeed = constantSpeed;
        double calcRightSpeed = (maxSpeed * calcRightAdd) + constantSpeed;
        setRightSpeed(calcRightSpeed);
        setLeftSpeed(calcLeftSpeed);
    }

    public void driveRightArc(double target, double maxSpeed, double constantSpeed, double smoothnessFactor){
        double calcLeftAdd = Math.log(target + 1) / Math.log(smoothnessFactor);
        double calcRightSpeed = constantSpeed;
        double calcLeftSpeed = (maxSpeed * calcLeftAdd) + constantSpeed;
        setRightSpeed(calcRightSpeed);
        setLeftSpeed(calcLeftSpeed);
    }
}
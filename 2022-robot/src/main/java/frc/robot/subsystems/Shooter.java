package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Shooter {
    private CANSparkMax s_primary, s_secondary;
    private PIDController pid;
    private double kP, kI, kD;
    private double vI = 0;
    private double vF = 0;
    private DigitalInput irsensor_shooter;
    public static Shooter instance;

    //theory
    private boolean loweredSpeed = false;
    private boolean gainedSpeed = false;

     //ir sensor logic
     private boolean previous = false;
     private boolean toggle = false;

    private Shooter() {
        s_primary = new CANSparkMax(RobotMap.Shooter.S_PRIMARY, MotorType.kBrushless);
        s_secondary = new CANSparkMax(RobotMap.Shooter.S_SECONDARY, MotorType.kBrushless);

        
        irsensor_shooter = new DigitalInput(2);


        s_primary.setInverted(RobotMap.Shooter.IS_INVERTED);
    
        s_secondary.follow(s_primary, true);


        if (Robot.isCompBot) {
            kP = RobotMap.PID_CONSTANTS.COMP_BOT.SHOOTER_CONSTANTS.KP;
            kI = RobotMap.PID_CONSTANTS.COMP_BOT.SHOOTER_CONSTANTS.KI;
            kD = RobotMap.PID_CONSTANTS.COMP_BOT.SHOOTER_CONSTANTS.KD;
        }
        else {
            kP = RobotMap.PID_CONSTANTS.PRAC_BOT.SHOOTER_CONSTANTS.KP;
            kI = RobotMap.PID_CONSTANTS.PRAC_BOT.SHOOTER_CONSTANTS.KI;
            kD = RobotMap.PID_CONSTANTS.PRAC_BOT.SHOOTER_CONSTANTS.KD;
        }
        pid = new PIDController(kP, kI, kD);
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public double getRPM() {
        return s_primary.getEncoder().getVelocity();
    }

    public void setSpeed(double speed) {
        s_primary.set(speed);
    }

    public void stop() {
        s_primary.set(0);
    }

    public void shoot(double targetRPM) {
        double calcSpeed = pid.calculate(getRPM(), targetRPM);
        setSpeed(calcSpeed);
    }
    
    public boolean onTarget() {
        pid.setTolerance(30);
        return pid.atSetpoint();
    }

    //theory code
    public void ballIsShoot(double bounds) {
        vI = vF;
        vF = s_primary.getEncoder().getVelocity();
        double acceleration = (vF - vI) / 0.02;
        if (acceleration < -bounds) {
            loweredSpeed = false;
        }
        if(onTarget() && loweredSpeed){
           loweredSpeed = true;
           Robot.hopper.addBall();
        }
        
     }


    public void shootLogic(double targetRPM) {
        shoot(targetRPM);
        if(onTarget()){
            Robot.hopper.setSpeed(1);
        }
        else{
            Robot.hopper.stop();
        }

    }

    public boolean getCurrent() {
        return irsensor_shooter.get();
    }

    public double acceleration(){
        vI = vF;
        vF = s_primary.getEncoder().getVelocity();
        double acceleration = (vF - vI) / 0.02;
        return acceleration;
    }

    public double calcSpeed(){
        double distance = Robot.limelight.getDistance();
        double calcSpeed = 2020 * Math.pow(Math.E, 0.0264*distance);
        return calcSpeed;
    }
    

}

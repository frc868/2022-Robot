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
            loweredSpeed = true;
        }
        if (acceleration > bounds) {
            gainedSpeed = true;
        }
        if (loweredSpeed && gainedSpeed) {
            Robot.hopper.subBall();
            loweredSpeed = false;
            gainedSpeed = false;
        }
    }

    public void shootLogic(double targetRPM) {
        if (Robot.hopper.ballCount > 0) {
            shoot(targetRPM);
            if (onTarget()) {
                Robot.hopper.setBack();
                Robot.hopper.run();
            }
            else {
                Robot.hopper.setForward();
                Robot.hopper.stop();
            }
        }
    }

    public boolean getCurrent() {
        return irsensor_shooter.get();
    }

    public void subBall() {
        if (!getCurrent() && previous) { 
            toggle = !toggle;
        }

        previous = getCurrent();

        if (toggle) {
            Robot.hopper.subBall();
            toggle = !toggle;
        }
    }
}

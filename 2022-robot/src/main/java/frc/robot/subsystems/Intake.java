package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Intake {
    private CANSparkMax intake;
    private DoubleSolenoid upDowner_1; 
    private DoubleSolenoid upDowner_2;
    private double vI = 0;
    private double vF = 0;
    public static Intake instance;

    //theory
    private boolean loweredSpeed = false;
    private boolean gainedSpeed = false;


    private Intake(){
        intake = new CANSparkMax(RobotMap.Intake.INTAKE, MotorType.kBrushless); //TODO: think they using 550, make sure to check
        intake.setInverted(RobotMap.Intake.IS_INVERTED);

        upDowner_1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Intake.UPDOWNER11, RobotMap.Intake.UPDOWNER12); //TODO: assuming we using the rev pneumatics hub and a double solenoid
        upDowner_2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Intake.UPDOWNER21, RobotMap.Intake.UPDOWNER22);
    }

    public static Intake getInstance(){
        if(instance == null){
            instance = new Intake();
        }
        return instance;
    }

    public void setDown(){
        upDowner_1.set(Value.kForward);
        upDowner_2.set(Value.kForward);
    }

    public void setUp(){
        upDowner_1.set(Value.kReverse);
        upDowner_2.set(Value.kReverse);
    }

    public void toggle(){
        upDowner_1.toggle();
        upDowner_2.toggle();
    }

    public void setDefault(){
        upDowner_1.set(Value.kReverse);
        upDowner_2.set(Value.kReverse);
    }

    //Theory code. Theory is that when the ball is taken in by the intake the graph of the RPM vs. Time graph will have a drop in it which can tell use whether or not a ball is added
    //Uses IVT to know when a ball is added
    public void addBall(){
        vI = vF;
        vF = intake.getEncoder().getVelocity();
        double acceleration = (vF - vI) / 0.02;
        if(acceleration < -5){
            loweredSpeed = true;
        }
        if(acceleration > 5){
            gainedSpeed = true;
        }
        if(loweredSpeed && gainedSpeed){
            Robot.hopper.addBall();
            loweredSpeed = false;
            gainedSpeed = false;
        }
    }
    
}

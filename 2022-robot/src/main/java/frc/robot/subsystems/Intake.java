package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Intake {
    private CANSparkMax i_primary, i_secondary;
    private DoubleSolenoid upDowner_1; 
    private DoubleSolenoid upDowner_2;
    private double vI = 0;
    private double vF = 0;
    private DigitalInput irsensor_intake;
    public static Intake instance;

    //theory
    private boolean loweredSpeed = false;
    private boolean gainedSpeed = false;

    //ir sensor logic
    private boolean previous = false;
    private boolean toggle = false;


    private Intake(){
        i_primary = new CANSparkMax(RobotMap.Intake.I_PRIMARY, MotorType.kBrushless); //TODO: think they using 550, make sure to check
        i_secondary = new CANSparkMax(RobotMap.Intake.I_SECONDARY, MotorType.kBrushless);
        irsensor_intake = new DigitalInput(1);

        i_secondary.follow(i_primary, true);
        i_primary.setInverted(RobotMap.Intake.IS_INVERTED);

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
    public void addBall(double bounds){
        vI = vF;
        vF = i_primary.getEncoder().getVelocity();
        double acceleration = (vF - vI) / 0.02;
        if(acceleration < -bounds){
            loweredSpeed = true;
        }
        if(acceleration > bounds){
            gainedSpeed = true;
        }
        if(loweredSpeed && gainedSpeed){
            Robot.hopper.addBall();
            loweredSpeed = false;
            gainedSpeed = false;
        }
    }
    
    public void run(){
        i_primary.set(0.1);
    }

    public boolean getCurrent(){
        return irsensor_intake.get();
    }

    public void addBall(){
        if (getCurrent() && !previous) { 
            toggle = !toggle;
        }

        previous = getCurrent();

        if (toggle) {
            Robot.hopper.addBall();
            toggle = !toggle;
        }
    }

    
}

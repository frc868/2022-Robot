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
    private CANSparkMax i_primary;
    private DoubleSolenoid intake_solenoid; 
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


    private Intake() {
        i_primary = new CANSparkMax(RobotMap.Intake.I_PRIMARY, MotorType.kBrushless); //TODO: think they using 550, make sure to check
        irsensor_intake = new DigitalInput(1);

        i_primary.setInverted(RobotMap.Intake.IS_INVERTED);

        intake_solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Intake.INTAKE_SOLENOID_CHANNEL2, RobotMap.Intake.INTAKE_SOLENOID_CHANNEL1);
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    public void setForward(){
        intake_solenoid.set(Value.kForward);
    }

    public void setReverse(){
        intake_solenoid.set(Value.kReverse);
    }

    public void toggle(){
        intake_solenoid.toggle();
    }

    public void print(){
        System.out.println(intake_solenoid.get());
    }

    //Theory code. Theory is that when the ball is taken in by the intake the graph of the RPM vs. Time graph will have a drop in it which can tell use whether or not a ball is added
    //Uses IVT to know when a ball is added
    public void addBall(double bounds) {
        vI = vF;
        vF = i_primary.getEncoder().getVelocity();
        double acceleration = (vF - vI) / 0.02;
        if (acceleration < -bounds) {
            loweredSpeed = true;
        }
        if (loweredSpeed && acceleration > bounds) {
            gainedSpeed = true;
        }
        if (loweredSpeed && gainedSpeed) {
            Robot.hopper.addBall();
            loweredSpeed = false;
            gainedSpeed = false;
        }
    }
    
    public void run() {
        i_primary.set(1);
    }

    public void reverse(){
        i_primary.set(-1);
    }

    public void stop(){
        i_primary.set(0);
    }

    public boolean getCurrent() {
        return irsensor_intake.get();
    }

    public void addBall() {
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

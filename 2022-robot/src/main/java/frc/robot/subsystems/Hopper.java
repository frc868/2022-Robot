package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;

public class Hopper {
    private CANSparkMax hopper;
    private Solenoid hopper_actuator;
    public int ballCount = 1;
    public static Hopper instance;

    private Hopper(){
        hopper = new CANSparkMax(RobotMap.Hopper.HOPPER, MotorType.kBrushless); 
        hopper.setInverted(RobotMap.Hopper.IS_INVERTED);

        hopper_actuator = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.Hopper.HOPPER_ACTUATOR);
    }

    public static Hopper getInstance() {
        if (instance == null) {
            instance = new Hopper();
        }
        return instance;
    }

    public void run() {
        hopper.set(0.1);
    }

    public void setSpeed(double speed) {
        hopper.set(speed);
    }

    public void addBall() {
        ballCount = ballCount + 1;
    }

    public void subBall() {
        ballCount = ballCount - 1;
    }

    public double ballsInHopper() {
        return ballCount;
    }

    public void setForward(){
        hopper_actuator.set(true);
    }

    public void setBack(){
        hopper_actuator.set(false);
    }

    public void toggle(){
        hopper_actuator.toggle();
    }

    public void stop() {
        setSpeed(0);
    }
    
    
}

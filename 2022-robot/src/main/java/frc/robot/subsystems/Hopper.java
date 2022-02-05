package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;

public class Hopper {
    private CANSparkMax hopper;
    private Solenoid first, second;
    public int ballCount = 1;
    public static Hopper instance;

    private Hopper(){
        hopper = new CANSparkMax(RobotMap.Hopper.HOPPER, MotorType.kBrushless); //TODO:Pretty sure its a 550 but need to check
        hopper.setInverted(RobotMap.Hopper.IS_INVERTED);

        first = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.Hopper.FIRST);
        second = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.Hopper.SECOND);
    }

    public static Hopper getInstance(){
        if(instance == null){
            instance = new Hopper();
        }
        return instance;
    }

    public void run(){
        hopper.set(0.1);
    }

    public void setSpeed(double speed){
        hopper.set(speed);
    }

    public void addBall(){
        ballCount = ballCount + 1;
    }

    public void subBall(){
        ballCount = ballCount - 1;
    }

    public double ballsInHopper(){
        return ballCount;
    }

    public void setForward(){
        first.set(true);
        second.set(true);
    }

    public void setBack(){
        first.set(false);
        second.set(false);
    }

    public void stop(){
        setSpeed(0);
    }
    
}

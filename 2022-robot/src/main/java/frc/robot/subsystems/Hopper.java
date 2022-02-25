package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;

public class Hopper {
    private CANSparkMax hopper;
    private DoubleSolenoid hopper_solenoid;
    public int ballCount = 1;
    public static Hopper instance;

    private Hopper(){
        hopper = new CANSparkMax(RobotMap.Hopper.HOPPER, MotorType.kBrushless); 
        hopper.setInverted(RobotMap.Hopper.IS_INVERTED);

        hopper_solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Hopper.HOPPER_SOLENOID_2, RobotMap.Hopper.HOPPER_SOLENOID_1);
    }

    public static Hopper getInstance() {
        if (instance == null) {
            instance = new Hopper();
        }
        return instance;
    }

    public void run() {
        hopper.set(1);
    }

    public void print(){
        System.out.println(hopper_solenoid.get());
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
        hopper_solenoid.set(Value.kForward);
    }

    public void setReverse(){
        hopper_solenoid.set(Value.kReverse);
    }

    public void toggle(){
        hopper_solenoid.toggle();
    }

    public void stop() {
        setSpeed(0);
    }

    public double getDistance(){
        return hopper.getEncoder().getPosition();
    }

    public void reset(){
        hopper.getEncoder().setPosition(0);
    }
    
    
}

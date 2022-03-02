package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.RobotMap;

public class Climber {
    private CANSparkMax c_primary, c_secondary;
    private DoubleSolenoid climber_solenoid_1; 
    private DoubleSolenoid climber_solenoid_2;
    public static Climber instance;

    //Motors: 2 550s
    //Solenoids: 2 double 2 single
    private Climber() {
        c_primary = new CANSparkMax(RobotMap.Climber.C_PRIMARY, MotorType.kBrushless); //TODO: pretty sure they want to use NEOS have to check
        c_secondary = new CANSparkMax(RobotMap.Climber.C_SECONDARY, MotorType.kBrushless);

        climber_solenoid_1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Climber.CLIMBER_SOLENOID_CHANNEL1, RobotMap.Climber.CLIMBER_SOLENOID_CHANNEL2);
        climber_solenoid_2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Climber.CLIMBER_SOLENOID_SINGLE, 4);

        c_primary.setInverted(RobotMap.Climber.IS_INVERTED);
        c_secondary.follow(c_primary, true);

    }

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    public double getPrimaryPosition(){
        return c_primary.getEncoder().getPosition();
    }

    public void setSpeed(double speed){
        c_primary.set(speed);
    }

    public void setForward(){
        climber_solenoid_1.set(Value.kForward);
    }

    public void setReverse(){
        climber_solenoid_1.set(Value.kReverse);
    }

    public void setTrue(){
        climber_solenoid_2.set(Value.kForward);
    }

    public void setFalse(){
        climber_solenoid_2.set(Value.kReverse);
    }

    public void motorTarget(double target, double maxPower, double smoothnessFactor) {
        double distanceToTarget = Math.abs(target) - Math.abs(getPrimaryPosition());
        double calcSpeed = Math.log(distanceToTarget + 1)/Math.log(smoothnessFactor);
        calcSpeed = calcSpeed*maxPower;
        if (target < 0) {
            calcSpeed = calcSpeed * -1;
        }
        setSpeed(calcSpeed);
    }

}


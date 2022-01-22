package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;

public class Intake {
    private CANSparkMax intake;
    private DoubleSolenoid upDowner; 
    public static Intake instance;

    private Intake(){
        intake = new CANSparkMax(RobotMap.Intake.INTAKE, MotorType.kBrushless); //TODO: think they using 550, make sure to check
        intake.setInverted(RobotMap.Intake.IS_INVERTED);

        upDowner = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Intake.UPDOWNER1, RobotMap.Intake.UPDOWNER2); //TODO: assuming we using the rev pneumatics hub and a double solenoid
    }

    public void setDown(){
        upDowner.set(Value.kForward);
    }

    public void setUp(){
        upDowner.set(Value.kReverse);
    }

    public void toggle(){
        upDowner.toggle();
    }

    public void reset(){
        upDowner.set(Value.kReverse);
    }
}

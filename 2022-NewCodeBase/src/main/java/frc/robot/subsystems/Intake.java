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
    public static Intake instance;

    /**
    * Constructor of class Intake. Used to instantiate the Intake class. Should only be used in getInstance method.
    */
    private Intake() {

        //CAN ID setting
        i_primary = new CANSparkMax(RobotMap.SUBSYSTEMS.INTAKE.I_PRIMARY, MotorType.kBrushless); 

        //Solenoid ID setting
        intake_solenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.SUBSYSTEMS.INTAKE.INTAKE_SOLENOID_CHANNEL2, RobotMap.SUBSYSTEMS.INTAKE.INTAKE_SOLENOID_CHANNEL1);

        //Inverse logic
        i_primary.setInverted(RobotMap.SUBSYSTEMS.INTAKE.IS_INVERTED);

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
    

    
}

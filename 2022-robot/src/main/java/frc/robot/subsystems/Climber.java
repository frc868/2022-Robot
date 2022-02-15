package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;

public class Climber {
    private CANSparkMax c_primary, c_secondary;
    private DoubleSolenoid upDowner1, upDowner2;
    private Solenoid single1, single2;
    private PIDController pid;
    private double kP, kI, kD;
    public static Climber instance;

    //Motors: 2 550s
    //Solenoids: 2 double 2 single
    private Climber() {
        c_primary = new CANSparkMax(RobotMap.Climber.C_PRIMARY, MotorType.kBrushless); //TODO: pretty sure they want to use NEOS have to check
        c_secondary = new CANSparkMax(RobotMap.Climber.C_SECONDARY, MotorType.kBrushless);

        upDowner1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Climber.UPDOWNER11, RobotMap.Climber.UPDOWNER12);
        upDowner2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, RobotMap.Climber.UPDOWNER21, RobotMap.Climber.UPDOWNER22);
        single1 = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.Climber.SINGLE1);
        single2 = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.Climber.SINGLE2);

        c_primary.setInverted(RobotMap.Climber.IS_INVERTED);
        c_secondary.follow(c_primary, true);

        kP = 0.005;
        kI = 0.0;
        kD = 0.0;
        pid = new PIDController(kP, kI, kD);
    }

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    public void climb() { //TODO:Climb logic here, prob gonna use a pid of some tupe

    }
}


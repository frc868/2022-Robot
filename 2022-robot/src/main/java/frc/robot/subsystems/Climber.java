package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;

public class Climber {
    private CANSparkMax c_primary, c_secondary;
    private Solenoid climber_solenoid_1, climber_solenoid_2;
    private PIDController pid;
    private double kP, kI, kD;
    public static Climber instance;

    //Motors: 2 550s
    //Solenoids: 2 double 2 single
    private Climber() {
        c_primary = new CANSparkMax(RobotMap.Climber.C_PRIMARY, MotorType.kBrushless); //TODO: pretty sure they want to use NEOS have to check
        c_secondary = new CANSparkMax(RobotMap.Climber.C_SECONDARY, MotorType.kBrushless);

        climber_solenoid_1 = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.Climber.CLIMBER_SOLENOID_1);
        climber_solenoid_2 = new Solenoid(PneumaticsModuleType.REVPH, RobotMap.Climber.CLIMBER_SOLENOID_2);

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


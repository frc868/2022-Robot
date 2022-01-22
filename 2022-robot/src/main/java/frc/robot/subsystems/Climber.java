package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.RobotMap;

public class Climber {
    private CANSparkMax c_primary, c_secondary;
    private PIDController pid;
    private double kP, kI, kD;
    public static Climber instance;

    private Climber(){
        c_primary = new CANSparkMax(RobotMap.Climber.C_PRIMARY, MotorType.kBrushless); //TODO: pretty sure they want to use NEOS have to check
        c_secondary = new CANSparkMax(RobotMap.Climber.C_SECONDARY, MotorType.kBrushless);

        c_primary.setInverted(RobotMap.Climber.IS_INVERTED);
        c_secondary.follow(c_primary, true);

        kP = 0.005;
        kI = 0.0;
        kD = 0.0;
        pid = new PIDController(kP, kI, kD);
    }

    public static Climber getInstance(){
        if(instance == null){
            instance = new Climber();
        }
        return instance;
    }

    public void climb(){ //TODO:Climb logic here, prob gonna use a pid of some tupe

    }
}


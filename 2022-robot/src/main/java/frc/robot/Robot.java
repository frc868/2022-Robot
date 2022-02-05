package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auton.AutonChooser;
import frc.robot.auton.cameras.Astra;
import frc.robot.auton.cameras.Limelight;
import frc.robot.sensors.Gyro;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;




public class Robot extends TimedRobot {
  public static boolean isCompBot = false;
  public static Drivetrain drivetrain = Drivetrain.getInstance();
  public static Gyro gyro = Gyro.getInstance();
  public static AutonChooser autonChooser = AutonChooser.getInstance();
  public static Limelight limelight = Limelight.getInstance();
  public static Astra astra = Astra.getInstance();
  public static Shooter shooter = Shooter.getInstance();
  public static Hopper hopper = Hopper.getInstance();
  public static Intake intake = Intake.getInstance();

  @Override
  public void robotInit() {
    gyro.calibrate();
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    drivetrain.reset();
    gyro.reset();
    
    autonChooser.resetSelectedPath();
  }

  @Override
  public void autonomousPeriodic() {
    drivetrain.driveLeftArc(50, 0.4, 0.2, 60);
    //System.out.println(camera.getTx());
    //autonChooser.runSelectedPath();
    //drivetrain.turnToAngle(45);
    System.out.println(astra.getTx());
  }


  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    OI.updateOI();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}

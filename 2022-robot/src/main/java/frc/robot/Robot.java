package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auton.AutonChooser;
import frc.robot.auton.Camera;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro;




public class Robot extends TimedRobot {
  public static boolean isCompBot = true;
  public static Drivetrain drivetrain = Drivetrain.getInstance();
  public static Gyro gyro = Gyro.getInstance();
  public static AutonChooser autonChooser = AutonChooser.getInstance();
  public static Camera camera = Camera.getInstance();

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
    drivetrain.turnToLimelight();
    //System.out.println(camera.getTx());
    //autonChooser.runSelectedPath();
    //drivetrain.turnToAngle(45);
    System.out.println(camera.getTa());
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

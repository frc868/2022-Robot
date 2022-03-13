package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auton.AutonChooser;
import frc.robot.auton.cameras.Astra;
import frc.robot.auton.cameras.Limelight;
import frc.robot.sensors.Gyro;
import frc.robot.sensors.PressureSensor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climber;

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
  public static Climber climber = Climber.getInstance();
  public static PressureSensor pressure = PressureSensor.getInstance();
  private static Mat mat;
  private static CvSink sink;

  @Override
  public void robotInit() {
    OI.updateSmartDashboard();
    gyro.resetAngle();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    intake.setReverse();
    hopper.setReverse();
    hopper.reset();
    drivetrain.reset();

    autonChooser.resetSelectedPath();
  }

  @Override
  public void autonomousPeriodic() {
    autonChooser.runSelectedPath();
  }

  @Override
  public void teleopInit() {
    drivetrain.reset();
  }

  @Override
  public void teleopPeriodic() {
    OI.updateOI();
    OI.updateSmartDashboard();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
    SmartDashboard.putNumber("pressure", pressure.getPressure());
  }

  @Override
  public void testPeriodic() {

  }
}

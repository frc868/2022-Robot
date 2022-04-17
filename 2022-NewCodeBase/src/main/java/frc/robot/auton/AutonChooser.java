package frc.robot.auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.auton.paths.TwoBallEncoders;
import frc.robot.auton.paths.ThreeBallPartial;
import frc.robot.auton.paths.ThreeBall;

public class AutonChooser {
    private static AutonChooser instance;
    private SendableChooser<AutonPath> chooser = new SendableChooser<>();

    /**
     * Constructor that defines each choosable path.
     */
    private AutonChooser() {
        this.addPath("TwoBallEncoders", new TwoBallEncoders());
        this.addPath("ThreeBallPartial", new ThreeBallPartial());
        this.addPath("ThreeBall", new ThreeBall());
        // chooser.setDefaultOption("TwoBallEncoders", new TwoBallEncoders());
        chooser.setDefaultOption("ThreeBallPartial", new ThreeBallPartial());
        // chooser.setDefaultOption("ThreeBall", new ThreeBall());

    }

    public static AutonChooser getInstance() {
        if (instance == null) {
            return new AutonChooser();
        }

        return instance;
    }

    /**
     * Adds a path to the SmartDashboard chooser.
     * 
     * @param name the name of the path
     * @param path the AutonPath to use
     */
    public void addPath(String name, AutonPath path) {
        chooser.addOption(name, path);
    }

    /**
     * Runs the selected path. This is used in autonPeriodic.
     */
    public void runSelectedPath() {
        this.getCurrentPath().run();
    }

    public AutonPath getCurrentPath() {
        return chooser.getSelected();
    }

    public void reset() {
        chooser.getSelected().reset();
    }

}

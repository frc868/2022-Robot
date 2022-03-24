package frc.robot.auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.auton.paths.BackupTwoBallOriginal;

public class AutonChooser {
    private static AutonChooser instance;
    private SendableChooser<AutonPath> chooser = new SendableChooser<>();

    /**
     * Constructor that defines each choosable path.
     */
    private AutonChooser() {
        this.addPath("BackupTwoBallOriginal", new BackupTwoBallOriginal());
        chooser.setDefaultOption("BackupTwoBallOriginal", new BackupTwoBallOriginal());
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

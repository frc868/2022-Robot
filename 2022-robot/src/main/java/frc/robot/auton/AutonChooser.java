package frc.robot.auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auton.paths.FirstPath;

public class AutonChooser {
    private static AutonChooser instance;

    private SendableChooser<AutonPath> chooser = new SendableChooser<>();

    private AutonChooser() {
        this.addPath("FirstPath", new FirstPath());

        chooser.setDefaultOption("FirstPath", new FirstPath());
    }


    public static AutonChooser getInstance() {
        if (instance == null) {
            return new AutonChooser();
        }

        return instance;
    }


    public void addPath(String name, AutonPath func) {
        chooser.addOption(name, func);
    }

    public AutonPath getCurrentPath() {
        return chooser.getSelected();
    }

    public void runSelectedPath() {
        this.getCurrentPath().run();
    }


    public void resetSelectedPath() {
        this.getCurrentPath().reset();
    }

    public void initSD() {
        SmartDashboard.putData(chooser);
    }
}
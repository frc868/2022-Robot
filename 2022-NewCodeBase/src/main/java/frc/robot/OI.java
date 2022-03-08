package frc.robot;

import frc.robot.helpers.ControllerWrapper;

public class OI {

    // USB Ports
    public static ControllerWrapper driver = new ControllerWrapper(RobotMap.Controllers.DRIVER_PORT, true);
    public static ControllerWrapper operator = new ControllerWrapper(RobotMap.Controllers.OPERATOR_PORT, true);

    // Singleton Instance
    public static OI instance;

    /**
     * Creates a Singleton for class OI. Use this whenever OI and it's methods need
     * to be accessed
     * 
     * @return a static reference to class OI
     */
    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    /**
     * Code to be called during TeleOp periodic
     */
    public static void updateOI() {
        // Driver

    }

    /**
     * Code to display and get values on SmartDashboard
     */
    public static void updateSmartDashboard() {

    }
}
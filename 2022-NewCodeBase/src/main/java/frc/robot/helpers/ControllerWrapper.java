package frc.robot.helpers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.RobotMap;

/**
 * A wrapper for WPILib's XboxController class. Importantly, it adds the ability
 * to retrieve a WPILib Button.
 * 
 * Utilized by instiating a new ControllerWrapper rather than an XboxController.
 * Any XboxController-specific submethods can be retrieved by
 * <code>driver.getController().getRawAxis(1)</code> or the like.
 * Buttons/D-pad directions are referenced using <code>driver.bA</code> or
 * <code>driver.dNE</code>, for example.
 */
public class ControllerWrapper {
    private XboxController controller;
    public ButtonWrapper bA, bB, bX, bY, bRB, bLB, bRSTK, bLSTK, bSTART, bMENU; // read: "button Menu"
    public ButtonWrapper dN, dE, dS, dW, dNE, dNW, dSE, dSW; // read: "d-pad North"

    private final double RUMBLE_DELAY = 0.3;
    private final boolean DEADZONE_ENABLED;

    private Timer timer; // used for rumble timing
    private double deadzone = 0.1;

    public ControllerWrapper(int port, boolean deadzone) {
        this.controller = new XboxController(port);
        this.DEADZONE_ENABLED = deadzone;

        // face buttons
        bA = new ButtonWrapper(this.controller, RobotMap.Controllers.A);
        bB = new ButtonWrapper(this.controller, RobotMap.Controllers.B);
        bX = new ButtonWrapper(this.controller, RobotMap.Controllers.X);
        bY = new ButtonWrapper(this.controller, RobotMap.Controllers.Y);
        bSTART = new ButtonWrapper(this.controller, RobotMap.Controllers.START);
        bMENU = new ButtonWrapper(this.controller, RobotMap.Controllers.MENU);

        // misc buttons
        bRB = new ButtonWrapper(this.controller, RobotMap.Controllers.RB);
        bLB = new ButtonWrapper(this.controller, RobotMap.Controllers.LB);
        bRSTK = new ButtonWrapper(this.controller, RobotMap.Controllers.RSTK);
        bLSTK = new ButtonWrapper(this.controller, RobotMap.Controllers.LSTK);

        // d-pad/"POV" buttons
        // reads angle value of the combined d-pad
        dN = new ButtonWrapper(this.controller, 0, RobotMap.Controllers.POV);
        dNE = new ButtonWrapper(this.controller, 45, RobotMap.Controllers.POV);
        dE = new ButtonWrapper(this.controller, 90, RobotMap.Controllers.POV);
        dSE = new ButtonWrapper(this.controller, 135, RobotMap.Controllers.POV);
        dS = new ButtonWrapper(this.controller, 180, RobotMap.Controllers.POV);
        dSW = new ButtonWrapper(this.controller, 225, RobotMap.Controllers.POV);
        dW = new ButtonWrapper(this.controller, 270, RobotMap.Controllers.POV);
        dNW = new ButtonWrapper(this.controller, 315, RobotMap.Controllers.POV);
    }

    /**
     * Grants access to the base XboxController, for additional methods.
     * 
     * @return an XboxController object
     */
    public XboxController getController() {
        return this.controller;
    }

    /**
     * Returns the left stick's X-axis value.
     */
    public double getLX() {
        double lx = this.controller.getRawAxis(RobotMap.Controllers.LX);
        if (DEADZONE_ENABLED) {
            return Helper.deadzone(lx, this.getDeadzone());
        }
        return lx;
    }

    /**
     * Returns the left stick's Y-axis value.
     */
    public double getLY() {
        double ly = this.controller.getRawAxis(RobotMap.Controllers.LY);
        if (DEADZONE_ENABLED) {
            return Helper.deadzone(ly, this.getDeadzone());
        }
        return ly;
    }

    /**
     * Returns the right stick's X-axis value.
     */
    public double getRX() {
        double rx = this.controller.getRawAxis(RobotMap.Controllers.RX);
        if (DEADZONE_ENABLED) {
            return Helper.deadzone(rx, this.getDeadzone());
        }
        return rx;
    }

    /**
     * Returns the right stick's Y-axis value.
     */
    public double getRY() {
        double ry = this.controller.getRawAxis(RobotMap.Controllers.RY);
        if (DEADZONE_ENABLED) {
            return Helper.deadzone(ry, this.getDeadzone());
        }
        return ry;
    }

    /**
     * Returns the left trigger's value.
     */
    public double getLT() {
        return this.controller.getRawAxis(RobotMap.Controllers.LT);
    }

    /**
     * Returns the right trigger's value.
     */
    public double getRT() {
        return this.controller.getRawAxis(RobotMap.Controllers.RT);
    }

    /**
     * Sets the controller's deadzone.
     * 
     * @param deadzone the deadzone (for both sides of the axis) to set
     */
    public void setDeadzone(double deadzone) {
        this.deadzone = deadzone;
    }

    /**
     * Gets the controller's deadzone.
     */
    public double getDeadzone() {
        return this.deadzone;
    }

    /**
     * Rumbles the controller for a given amount of time.
     * NEEDS TESTING.
     */
    public void rumble() {
        timer.reset();
        timer.start();

        this.controller.setRumble(RumbleType.kLeftRumble, 1);
        this.controller.setRumble(RumbleType.kRightRumble, 1);
        while (timer.get() < RUMBLE_DELAY) {
        } // idle
        this.controller.setRumble(RumbleType.kLeftRumble, 0);
        this.controller.setRumble(RumbleType.kRightRumble, 0);

        timer.stop();
    }
}
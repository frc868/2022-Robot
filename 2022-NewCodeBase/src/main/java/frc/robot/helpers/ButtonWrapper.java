package frc.robot.helpers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


/**
 * A wrapper for the WPILib Button class, to be used with ControllerWrapper.
 * Allows Runnables to be used rather than commands.
 */
public class ButtonWrapper extends Trigger {
    private final XboxController controller;
    private final int id; // either contains the button ID or the POV number
    private final int angle; // only used if there's a POV button
    private final boolean isPOV; // determines if the button's a POV button

    private boolean toggleState = false; // whether the button state has changed
    private boolean lastState = false; // previous state of the button

    public ButtonWrapper(XboxController controller, int id) {
        this.controller = controller;
        this.id = id;
        this.angle = 0; // not a POV button
        this.isPOV = false; // still not a POV button
    }

    /**
     * Only use this constructor if you're setting a POV button.
     * You're *probably* working on POV 0.
     */
    public ButtonWrapper(XboxController controller, int angle, int povNumber) {
        this.isPOV = true;
        this.controller = controller;
        this.id = povNumber;
        this.angle = angle;
    }

    /**
     * Retrieves the state of the button.
     * Required to extend the Button class.
     * @return button state as a boolean
     */
    public boolean get() {
        if (isPOV) {
            return this.controller.getPOV(this.id) == this.angle;
        }
        return this.controller.getRawButton(this.id);
    }

    /**
     * Runs "something" on the rising edge of a button press.
     * @param func a lambda representing the action
     */
    public void whenPressed(Runnable func) {
        if (this.get() && !lastState) { // button is true, lastState is false
            // toggle the state if the state is toggled
            toggleState = !toggleState;
        }

        // store the previous button state
        lastState = this.get();

        // if the rising edge of the button is hit, run the Runnable
        if (toggleState) {
            func.run();
            toggleState = !toggleState;
        }
    }

    /**
     * Runs "something" on the falling edge of a button press.
     * @param func a lambda representing the action
     */
    public void whenReleased(Runnable func) {
        if (!this.get() && lastState) { // button is false, lastState is true
            // the first rule of tautology club is the fundamental law of tautology club
            toggleState = !toggleState;
        }

        // store the previous button state
        lastState = this.get();

        if (toggleState) {
            func.run();
            toggleState = !toggleState;
        }
    }

    /**
     * Runs "something" while the button is held.
     * This shouldn't conflict with whenPressed()'s implementation, as we shouldn't
     * be binding whenPressed and whileHeld to the same button ever.
     * @param func a lambda representing the action
     */
    public void whileHeld(Runnable func) {
        if (this.get()) {
            func.run();
        }
    }

    public void setToggleState(boolean toggleState) {
        this.toggleState = toggleState;
    }

    public void setLastState(boolean lastState) {
        this.lastState = lastState;
    }

    public boolean getToggleState() {
        return this.toggleState;
    }

    public boolean getLastState() {
        return this.lastState;
    }
}

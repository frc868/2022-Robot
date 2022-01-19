package frc.robot.helpers;

/**
 * The helper class. Defines various functions used throughout the codebase.
 * These usually tend to be trivial math functions that we don't want to copy into each subsystem.
 * 
 * @author lm
 * @author ic
 */
public class Helper {
    /**
     * Given a value, checks to makes sure it is between min and max. If not replace with the value
     *  of min or max. If so return value
     * @param value The value to check
     * @param min The minimum acceptable value
     * @param max The maximum acceptable value
     * @return The sanity value after check
     * @author lm
     * @author ic
     */
    public static double boundValue(double value, double min, double max) {
        if (value > max) {
            return max;
        }
        if (value < min) {
            return min;
        }
        return value;
    }
    /**
     * Given a value, checks to makes sure it is between -1.0 and 1.0. If not replace with the 
     * value with -1.0 or 1.0. If so return value
     * @param value The value to check
     * @return The sanity value after check
     * @author lm
     * @author ic
     */
    public static double boundValue(double value) {
        return boundValue(value, -1, 1);
    }

    /**
     * Creates a virtual deadzone. 
     * If a value is between the lower max and upper min, it will output zero. 
     * If a value is outside this range, the original value will be outputted.
     * @param value value to deadzone
     * @param size the size of the deadzone
     * @return the "deadzoned" version of the input value
     */
    public static double deadzone(double value, double size) {
        if (Math.abs(value) < Math.abs(size)) {
            return 0;
        } else {
            return value;
        }
    }

    /**
     * Creates a virtual deadzone on a specific controller.
     * @param value value to deadzone
     * @param controller the controller to set the deadzone on
     * @return the "deadzoned" version of the input value
     * @author hrl
     */
    /*public static double deadzone(double value, ControllerWrapper controller) {
        return deadzone(value, controller.getDeadzone());
    }*/

    /**
     * Takes the value and returns the value to the third power
     * @param value value to be cubed
     * @return the third power of value
     * @author SV
     * @author JW
     */
    public static double desensitize(double value) {
        return Math.pow(value, 3);
    } 
}

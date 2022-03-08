package frc.robot.auton;

/**
 * Defines the base of an AutonPath enum in the state machine.
 */
public abstract class AutonPath {
    public abstract void run();

    public abstract void reset();

    @Override
    public abstract String toString();
}

package frc.robot.auton;

public abstract class AutonPath {
    public abstract void run();
    public abstract void reset();
    @Override
    public abstract String toString();
}
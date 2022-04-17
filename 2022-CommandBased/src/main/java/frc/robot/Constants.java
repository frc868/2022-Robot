// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class Drivetrain {
        public static final class CANIDs {
            public static final int L_PRIMARY = 4;
            public static final int L_SECONDARY = 6;
            public static final int R_PRIMARY = 1;
            public static final int R_SECONDARY = 3;
        }

        public static final boolean IS_LEFT_INVERTED = true;
        public static final boolean IS_RIGHT_INVERTED = false;
        public static final double ENCODER_DISTANCE_PER_PULSE = 1.0;

        public static final class DriveStraightPID {
            public static final int kP = 1;
            public static final int kI = 0;
            public static final int kD = 0;
        }

        public static final class TurnToAngleGyroPID {
            public static final int kP = 1;
            public static final int kI = 0;
            public static final int kD = 0;
        }

        public static final class TurnToBallPID {
            public static final int kP = 1;
            public static final int kI = 0;
            public static final int kD = 0;
        }

        public static final class TurnToGoalPID {
            public static final int kP = 1;
            public static final int kI = 0;
            public static final int kD = 0;
        }
    }

    public static final class OI {
        public static final int DRIVER_PORT = 0;
        public static final int OPERATOR_PORT = 1;
    }

    public static final class Hopper {
        public static final class CANIDs {
            public static final int H_PRIMARY = 0;
        }

        public static final int GATEKEEPER_CHANNEL1 = 0;
        public static final int GATEKEEPER_CHANNEL2 = 0;

        public static final boolean IS_INVERTED = false;
    }

    public static final class Intake {
        public static final class CANIDs {
            public static final int I_PRIMARY = 0;
        }

        public static final int INTAKE_CHANNEL1 = 0;
        public static final int INTAKE_CHANNEL2 = 0;

        public static final boolean IS_INVERTED = false;
    }

    public static final class Shooter {
        public static final class CANIDs {
            public static final int S_PRIMARY = 0;
            public static final int S_SECONDARY = 0;
        }

        public static final boolean IS_INVERTED = false;
        public static final int kP = 1;
        public static final int kI = 0;
        public static final int kD = 0;
    }

    public static final class Limelight {

        // Goal and angle measurements
        public static final double LL_HEIGHT = 22.0;
        public static final double HUB_HEIGHT = 101.0;
        public static final double ANGLE = 29.6375;

        // Good shot distances
        public static final double HIGH_GOAL_SHOT_DISTANCE = 6.75;
        public static final double LOW_GOAL_SHOT_DISTANCE = 0.0; // untested
    }

    public static final class Climber {

        // CAN IDs
        public static final int C_PRIMARY = 12;
        public static final int C_SECONDARY = 9;

        // Inverse logic
        public static final boolean IS_INVERTED = false;

        // Solenoid logic
        public static final int CLIMBER_EXTEND_CHANNEL1 = 2;
        public static final int CLIMBER_EXTEND_CHANNEL2 = 5;
        public static final int CLIMBER_LOCK_CHANNEL1 = 3;
        public static final int CLIMBER_LOCK_CHANNEL2 = 4;

    }
}

package frc.robot;

public class RobotMap {
    public static final boolean HIGH_GOAL_MODE = true;

    public static class Subsystems {
        public static class Drivetrain {

            // CAN IDs
            public static final int R_PRIMARY = 1;
            public static final int R_SECONDARY = 3;
            public static final int L_PRIMARY = 4;
            public static final int L_SECONDARY = 6;

            // Inverse logic
            public static final boolean RIGHT_IS_INVERTED = false;
            public static final boolean LEFT_IS_INVERTED = true;

            // Max accleration (time in seconds to go from zero to full throttle)
            public static final double MAX_ACCEL_RATE = 0.3;

        }

        public static class Gyro {

            // SPI port that the gyro is on
            public static final int PORT = 0;

        }

        public static class Hopper {

            // CAN IDs
            public static final int H_MOTOR = 7;

            // Inverse logic
            public static final boolean IS_INVERTED = false;

            // Solenoid logic
            public static final int GATEKEEPER_CHANNEL1 = 1;
            public static final int GATEKEEPER_CHANNEL2 = 6;

        }

        public static class Intake {

            // CAN IDs
            public static final int I_PRIMARY = 10;
            public static final int I_SECONDARY = 11;

            // Inverse logic
            public static final boolean IS_INVERTED = true;

            // Solenoid logic
            public static final int INTAKE_SOLENOID_CHANNEL1 = 0;
            public static final int INTAKE_SOLENOID_CHANNEL2 = 7;

        }

        public static class Climber {

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

        public static class Shooter {

            // CAN IDs
            public static final int S_PRIMARY = 8;
            public static final int S_SECONDARY = 13;

            // Inverse logic
            public static final boolean IS_INVERTED = true;

            // Good shot RPMs
            public static final double HIGH_GOAL_RPM = 2800.0;
            public static final double LOW_GOAL_RPM = 2150.0;

        }

        public static class Limelight {

            // Goal and angle measurements
            public static final double LL_HEIGHT = 22.0;
            public static final double HUB_HEIGHT = 101.0;
            public static final double ANGLE = 29.6375;

            // Good shot distances
            public static final double HIGH_GOAL_SHOT_DISTANCE = 6.75;
            public static final double LOW_GOAL_SHOT_DISTANCE = 0.0; // untested
        }
    }

    public static class Controllers {

        // USB port order
        public static final int DRIVER_PORT = 0;
        public static final int OPERATOR_PORT = 1;

        // Button index layout
        public static final int A = 1;
        public static final int B = 2;
        public static final int X = 3;
        public static final int Y = 4;
        public static final int RB = 6;
        public static final int LB = 5;
        public static final int RSTK = 10;
        public static final int LSTK = 9;
        public static final int START = 8;
        public static final int MENU = 7;

        // Analog joystick and trigger layout
        public static final int LX = 0;
        public static final int LY = 1; // Arcade drive
        public static final int RX = 4; // Arcade drive
        public static final int RY = 5;
        public static final int LT = 2;
        public static final int RT = 3;

        public static final int POV = 0; // untested

    }

    public static class PIDConstants {

        // PID constansts for climber
        public static class Climber {
            public static final double KP = 0.01;
            public static final double KI = 0;
            public static final double KD = 0;
        }

        // PID constants for shooter
        public static class Shooter {
            public static final double KP = 0.00055;
            public static final double KI = 0.0023;
            public static final double KD = 0.00005;
        }

        public static class Drivetrain {

            /**
             * Tuning data
             * Date:
             * Location:
             * Resting voltage:
             * Working voltage:
             * Notes: This will be retuned this week.
             */
            public static class TurnToLimelight {
                public static final double KP = 0.023;
                public static final double KI = 0.0090;
                public static final double KD = 0.0006;
            }

            /**
             * Tuning data
             * Date:
             * Location:
             * Resting voltage:
             * Working voltage:
             * Notes: This will be re-tuned this week.
             */
            public static class DriveToLimelight {
                public static final double KP = 0.08;
                public static final double KI = 0.005;
                public static final double KD = 0;
            }

            /**
             * Tuning data
             * Date:
             * Location:
             * Resting voltage:
             * Working voltage:
             * Notes: This will be re-tuned this week.
             */
            public static class TurnToAstra {
                public static final double KP = 0.020;
                public static final double KI = 0.04;
                public static final double KD = 0.0045;
            }

            /**
             * Tuning data
             * Date:
             * Location:
             * Resting voltage:
             * Working voltage:
             * Notes: This will be re-tuned this week.
             */
            public static class DriveToAstra {
                public static final double KP = 0.08;
                public static final double KI = 0;
                public static final double KD = 0;
            }

            /**
             * Tuning data
             * Date:
             * Location:
             * Resting voltage:
             * Working voltage:
             * Notes: This will be re-tuned this week.
             */
            public static class RightDrivetrain {
                public static final double KP = 0.08;
                public static final double KI = 0;
                public static final double KD = 0;
            }

            /**
             * Tuning data
             * Date:
             * Location:
             * Resting voltage:
             * Working voltage:
             * Notes: This will be re-tuned this week.
             */
            public static class LeftDrivetrain {
                public static final double KP = 0.04;
                public static final double KI = 0;
                public static final double KD = 0;
            }

            /**
             * Tuning data
             * Date:
             * Location:
             * Resting voltage:
             * Working voltage:
             * Notes: This will be re-tuned this week.
             */
            public static class DriveStraight {
                public static final double KP = 0.01;
                public static final double KI = 0;
                public static final double KD = 0;
            }

            /**
             * Tuning data
             * Date:
             * Location:
             * Resting voltage:
             * Working voltage:
             * Notes: This will be re-tuned this week.
             */
            public static class TurnToAngleGyro {
                public static final double KP = 0.01;
                public static final double KI = 0;
                public static final double KD = 0;
            }
        }
    }

}

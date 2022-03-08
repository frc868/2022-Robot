package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

public class RobotMap {
    public static class SUBSYSTEMS {
        public static class DRIVETRAIN {

            // CAN IDs
            public static final int R_PRIMARY = 1;
            public static final int R_SECONDARY = 3;
            public static final int L_PRIMARY = 4;
            public static final int L_SECONDARY = 6;

            // Inverse logic
            public static final boolean RIGHT_IS_INVERTED = false;
            public static final boolean LEFT_IS_INVERTED = true;

            // Max accleration
            public static final double TIME = 1.5;

        }

        public static class GYRO {

            // SPI port logic
            public static final Port GYRO = SPI.Port.kMXP;

        }

        public static class HOPPER {

            // CAN IDs
            public static final int HOPPER = 7;

            // Inverse logic
            public static final boolean IS_INVERTED = false;

            // Solenoid logic
            public static final int GATEKEEPER_CHANNEL1 = 1;
            public static final int GATEKEEPER_CHANNEL2 = 6;

        }

        public static class INTAKE {

            // CAN IDs
            public static final int I_PRIMARY = 10;
            public static final int I_SECONDARY = 11;

            // Inverse logic
            public static final boolean IS_INVERTED = true;

            // Solenoid logic
            public static final int INTAKE_SOLENOID_CHANNEL1 = 0;
            public static final int INTAKE_SOLENOID_CHANNEL2 = 7;

        }

        public static class CLIMBER {

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

        public static class SHOOTER {

            // CAN IDs
            public static final int S_PRIMARY = 8;
            public static final int S_SECONDARY = 13;

            // Inverse logic
            public static final boolean IS_INVERTED = true;

        }

        public static class LIMELIGHT {

            // Goal and angle measurements
            public static final double LL_HEIGHT = 22.0;
            public static final double HUB_HEIGHT = 101.0;
            public static final double ANGLE = 25.0;

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

    public static class PID_CONSTANTS {

        // PID constansts for climber
        public static class CLIMBER {

            public static final double KP = 0.01;
            public static final double KI = 0;
            public static final double KD = 0;

        }

        // PID constants for shooter
        public static class SHOOTER {

            public static final double KP = 0.00055;
            public static final double KI = 0.0023;
            public static final double KD = 0.00005;

        }

        public static class DRIVETRAIN {

            // PID constants for turning to limelight
            public static class TURN_TO_LIMELIGHT_PID {

                public static final double KP = 0.022;
                public static final double KI = 0.00;
                public static final double KD = 0.0006;

            }

            // PID constants for driving to limelight
            public static class DRIVE_TO_LIMELIGHT_PID {

                public static final double KP = 0.08;
                public static final double KI = 0;
                public static final double KD = 0;

            }

            // PID constants for driving right side to position
            public static class RIGHT_SIDE_PID {

                public static final double KP = 0.01;
                public static final double KI = 0;
                public static final double KD = 0;

            }

            // PID constants for driving left side to position
            public static class LEFT_SIDE_PID {

                public static final double KP = 0.01;
                public static final double KI = 0;
                public static final double KD = 0;

            }
        }
    }

}

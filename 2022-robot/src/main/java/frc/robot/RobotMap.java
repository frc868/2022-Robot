package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

public class RobotMap {
    public static class Drivetrain{
        public static final int R_PRIMARY = 4;
        public static final int R_SECONDARY = 5;
        public static final int R_TERTIARY = 6;
        public static final int L_PRIMARY = 1;
        public static final int L_SECONDARY = 2;
        public static final int L_TERTIARY = 3;
        public static final boolean RIGHT_IS_INVERTED = false;
        public static final boolean LEFT_IS_INVERTED = true;
    }
    public static class Gyro{
        public static final Port GYRO = SPI.Port.kMXP;
    }
    public static class Hopper{
        public static final int HOPPER = 7;
        public static final boolean IS_INVERTED = false;
    }
    public static class Intake{
        public static final int INTAKE = 10;
        public static final boolean IS_INVERTED = false;
        public static final int UPDOWNER11 = 1;
        public static final int UPDOWNER12 = 2;
        public static final int UPDOWNER21 = 3;
        public static final int UPDOWNER22 = 4;
    }
    public static class Climber{
        public static final int C_PRIMARY = 8;
        public static final int C_SECONDARY = 9;
        public static final boolean IS_INVERTED = false;
    }
    public static class Shooter{
        public static final int S_PRIMARY = 11;
        public static final int S_SECONDARY = 12;
        public static final boolean IS_INVERTED = false;
    }
    public static class Controllers {
       
        public static final int DRIVER_PORT = 0;
        public static final int OPERATOR_PORT = 1;

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

        public static final int LX = 0;
        public static final int LY = 1; // Arcade drive
        public static final int RX = 4; // Arcade drive
       

        public static final int RY = 5;
        public static final int LT = 2;
        public static final int RT = 3;

        public static final int POV = 0; // untested
    }
}

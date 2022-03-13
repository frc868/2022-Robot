package frc.robot.sensors;

import java.math.BigInteger;

import edu.wpi.first.wpilibj.I2C;

public class PressureSensor {
    private I2C pressureSensor;
    private byte[] array = new byte[4];
    public static PressureSensor instance;

    private PressureSensor() {
        pressureSensor = new I2C(I2C.Port.kOnboard, 9);
    }

    public static PressureSensor getInstance() {
        if (instance == null) {
            instance = new PressureSensor();
        }
        return instance;
    }

    public void readValue() {
        pressureSensor.read(9, 4, array);
    }

    // Used for troubleshooting
    public void writeValue() {
        pressureSensor.write(9, 1);
    }

    /**
     * Gets the pressure of a ball in the hopper.
     * 
     * @return
     */
    public double getPressure() {
        readValue();
        long value = new BigInteger(array).longValue();
        return value;
    }
}

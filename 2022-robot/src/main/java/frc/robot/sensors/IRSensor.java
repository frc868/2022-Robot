package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class IRSensor {
    private DigitalInput irSensor;
    public static IRSensor instance;

    private IRSensor() {
        irSensor = new DigitalInput(0);
    }


    public static IRSensor getInstance(){
        if(instance == null){
            instance = new IRSensor();
        }
        return instance;
    }

    public void printValue() {
        System.out.println(irSensor.get());
    }
}

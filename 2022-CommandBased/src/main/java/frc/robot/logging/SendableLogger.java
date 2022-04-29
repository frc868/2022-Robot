package frc.robot.logging;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SendableLogger extends Logger<Sendable> {
    private String key;
    private Sendable sendable;

    public SendableLogger(String subsystem, String key, Sendable sendable) {
        super(sendable, subsystem);
        this.key = key;
        this.sendable = sendable;
    }

    @Override
    public void run() {
        SmartDashboard.putData(subsystem + "/" + key, sendable);
    }

}

package frc.robot.logging;

public class LogGroup {
    private Logger<?>[] loggers;

    public LogGroup(Logger<?>[] loggers) {
        this.loggers = loggers;
    }

    public void run() {
        for (Logger<?> logger : loggers) {
            logger.run();
        }
    }
}

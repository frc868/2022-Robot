package frc.robot.logging;

import java.util.concurrent.Callable;

public class LogItem<T> {

    private LogType type;
    private String key;
    private Callable<T> func;

    public LogItem(LogType type, String key, Callable<T> func) {
        this.type = type;
        this.key = key;
        this.func = func;
    }

    public LogType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public Callable<T> getFunc() {
        return func;
    }
}

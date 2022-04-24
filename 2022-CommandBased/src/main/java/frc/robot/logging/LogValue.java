package frc.robot.logging;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import edu.wpi.first.util.sendable.Sendable;

public class LogValue<K> {

    private Class<K> type;
    private String key;
    private Callable<K> func;
    // private LogType type;

    public LogValue(Class<K> type, String key, Callable<K> func) {
        this.type = type;
        this.key = key;
        this.func = func;
        // this.type = type;
    }

    public String getKey() {
        return key;
    }

    public Callable<K> getFunc() {
        return func;
    }

    public Class<K> getType() {
        return type;
    }

    // public K getType() {
    // return type;
    // }
}

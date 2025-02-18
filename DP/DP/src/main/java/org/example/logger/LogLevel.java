package org.example.logger;

public enum LogLevel {
    DEBUG(1),
    INFO(2),
    WARN(4),
    ERROR(3),
    FATAL(5);

    private final int level;

    LogLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }
}

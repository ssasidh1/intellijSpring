package org.example.logger;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static Logger logger;
    private final Formatter formatter;
    private final List<Appenders> appenders;

    private LogLevel curentLevel;
    private Logger() {
        this.curentLevel = LogLevel.INFO;
        this.appenders = new ArrayList<>();
        this.formatter = new SimpleFormatterImpl();
    }

    public static synchronized Logger getLogger() {
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void addAppender(Appenders appender) {
        this.appenders.add(appender);
    }
    public static Logger getInstance(){
        if(logger == null){
            synchronized(Logger.class){
                if(logger == null){
                    logger = new Logger();
                }
            }
        }
        return logger;
    }

    public void setLevel(LogLevel level){
        this.curentLevel = level;
    }

    public void debug(String msg){
        log(LogLevel.DEBUG,msg);
    }

    public void warn(String msg){
        log(LogLevel.WARN,msg);
    }
    public void info(String msg){
        log(LogLevel.INFO,msg);
    }

    public void log(LogLevel level, String msg){
        if(level.getLevel() >= curentLevel.getLevel()){
            String formattedMsg = formatter.format(level, msg);
            for(Appenders appender : appenders){
                appender.Append(formattedMsg);
            }
        }
    }

}

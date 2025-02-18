package org.example.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleFormatterImpl implements Formatter {

    private final SimpleDateFormat dateFormat;

    public SimpleFormatterImpl() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }
    @Override
    public String format(LogLevel level, String message) {
       String timeStamp = dateFormat.format(new Date());
       return String.format("[%s] [%s] %s",timeStamp,level.name(),message);
    }
}

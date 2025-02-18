package org.example.logger;

public interface Formatter {
    String format(LogLevel level, String message);
}

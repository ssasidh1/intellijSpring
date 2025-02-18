package org.example.logger;

public class ConsoleAppendersImpl implements Appenders {

    public ConsoleAppendersImpl() {

    }

    @Override
    public void Append(String message) {
        System.out.println(message);
    }
}

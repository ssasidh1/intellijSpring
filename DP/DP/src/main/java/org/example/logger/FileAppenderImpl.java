package org.example.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppenderImpl implements Appenders {

    private final String filePath;
    private BufferedWriter writer;
    public FileAppenderImpl(String filePath) {
        this.filePath = filePath;
        try{
            this.writer = new BufferedWriter(new FileWriter(this.filePath,true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void Append(String message) {
        try{
            writer.write(message);
            writer.newLine();
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}

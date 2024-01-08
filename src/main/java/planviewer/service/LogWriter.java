package planviewer.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class LogWriter {
    public static void writeLog(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log/log.txt", true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

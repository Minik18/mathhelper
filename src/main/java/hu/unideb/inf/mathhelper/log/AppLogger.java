package hu.unideb.inf.mathhelper.log;

import java.io.*;
import java.time.LocalDateTime;

public class AppLogger {

    private static final File LOG_FILE = new File("./data/lastLog.log");

    public static void logError(Exception e) {
        if (!LOG_FILE.exists()) {
            write(e.getMessage());
        } else {
            if (LOG_FILE.length() != 0) {    //Not Empty, so make it empty
                try {
                    PrintWriter writer = new PrintWriter(LOG_FILE);
                    writer.print("");
                    writer.close();
                    write(e.getMessage());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            } else {
                write(e.getMessage());
            }
        }
    }

    private static void write(String message) {
        try {
            String date = LocalDateTime.now().toString();
            FileWriter out = new FileWriter(LOG_FILE, true);
            out.write(date + " - " + message);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

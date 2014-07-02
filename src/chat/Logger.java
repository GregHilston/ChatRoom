package chat;

import java.io.*;
import java.util.Calendar;

public class Logger {
    private static Logger logger = new Logger();
    File file = new File(getFileName());

    private Logger() {
        // Singleton
    }


    public static Logger getInstance() {
        return logger;
    }

    public void createLogFile() {
        // If directory doesn't exist, then create it
        file.getParentFile().mkdirs();

        // If file doesn't exist, then create it
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        log("Server Started on port " + ServerInfo.getInstance().getPortNumber());
    }

    public static String getFileName() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH); // Note: zero based!
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return  "Logs\\" + month + "_" + day + "_" + year + ".log";
    }


    public String getTimeStamp() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        int millis = Calendar.getInstance().get(Calendar.MILLISECOND);

        return "(" + hour + ":" + minute + ":" + second + ":" + millis + ") ";
    }


    public void log(String message) {
        try {
            FileWriter fileWritter = new FileWriter(file, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(getTimeStamp() + message + "\n");
            bufferWritter.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

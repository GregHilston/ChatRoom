package chat;

import java.io.*;
import java.util.Calendar;

public class Logger {
    private static Logger logger = new Logger();
    private File dir = new File("Logs");
    private File file = new File(getFileName());


    /***
     * Hidden, to force calling of getInstance
     */
    private Logger() {
        // Singleton
    }


    /***
     * Singleton instance getter
     *
     * @return      The one instance of the Logger
     */
    public static Logger getInstance() {
        return logger;
    }

    /***
     * Creates the "Logs" directory if does not already exist and creates the log file for this server's lifetime.
     * If the server already ran on this day, the new logfile will just append to the first one.
     */
    protected void createLogFile() {
        // If directory doesn't exist, then create it
        if(!dir.exists()) {
            dir.mkdir();
        }

        // If file doesn't exist, then create it
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        log("ServerApp started on " + ServerInfo.getInstance().getLocalIpAddress() + " listening on port: " + ServerInfo.getInstance().getPortNumber());
    }

    /***
     * Returns the filename to be used for this Server's log file
     *
     * @return  filename
     */
    private String getFileName() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH); // Note: zero based!
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return dir.toString() + "/" + month + "_" + day + "_" + year + ".log";
    }


    /***
     * Gets the current time
     *
     * @return  Time in the format (hh:mm:ss:msms)
     */
    private String getTimeStamp() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        int millis = Calendar.getInstance().get(Calendar.MILLISECOND);

        return "(" + hour + ":" + minute + ":" + second + ":" + millis + ") ";
    }


    /***
     * Writes the inputted message to the logfile
     *
     * @param message   Message to be logged
     */
    protected void log(String message) {
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

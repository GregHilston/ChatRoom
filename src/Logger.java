import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class responsible for logging events and ChatMessages
 */
public class Logger {
    private static File dir = new File("logs");
    private static File file = new File(new SimpleDateFormat("MM_dd_yyyy").format(new Date()) + ".log");
    private static File fullPathToNewFile = new File(dir.toString() + "/" + file.toString());

    /**
     * Creates the "Logs" directory if does not already exist and creates the log file for this server's lifetime.
     * If the server already ran on this day, the new logfile will just append to the first one.
     */
    public static void createLogFile() {
        if (dir.exists() || dir.mkdir()) { // If directory doesn't exist, then create it
            if (!fullPathToNewFile.exists()) { // If file doesn't exist, then create it
                try {
                    fullPathToNewFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Writes the server string to the log file, prefaced with the current time
     *
     * @param message   the string to be printed to the log file
     */
    public static void logString(String message) {
        // append to the end of the file (time): [message]
        try {
            FileWriter fileWritter = new FileWriter(fullPathToNewFile, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

            bufferWritter.write(getCurrentTimeStamp() + message);
            bufferWritter.newLine();
            bufferWritter.close();
            System.out.println(getCurrentTimeStamp() + message);
        } catch (FileNotFoundException e) { // Missing logs folder
            Logger.createLogFile();
            System.err.println("Warning: log folder was missing, creating folder now");
            Logger.logString(message); // Recall the function, as the folder has been created
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the user message to the log file, prefaced with the current time
     *
     * @param message   the message to be printed to the log file
     */
    public static void logMessage(ChatMessage message) {
        // append to the end of the file (time): [message]
        try {
            FileWriter fileWritter = new FileWriter(fullPathToNewFile, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

            bufferWritter.write(getCurrentTimeStamp() + message);
            bufferWritter.newLine();
            bufferWritter.close();
            System.out.println(getCurrentTimeStamp() + message);
        } catch (FileNotFoundException e) { // Missing logs folder
            Logger.createLogFile();
            System.err.println("Warning: log folder was missing, creating folder now");
            Logger.logMessage(message); // Recall the function, as the folder has been created
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the current time as a timestamp for logging and printing
     *
     * @return timestamp    the current time
     */
    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("h:mm:ss a ").format(new Date());
    }

    /**
     * Returns the filename to be used for this Server's log file
     *
     * @return filename the name of the file being written to
     */
    public static String getFileName() {
        return file.getName();
    }
}

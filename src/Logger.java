import java.io.*;
import java.util.Calendar;

/***
 * Create a log file called [mm-dd-yyyy].log in the folder logs
 */

public class Logger {
    private static File dir = new File("logs");
    private static File file = new File(getFileName());
    private static File fullPathToNewFile = new File(dir.toString() + "/" + file.toString());


    /***
     * Creates the "Logs" directory if does not already exist and creates the log file for this server's lifetime.
     * If the server already ran on this day, the new logfile will just append to the first one.
     */
    public static void createLogFile() {
        // If directory doesn't exist, then create it
        if(!dir.exists()) {
            dir.mkdir();
        }

        // If file doesn't exist, then create it
        if(!fullPathToNewFile.exists()){
            try {
                fullPathToNewFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    /***
     * Writes the message to the log file, prefaced with the current time
     *
     * @param message   the message to be printed to the log file
     */
    public static void writeMessage(String message) {
        // append to the end of the file (time): [message]
        try {
            FileWriter fileWritter = new FileWriter(fullPathToNewFile, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(message + "\n");
            System.out.println(message); // TODO: remove this for final build
            bufferWritter.close();
        }
        catch(FileNotFoundException e) { // Missing logs folder
            createLogFile();
            writeMessage("Warning: log folder was missing, creating folder now");
            writeMessage(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***
     * Returns the filename to be used for this Server's log file
     *
     * @return  filename
     */
    public static String getFileName() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH); // Note: zero based!
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return month + "_" + day + "_" + year + ".log";
    }
}

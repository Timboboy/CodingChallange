import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MagnificentMonitoringTool {
    public static void main(String[] args) {
        FileHandler fileHandler = null;
        try {
            if(args.length == 1 && args[0].equals("help") || args.length != 4) {
                System.out.println("MonitoringTool - Required parameters: URL INTERVAL TIMEOUT PATH/TO/DIRECTORY/FOR/LOGFILES/");
                return;
            }

            /* I could have used a properties file to configure the logger, but I decided against since I wanted to accept the log file path as parameter*/
            var logger = Logger.getLogger("MagnificentMonitoringTool");
            var formatter = DateTimeFormatter.ofPattern("yyy-MM-dd-HH-mm-ss");
            fileHandler = new FileHandler(args[3] + formatter.format(LocalDateTime.now()) + "_logfile.log");
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());

            var tester = new ConnectionTester(new URL(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), logger);

            while (true) {
                tester.testConnection();
                tester.waitOnInterval();
            }
        } catch(IOException error) {
            error.printStackTrace();
        } finally {
            if (fileHandler != null) {
                fileHandler.close();
            }
        }
    }
}


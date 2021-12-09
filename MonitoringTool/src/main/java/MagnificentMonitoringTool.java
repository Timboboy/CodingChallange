import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MagnificentMonitoringTool {
    public static void main(String[] args) {
        if(args.length == 1 && args[0].equals("help") || args.length != 4) {
            System.out.println("MonitoringTool - Required parameters: URL INTERVAL TIMEOUT PATH/TO/PROPERTIES/FILE/FOR/LOGGER/");
            return;
        }

        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream(args[3]));
            var logger = Logger.getLogger(MagnificentMonitoringTool.class.getName());
            var tester = new ConnectionTester(new URL(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), logger);

            while (true) {
                tester.testConnection();
                tester.waitOnInterval();
            }
        } catch(IOException error) {
            error.printStackTrace();
        }
    }
}


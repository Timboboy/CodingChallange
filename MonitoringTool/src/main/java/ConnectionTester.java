import java.net.URL;
import java.util.logging.Logger;

public class ConnectionTester {
    private final HttpConnectionHandler connectionHandler;
    private final Logger logger;
    private long timeOfLastResponse = System.currentTimeMillis();
    private long successfulAttempts = 0;
    private long totalAttempts = 0;
    private final int testingInterval;
    private final int timeout;

    public ConnectionTester(URL url, int interval, int connectionTimeout, Logger out) {
        connectionHandler = new HttpConnectionHandler(url);
        testingInterval = interval;
        timeout = connectionTimeout;
        logger = out;
    }

    public void testConnection() {
        ++totalAttempts;
        String status = connectionHandler.connect();
        if (status.equals("ok")) {
            ++successfulAttempts;
            timeOfLastResponse = System.currentTimeMillis();
            logger.info("Connection attempt succeeded. Responded with: " + connectionHandler.request() + " " +
                    Long.toUnsignedString(successfulAttempts) + "/" + Long.toUnsignedString(totalAttempts) + " attempts succeeded.");
            connectionHandler.disconnect();
        } else if (System.currentTimeMillis() - timeOfLastResponse > timeout) {
            logger.severe("Connection timeout since " + Long.toUnsignedString(System.currentTimeMillis() - timeOfLastResponse) + " milliseconds");
        } else {
            logger.warning("Connection attempt failed. Response:" + status + " " +
                    Long.toUnsignedString(successfulAttempts) + "/" + Long.toUnsignedString(totalAttempts) + " attempts succeeded.");
        }
    }

    public void waitOnInterval() {
        try {
            Thread.sleep(testingInterval);
        } catch(InterruptedException error) {
            error.printStackTrace();
        }
    }
}

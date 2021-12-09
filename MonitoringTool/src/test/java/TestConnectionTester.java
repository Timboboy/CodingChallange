import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class TestConnectionTester {
    @Test
    public void testValidConnection() {
        try {
            var url = Mockito.mock(URL.class);
            var connection = Mockito.mock(HttpURLConnection.class);
            var logger = Mockito.mock(Logger.class);

            Mockito.when(url.openConnection()).thenReturn(connection);
            Mockito.when(connection.getResponseCode()).thenReturn(200);
            Mockito.when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("Hello World".getBytes()));

            var tester = new ConnectionTester(url, 100, 500, logger);
            tester.testConnection();
            Mockito.verify(logger).info("Connection attempt succeeded. Responded with: Hello World 1/1 attempts succeeded.");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @Test
    public void testInvalidConnection() {
        try {
            var logger = Mockito.mock(Logger.class);
            var tester = new ConnectionTester(new URL("http://theotherlocalhost:12345"), 100, 50000, logger);

            tester.testConnection();
            Mockito.verify(logger).warning("Connection attempt failed. Response:java.net.UnknownHostException: theotherlocalhost 0/1 attempts succeeded.");
        } catch (MalformedURLException error) {
            error.printStackTrace();
        }
    }

    @Test
    public void testConnectionTimeout() {
        try {
            var logger = Mockito.mock(Logger.class);
            var tester = new ConnectionTester(new URL("http://theotherlocalhost:12345"), 2000, 1200, logger);

            tester.testConnection();
            tester.waitOnInterval();
            tester.testConnection();

            Mockito.verify(logger).severe(Mockito.anyString());
        } catch (MalformedURLException error) {
            error.printStackTrace();
        }
    }
}

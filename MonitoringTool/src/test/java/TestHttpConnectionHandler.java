import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestHttpConnectionHandler {
    @Test
    public void connectToInvalidURL() {
        try {
            var handler = new HttpConnectionHandler(new URL("http://theotherlocalhost:12345"));
            Assert.assertNotEquals(handler.connect(), "ok");
        } catch (MalformedURLException error) {
            error.printStackTrace();
        }
    }

    @Test
    public void requestFromURL() {
        try {
            var handler = new HttpConnectionHandler(new URL("http://theotherlocalhost:12345"));
            Assert.assertNotEquals(handler.connect(), "ok");
            Assert.assertEquals(handler.request(), "No reader available for the current connection");
        } catch (MalformedURLException error) {
            error.printStackTrace();
        }
    }

    @Test
    public void connectToValidURL() {
        try {
            var url = Mockito.mock(URL.class);
            var connection = Mockito.mock(HttpURLConnection.class);

            Mockito.when(url.openConnection()).thenReturn(connection);
            Mockito.when(connection.getResponseCode()).thenReturn(200);
            Mockito.when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("Hello World".getBytes()));

            var handler = new HttpConnectionHandler(url);
            Assert.assertEquals(handler.connect(), "ok");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @Test
    public void readFromValidURL() {
        try {
            var url = Mockito.mock(URL.class);
            var connection = Mockito.mock(HttpURLConnection.class);

            Mockito.when(url.openConnection()).thenReturn(connection);
            Mockito.when(connection.getResponseCode()).thenReturn(200);
            Mockito.when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("Hello World".getBytes()));

            var handler = new HttpConnectionHandler(url);
            Assert.assertEquals(handler.connect(), "ok");
            Assert.assertEquals(handler.request(), "Hello World");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}

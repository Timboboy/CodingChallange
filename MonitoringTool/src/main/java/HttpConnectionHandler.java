import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionHandler {
    private HttpURLConnection connection = null;
    private BufferedReader reader = null;
    private final URL url;
    
    public String connect() {
        try {
            connection = (HttpURLConnection)url.openConnection();
            connection.setAllowUserInteraction(true);
            connection.setRequestMethod("GET");
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return "ok";
        } catch (IOException error) {
            return error.toString();
        }
    }

    public void disconnect() {
        if(connection != null) {
            connection.disconnect();
        }
    }

    public String request() {
        try {
            if(reader != null) {
                StringBuilder response = new StringBuilder();
                while(reader.ready()) {
                    response.append(reader.readLine());
                } return response.toString();
            } return "No reader available for the current connection";
        } catch(IOException error) {
            return error.toString();
        }
    }

    public HttpConnectionHandler(URL url) {
        this.url = url;
    }
}

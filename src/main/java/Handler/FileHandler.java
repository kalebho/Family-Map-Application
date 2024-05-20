package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //Be pessimistic
        boolean success = false;

        //Should be a get
        if (exchange.getRequestMethod().toLowerCase().equals("get")) {

            //Get the url as a string
            String url = exchange.getRequestURI().toString();

            //If the url is empty then set it to the default "/index.html"
            if (url.equals("/")) {
                url = "/index.html";
            }

            //Make the filePath into an actual file
            String filePath = "web" + url;
            File myFile = new File(filePath);

            //Check to see if file actually exists
            if (myFile.exists()) {

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                // Since the client request was invalid, they will not receive the
                // list of games, so we close the response body output stream,
                // indicating that the response is complete.
                String notFoundPath = "web/HTML/404.html";
                myFile = new File(notFoundPath);
            }

            OutputStream respBody = exchange.getResponseBody();
            Files.copy(myFile.toPath(), respBody);
            respBody.close();
        }

    }
}

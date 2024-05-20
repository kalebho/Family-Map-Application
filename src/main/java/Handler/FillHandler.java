package Handler;

import DAO.DataAccessException;
import Response.FillResponse;
import Response.RegisterResponse;
import Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {

            //if the request is a post then we will continue
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //Extract the URL to get the username and number of generations
                String url = exchange.getRequestURI().toString();
                String[] urlArray = url.split("/");

                String username = urlArray[2];
                int numGenerations;
                if (urlArray.length == 3) {
                    numGenerations = 4;
                }
                else {
                    numGenerations = Integer.parseInt(urlArray[3]);
                }

                //Create a Fill Service object and call fill on it using the request
                FillService fillService = new FillService();
                FillResponse fillResponse = null;
                fillResponse = fillService.fill(username, numGenerations);

                //Once you've registered then start creating response body
                if (fillResponse.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Gson gson = new Gson();
                //Change the FillResponse object into JSON string using GSON
                String fillResponseJSON = gson.toJson(fillResponse);
                //Get the response body
                OutputStream respBody = exchange.getResponseBody();
                writeString(fillResponseJSON, respBody);
                //CLose the output stream to know we are done sending data
                respBody.close();
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
